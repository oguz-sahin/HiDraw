package com.huawei.hidraw.ui.adapter.draw

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huawei.hidraw.data.model.DrawModel
import com.huawei.hidraw.databinding.ItemCommonDrawBinding
import com.huawei.hidraw.util.ext.inflate

/**
 * Created by Oguz Sahin on 12/2/2021.
 */

class DrawViewHolder(private val binding: ItemCommonDrawBinding, onDrawClicked: (() -> Unit)?) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener { onDrawClicked?.invoke() }
    }

    fun bind(drawModel: DrawModel) {
        with(binding) {
            viewState = DrawItemViewState(drawModel)
            executePendingBindings()
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onDrawClicked: (() -> Unit)?
        ): DrawViewHolder {
            val binding = parent.inflate(ItemCommonDrawBinding::inflate)
            return DrawViewHolder(binding, onDrawClicked)
        }
    }
}