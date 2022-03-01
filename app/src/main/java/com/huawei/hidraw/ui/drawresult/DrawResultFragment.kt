/*
 * Copyright 2022. Explore in HMS. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huawei.hidraw.ui.drawresult

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.pm.ActivityInfo
import android.media.MediaPlayer
import android.media.MediaScannerConnection
import android.media.projection.MediaProjectionManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import com.hbisoft.hbrecorder.HBRecorder
import com.hbisoft.hbrecorder.HBRecorderListener
import com.huawei.agconnect.config.AGConnectServicesConfig
import com.huawei.hidraw.R
import com.huawei.hidraw.core.BaseFragmentWithViewModel
import com.huawei.hidraw.databinding.FragmentDrawResultBinding
import com.huawei.hidraw.ui.adapter.drawresult.DrawResultAdapter
import com.huawei.hidraw.ui.adapter.drawresult.DrawResultAdapterModel
import com.huawei.hidraw.ui.drawresult.DrawResultEvent.CheckPermissionsForScreenRecord
import com.huawei.hidraw.ui.drawresult.DrawResultFragmentDirections.actionDrawResultFragmentToDrawDetailFragment
import com.huawei.hidraw.util.AppPermission.*
import com.huawei.hidraw.util.ext.*
import com.huawei.hidraw.vm.DrawResultViewModel
import com.huawei.hms.videoeditor.ui.api.MediaApplication
import com.huawei.hms.videoeditor.ui.api.MediaExportCallBack
import com.huawei.hms.videoeditor.ui.api.MediaInfo
import com.huawei.hms.videoeditor.ui.api.VideoEditorLaunchOption
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@Suppress("DEPRECATION")
@AndroidEntryPoint
class DrawResultFragment :
    BaseFragmentWithViewModel<FragmentDrawResultBinding, DrawResultViewModel>(
        inflate = FragmentDrawResultBinding::inflate
    ),
    HBRecorderListener {

    override val viewModel: DrawResultViewModel by viewModels()

    private val hbRecorder by lazy {
        HBRecorder(requireContext(), this).apply {
            isAudioEnabled(false)
        }
    }

    private lateinit var contentValues: ContentValues

    private var mUri: Uri? = null

    private val contentResolver by lazy { requireActivity().contentResolver }

    @Inject
    lateinit var drawResultAdapter: DrawResultAdapter

    @RequiresApi(Build.VERSION_CODES.R)
    private val manageExternalStorageLauncher = getManageExternalStorageResultLauncher {
        if (it) handlePermissionStatusForAndroid11OrAbove()
        else showPermissionErrorAndGetDrawResult()
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private val recordAudioPermissionResultLauncher = getPermissionResultLauncher(
        permission = RecordAudio,
        onPermissionGranted = ::handlePermissionStatusForAndroid11OrAbove,
        onPermissionDenied = { handlePermissionStatusForAndroid11OrAbove() },
        onPermissionDeniedPermanently = { showPermissionErrorAndGetDrawResult() }
    )

    private val permissionsForScreenRecord =
        arrayOf(WriteExternalStorage.name, ReadExternalStorage.name, RecordAudio.name)

    private val screenRecordPermissionsResultLauncherForAndroid11Below =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissionResult ->
            when {
                hasPermissions(
                    WriteExternalStorage,
                    RecordAudio,
                    ReadExternalStorage
                ) -> getDrawResultWithScreenRecord()
                permissionResult.keys.any { shouldShowRequestPermissionRationale(it).not() } -> showPermissionErrorAndGetDrawResult()
                else -> handlePermissionStatusForAndroid11Below()
            }
        }

    private val screenRecordResultLauncher = getStartActivityForResultLauncher {
        if (it.resultCode == Activity.RESULT_OK) {
            setOutputPath()
            hbRecorder.startScreenRecording(it.data, it.resultCode, requireActivity())
        } else {
            showPermissionErrorAndGetDrawResult()
        }
    }

    private val mediaExportCallBack: MediaExportCallBack = object : MediaExportCallBack {
        override fun onMediaExportSuccess(mediaInfo: MediaInfo) {
            showSuccess(R.string.saved_gallery_successfully)
        }

        override fun onMediaExportFailed(errorCode: Int) {
            showError(R.string.common_error)
        }
    }

    private val videoEditorKitLicenceId by lazy { UUID.randomUUID().toString() }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            navigateDrawDetailPage()
        }
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(requireActivity()) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
        }
    }

    private fun navigateDrawDetailPage() {
        val action = actionDrawResultFragmentToDrawDetailFragment()
            .setDrawId(viewModel.getDrawId())
        navigateDirections(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initVideoEditorKit()
    }

    private fun initVideoEditorKit() {
        with(MediaApplication.getInstance()) {
            val apiKey = AGConnectServicesConfig.fromContext(requireContext())
                .getString(AGC_API_KEY)
            setApiKey(apiKey)
            setLicenseId(videoEditorKitLicenceId)
            setOnMediaExportCallBack(mediaExportCallBack)
        }
    }

    private fun openVideoEditor() {
        val option = VideoEditorLaunchOption.Builder()
            .setStartMode(MediaApplication.START_MODE_IMPORT_FROM_MEDIA)
            .build()

        MediaApplication.getInstance().launchEditorActivity(requireContext(), option)
    }

    override fun initObserver() {
        observeEvent(viewModel.drawResultEvent, ::handleEvent)
        observe(viewModel.drawResult, ::setDrawResult)
    }

    override fun initListener() {
        binding.btnStopRecording.setOnClickListener {
            hbRecorder.stopScreenRecording()
        }
    }

    private fun initAdapter() {
        binding.rvResult.adapter = drawResultAdapter
    }

    private fun setDrawResult(drawResult: List<DrawResultAdapterModel>) {
        drawResultAdapter.drawResultAdapterModels = drawResult
        playApplauseAudio()
        playAnimation()
        showStopRecordingButtonIfScreenRecordActive()
    }

    private fun showStopRecordingButtonIfScreenRecordActive() {
        if (hbRecorder.isBusyRecording) {
            binding.btnStopRecording.show()
        }
    }

    private fun playAnimation() {
        with(binding.lavConfetti) {
            show()
            playAnimation()
        }
    }

    private fun playApplauseAudio() {
        MediaPlayer.create(requireContext(), R.raw.applause).also {
            it.start()
        }
    }

    private fun handleEvent(drawResultEvent: DrawResultEvent) {
        when (drawResultEvent) {
            CheckPermissionsForScreenRecord -> handlePermissionStatusByBuildVersion()
        }
    }

    private fun handlePermissionStatusByBuildVersion() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            handlePermissionStatusForAndroid11OrAbove()
        } else {
            handlePermissionStatusForAndroid11Below()
        }
    }

    private fun handlePermissionStatusForAndroid11Below() {
        if (hasPermissions(WriteExternalStorage, ReadExternalStorage, RecordAudio)) {
            getDrawResultWithScreenRecord()
        } else {
            screenRecordPermissionsResultLauncherForAndroid11Below.launch(
                permissionsForScreenRecord
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private fun handlePermissionStatusForAndroid11OrAbove() {
        when {
            checkManageExternalPermission().not() -> manageExternalStorageLauncher.launch("")
            hasPermission(RecordAudio).not() -> recordAudioPermissionResultLauncher.launch(
                RecordAudio.name
            )
            else -> getDrawResultWithScreenRecord()
        }
    }

    private fun getDrawResultWithScreenRecord() {
        val mediaProjectManager =
            requireActivity().getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
        val permissionIntent = mediaProjectManager.createScreenCaptureIntent()
        screenRecordResultLauncher.launch(permissionIntent)
    }

    private fun showPermissionErrorAndGetDrawResult() {
        showError(R.string.error_no_screen_record)
        viewModel.getDrawResult()
    }

    override fun HBRecorderOnStart() {
        Toast.makeText(requireContext(), "Screen Record Start", Toast.LENGTH_SHORT).show()
        viewModel.getDrawResult()
    }

    override fun HBRecorderOnComplete() {
        saveGalleryByBuildVersion()
        openVideoEditorDialog()
    }

    override fun HBRecorderOnError(errorCode: Int, reason: String?) {
        showError(R.string.common_error)
    }

    private fun openVideoEditorDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.would_you_like_edit)
            .setMessage(R.string.video_edit_message)
            .setCancelable(true)
            .setPositiveButton(R.string.okey) { _, _ ->
                openVideoEditor()
            }
            .setNegativeButton(R.string.cancel) { dialog, _ ->
                dialog.dismiss()
                navigateDrawDetailPage()
            }
            .setOnCancelListener {
                navigateDrawDetailPage()
            }
            .create()
            .show()
    }

    private fun saveGalleryByBuildVersion() {
        if (hbRecorder.wasUriSet()) {
            updateGalleryUri()
        } else {
            refreshGalleryFile()
        }
    }

    private fun setOutputPath() {
        val filename = generateFileName()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentValues = ContentValues().apply {
                put(MediaStore.Video.Media.RELATIVE_PATH, "Movies/" + "HBRecorder")
                put(MediaStore.Video.Media.TITLE, filename)
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4")
            }

            mUri = contentResolver.insert(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            hbRecorder.fileName = filename
            hbRecorder.setOutputUri(mUri)
        } else {
            createFolderIfNotCreated()
            hbRecorder.setOutputPath(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)
                    .toString() + "/HBRecorder"
            )
        }
    }

    private fun generateFileName(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault())
        val currentDate = Date(System.currentTimeMillis())
        return formatter.format(currentDate).replace(" ", "")
    }

    private fun createFolderIfNotCreated() {
        val f1 = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES),
            "HBRecorder"
        )
        if (!f1.exists() && f1.mkdirs()) {
            Log.i("Folder ", "created")
        }
    }

    @SuppressLint("InlinedApi")
    private fun updateGalleryUri() {
        with(contentValues) {
            clear()
            put(MediaStore.Video.Media.IS_PENDING, 0)
        }
        mUri?.let {
            contentResolver.update(it, contentValues, null, null)
        }
    }

    private fun refreshGalleryFile() {
        MediaScannerConnection.scanFile(
            requireContext(), arrayOf(hbRecorder.filePath), null
        ) { path, uri ->
            Log.i("ExternalStorage", "Scanned $path:")
            Log.i("ExternalStorage", "-> uri=$uri")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (hbRecorder.isBusyRecording) {
            hbRecorder.stopScreenRecording()
        }
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        onBackPressedCallback.remove()
    }

    companion object {
        const val AGC_API_KEY = "client/api_key"
    }
}
