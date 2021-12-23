package com.huawei.hidraw.ui.drawdetail

import android.content.Context
import com.huawei.hidraw.R
import com.huawei.hidraw.core.BaseViewState
import com.huawei.hidraw.data.model.DrawModel
import com.huawei.hidraw.data.model.DrawStatusTypes

/**
 * Created by Oguz Sahin on 12/13/2021.
 */
data class DrawDetailFragmentViewState(private val drawModel: DrawModel) : BaseViewState() {

    fun getPostImageUrl() = drawModel.postUrl

    fun getDrawTitle() = drawModel.drawName

    fun getDrawDescription() = drawModel.description

    fun getDrawWinnerCount(context: Context) =
        context.getString(R.string.winner_count, drawModel.permenantUserCount)

    fun getDrawBackupWinnerCount(context: Context) =
        context.getString(R.string.backup_winner_count, drawModel.spareUserCount)

    fun getDrawParticipantCount(context: Context) =
        context.getString(R.string.participant_count, drawModel.participantCount)

    fun getStatusImageVisibility() =
        getViewVisibility(drawModel.status != DrawStatusTypes.ACTIVE.value)

    fun getStatusImage() = when (drawModel.status) {
        DrawStatusTypes.PASSIVE.value -> R.mipmap.completed
        DrawStatusTypes.DELAYED.value -> R.mipmap.delayed
        else -> R.mipmap.completed
    }

    fun getWinnerGroupVisibility() =
        getViewVisibility(drawModel.status == DrawStatusTypes.PASSIVE.value && drawModel.permenantUserCount > 0)

    fun getBackupWinnerGroupVisibility() =
        getViewVisibility(drawModel.status == DrawStatusTypes.PASSIVE.value && drawModel.spareUserCount > 0)

    fun getButtonVisibility() =
        getViewVisibility(isViewVisible = drawModel.status == DrawStatusTypes.PASSIVE.value)

    //   fun getButtonText() = if
}
