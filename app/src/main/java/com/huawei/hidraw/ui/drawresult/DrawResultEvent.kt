package com.huawei.hidraw.ui.drawresult

sealed class DrawResultEvent {
    object CheckPermissionsForScreenRecord : DrawResultEvent()
}
