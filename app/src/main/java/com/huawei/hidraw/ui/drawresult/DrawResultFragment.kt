package com.huawei.hidraw.ui.drawresult

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
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
    ), HBRecorderListener {

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
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            handlePermissionStatusForAndroid11Below()
        }

    private val screenRecordResultLauncher = getStartActivityForResultLauncher {
        if (it.resultCode == Activity.RESULT_OK) {
            setOutputPath()
            hbRecorder.startScreenRecording(it.data, it.resultCode, requireActivity())
        } else {
            showPermissionErrorAndGetDrawResult()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val onBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val action = actionDrawResultFragmentToDrawDetailFragment()
                    .setDrawId(viewModel.getDrawId())
                navigateDirections(action)
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
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
        val permissionsResult = getMultiplePermissionResult(
            ReadExternalStorage,
            WriteExternalStorage,
            RecordAudio
        )

        when {
            permissionsResult.isAllPermissionGiven -> getDrawResultWithScreenRecord()
            permissionsResult.permanentlyDeniedPermissions.isNotEmpty() || permissionsResult.permanentlyDeniedPermissions.isNotEmpty() -> showPermissionErrorAndGetDrawResult()
            else -> {
                screenRecordPermissionsResultLauncherForAndroid11Below.launch(
                    permissionsForScreenRecord
                )
            }
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
        //TODO() video editor kit
    }

    private fun saveGalleryByBuildVersion() {
        if (hbRecorder.wasUriSet()) {
            updateGalleryUri()
        } else {
            refreshGalleryFile()
        }
    }

    override fun HBRecorderOnError(errorCode: Int, reason: String?) {
        showError(R.string.common_error)
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
        if (!f1.exists()) {
            if (f1.mkdirs()) {
                Log.i("Folder ", "created")
            }
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

}
