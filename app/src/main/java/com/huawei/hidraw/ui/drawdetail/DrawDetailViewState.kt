package com.huawei.hidraw.ui.drawdetail

import android.content.Context
import android.view.View
import com.huawei.hidraw.R
import com.huawei.hidraw.core.BaseViewState
import com.huawei.hidraw.data.model.DrawDetailModel
import com.huawei.hidraw.data.model.DrawStatusTypes.ACTIVE
import com.huawei.hidraw.data.model.DrawStatusTypes.PASSIVE
import com.huawei.hidraw.util.ext.convertTimeStampWithFormat
import com.huawei.hidraw.util.ext.getServerUrl
import com.huawei.hidraw.util.ext.isPassed

/**
 * Created by Oguz Sahin on 12/13/2021.
 */
data class DrawDetailViewState(private val drawDetailModel: DrawDetailModel) :
    BaseViewState() {


    fun getPostImageUrl() = drawDetailModel.photoUrl?.getServerUrl()

    fun getDrawTitle() = drawDetailModel.title

    fun getDrawDescription() = drawDetailModel.description

    fun getDrawEndDate() = drawDetailModel.endDate.convertTimeStampWithFormat()

    fun getDrawWinnerCount(context: Context) =
        context.getString(R.string.winner_count, drawDetailModel.winnerCount)

    fun getDrawBackupWinnerCount(context: Context) =
        context.getString(R.string.backup_winner_count, drawDetailModel.substituteCount)

    fun getDrawParticipantCount(context: Context) =
        context.getString(R.string.participant_count, drawDetailModel.participantCount)

    fun getStatusImageVisibility() = when {
        isDrawCompleted() -> View.VISIBLE
        isDrawDateDelayed() -> View.VISIBLE
        else -> View.GONE
    }

    fun getStatusImage() = when {
        isDrawCompleted() -> R.mipmap.completed
        isDrawDateDelayed() -> R.mipmap.delayed
        else -> R.mipmap.completed
    }

    fun getWinnerGroupVisibility() = getViewVisibility(isViewVisible = isDrawCompleted())

    fun getBackupWinnerGroupVisibility() =
        getViewVisibility(isViewVisible = isDrawCompleted() && hasBackupWinner())

    fun getButtonVisibility() = when {
        isDrawCompleted() -> View.GONE
        isDrawDateDelayed() && drawDetailModel.createdUser.not() -> View.GONE
        else -> View.VISIBLE
    }

    fun getButtonText() = when {
        drawDetailModel.createdUser -> "Star Draw"
        drawDetailModel.userAttended -> "Attended"
        else -> "Attend"
    }

    fun getButtonColor() =
        if (drawDetailModel.userAttended) R.color.hwid_auth_button_color_black else R.color.black


    private fun isDrawDateDelayed() =
        drawDetailModel.status == ACTIVE.value && drawDetailModel.endDate.isPassed()

    private fun isDrawCompleted() = drawDetailModel.status == PASSIVE.value

    private fun hasBackupWinner() = drawDetailModel.substituteCount > 0

}
