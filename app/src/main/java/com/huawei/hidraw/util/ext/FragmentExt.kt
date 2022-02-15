package com.huawei.hidraw.util.ext

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.huawei.hidraw.util.AppPermission
import com.huawei.hidraw.util.ManageExternalContracts
import com.huawei.hidraw.util.MultiplePermissionResult


fun Fragment.hasPermission(permission: AppPermission): Boolean {
    return when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> requireContext().checkSelfPermission(
            permission.name
        ) == PackageManager.PERMISSION_GRANTED
        else -> true
    }
}


fun Fragment.getMultiplePermissionResult(vararg permissions: AppPermission): MultiplePermissionResult {
    return permissions.groupBy { hasPermission(it) }.run {
        val grantedPermissions = this[true].orEmpty()
        val deniedPermissions = this[false].orEmpty()
        val deniedPermissionsStatus =
            deniedPermissions.groupBy { shouldShowRequestPermissionRationale(it.name) }

        MultiplePermissionResult(
            isAllPermissionGiven = deniedPermissions.isEmpty(),
            grantedPermissions = grantedPermissions,
            deniedPermissions = deniedPermissionsStatus[true].orEmpty(),
            permanentlyDeniedPermissions = deniedPermissionsStatus[false].orEmpty()
        )
    }
}

fun Fragment.getContentLauncher(onResult: (Uri?) -> Unit) =
    registerForActivityResult(ActivityResultContracts.GetContent(), onResult)

fun Fragment.getStartActivityForResultLauncher(onResult: (ActivityResult) -> Unit) =
    registerForActivityResult(ActivityResultContracts.StartActivityForResult(), onResult)

fun Fragment.getManageExternalStorageResultLauncher(onResult: (Boolean) -> Unit) =
    registerForActivityResult(ManageExternalContracts(), onResult)

fun Fragment.getPermissionResultLauncher(
    permission: AppPermission,
    onPermissionGranted: () -> Unit,
    onPermissionDenied: ((AppPermission) -> Unit)? = null,
    onPermissionDeniedPermanently: ((AppPermission) -> Unit)? = null
) = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
    when {
        isGranted -> onPermissionGranted.invoke()
        shouldShowRequestPermissionRationale(permission.name).not() -> onPermissionDeniedPermanently?.invoke(
            permission
        )
        else -> onPermissionDenied?.invoke(permission)
    }
}

fun Fragment.goSettings() {
    Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", requireContext().packageName, null)
    }.run {
        startActivity(this)
    }
}

@RequiresApi(Build.VERSION_CODES.R)
fun checkManageExternalPermission(): Boolean =
    Environment.isExternalStorageManager()



