package com.huawei.hidraw.ui.adapter.drawwinner

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject

class DrawWinnerAdapter @Inject constructor() : RecyclerView.Adapter<DrawWinnerViewHolder>() {

    var winners: List<ItemWinnerViewState> = listOf()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrawWinnerViewHolder {
        return DrawWinnerViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: DrawWinnerViewHolder, position: Int) {
        holder.bind(winners[position])
    }

    override fun getItemCount(): Int = winners.size
}
