package com.huawei.hidraw.ui.adapter.title

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huawei.hidraw.databinding.ItemTitleBinding
import com.huawei.hidraw.util.ext.executeWithAction
import com.huawei.hidraw.util.ext.inflate

class TitleViewHolder(private val binding: ItemTitleBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(itemTitleViewState: ItemTitleViewState) {
        binding.executeWithAction {
            viewState = itemTitleViewState
        }
    }

    companion object {
        fun create(
            parent: ViewGroup
        ): TitleViewHolder {
            val binding = parent.inflate(ItemTitleBinding::inflate)
            return TitleViewHolder(binding)
        }
    }
}
