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


    val draw = drawDetailModel.draw

    fun getPostImageUrl() = draw.photoUrl?.getServerUrl()

    fun getDrawTitle() = draw.title

    fun getDrawDescription() = draw.description

    fun getDrawEndDate() = draw.endDate.convertTimeStampWithFormat()

    fun getDrawWinnerCount(context: Context) =
        context.getString(R.string.winner_count, draw.winnerCount)

    fun getDrawBackupWinnerCount(context: Context) =
        context.getString(R.string.substitutes_count, draw.substituteCount)

    fun getDrawParticipantCount(context: Context) =
        context.getString(R.string.participant_count, draw.participantCount)

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

    fun getButtonText(context: Context) = when {
        drawDetailModel.createdUser -> context.getString(R.string.start_draw)
        else -> context.getString(R.string.attended)
    }

    fun getButtonColor() = if (drawDetailModel.userAttended) R.color.emperor else R.color.cream_can


    private fun isDrawDateDelayed() = draw.status == ACTIVE.value && draw.endDate.isPassed()

    private fun isDrawCompleted() = draw.status == PASSIVE.value

    private fun hasBackupWinner() = draw.substituteCount > 0


    fun getWinnersItemViewState() =
        drawDetailModel.winners?.map { winnerModel -> ItemWinnerViewState(winnerModel) } ?: listOf()

    fun getSubstitutesItemViewState() =
        drawDetailModel.substitutes?.map { winnerModel -> ItemWinnerViewState(winnerModel) }
            ?: listOf()

}
