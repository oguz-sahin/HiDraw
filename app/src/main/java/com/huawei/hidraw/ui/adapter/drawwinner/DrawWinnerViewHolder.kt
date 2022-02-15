package com.huawei.hidraw.ui.adapter.drawwinner

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huawei.hidraw.databinding.ItemWinnerBinding
import com.huawei.hidraw.util.ext.executeWithAction
import com.huawei.hidraw.util.ext.inflate

class DrawWinnerViewHolder(private val binding: ItemWinnerBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(itemWinnerViewState: ItemWinnerViewState) {
        binding.executeWithAction {
            viewState = itemWinnerViewState
        }
    }

    companion object {
        fun create(parent: ViewGroup): DrawWinnerViewHolder {
            val binding = parent.inflate(ItemWinnerBinding::inflate)
            return DrawWinnerViewHolder(binding)
        }
    }
}
