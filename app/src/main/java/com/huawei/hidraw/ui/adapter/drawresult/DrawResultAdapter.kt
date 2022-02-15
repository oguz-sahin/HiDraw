package com.huawei.hidraw.ui.adapter.drawresult

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huawei.hidraw.R
import com.huawei.hidraw.ui.adapter.drawresult.DrawResultAdapterModel.TitleItem
import com.huawei.hidraw.ui.adapter.drawresult.DrawResultAdapterModel.WinnerItem
import com.huawei.hidraw.ui.adapter.drawwinner.DrawWinnerViewHolder
import com.huawei.hidraw.ui.adapter.title.TitleViewHolder
import javax.inject.Inject

class DrawResultAdapter @Inject constructor() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var drawResultAdapterModels = listOf<DrawResultAdapterModel>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_title -> TitleViewHolder.create(parent)
            R.layout.item_winner -> DrawWinnerViewHolder.create(parent)
            else -> throw IllegalStateException("Unknown viewType")

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TitleViewHolder -> {
                val titleItem = drawResultAdapterModels[position] as TitleItem
                holder.bind(titleItem.itemTitleViewState)
            }

            is DrawWinnerViewHolder -> {
                val winnerItem = drawResultAdapterModels[position] as WinnerItem
                holder.bind(winnerItem.itemWinnerViewState)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (drawResultAdapterModels[position]) {
            is TitleItem -> R.layout.item_title
            is WinnerItem -> R.layout.item_winner
        }
    }


    override fun getItemCount() = drawResultAdapterModels.size
}
