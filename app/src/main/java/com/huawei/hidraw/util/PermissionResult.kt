package com.huawei.hidraw.util

data class MultiplePermissionResult(
    val isAllPermissionGiven: Boolean,
    val grantedPermissions: List<AppPermission>,
    val deniedPermissions: List<AppPermission>,
    val permanentlyDeniedPermissions: List<AppPermission> = listOf()
)
