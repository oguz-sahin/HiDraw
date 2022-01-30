package com.huawei.hidraw.ui.drawdetail

import android.content.Context
import com.huawei.hidraw.R
import com.huawei.hidraw.core.BaseViewState
import com.huawei.hidraw.data.model.DrawDetailModel
import com.huawei.hidraw.data.model.DrawStatusTypes

/**
 * Created by Oguz Sahin on 12/13/2021.
 */
data class DrawDetailFragmentViewState(private val drawDetailModel: DrawDetailModel) :
    BaseViewState() {

    fun getPostImageUrl() = drawDetailModel.postUrl

    fun getDrawTitle() = drawDetailModel.drawName

    fun getDrawDescription() = drawDetailModel.description

    fun getDrawWinnerCount(context: Context) =
        context.getString(R.string.winner_count, drawDetailModel.permenantUserCount)

    fun getDrawBackupWinnerCount(context: Context) =
        context.getString(R.string.backup_winner_count, drawDetailModel.spareUserCount)

    fun getDrawParticipantCount(context: Context) =
        context.getString(R.string.participant_count, drawDetailModel.participantCount)

    fun getStatusImageVisibility() =
        getViewVisibility(drawDetailModel.status != DrawStatusTypes.ACTIVE.value)

    fun getStatusImage() = when (drawDetailModel.status) {
        DrawStatusTypes.PASSIVE.value -> R.mipmap.completed
        DrawStatusTypes.DELAYED.value -> R.mipmap.delayed
        else -> R.mipmap.completed
    }

    fun getWinnerGroupVisibility() =
        getViewVisibility(drawDetailModel.status == DrawStatusTypes.PASSIVE.value && drawDetailModel.permenantUserCount > 0)

    fun getBackupWinnerGroupVisibility() =
        getViewVisibility(drawDetailModel.status == DrawStatusTypes.PASSIVE.value && drawDetailModel.spareUserCount > 0)

    fun getButtonVisibility() =
        getViewVisibility(isViewVisible = drawDetailModel.status == DrawStatusTypes.PASSIVE.value)

    //   fun getButtonText() = if
}
