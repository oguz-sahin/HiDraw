package com.huawei.hidraw.ui.adapter.draw

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huawei.hidraw.data.model.DrawModel
import com.huawei.hidraw.databinding.ItemCommonDrawBinding
import com.huawei.hidraw.util.ext.executeWithAction
import com.huawei.hidraw.util.ext.inflate

/**
 * Created by Oguz Sahin on 12/2/2021.
 */

class DrawViewHolder(
    private val binding: ItemCommonDrawBinding,
    private val onDrawClicked: ((drawId: Long) -> Unit)?
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(drawModel: DrawModel) {
        binding.executeWithAction {
            viewState = DrawItemViewState(drawModel)
        }
        binding.root.setOnClickListener { onDrawClicked?.invoke(drawModel.id) }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onDrawClicked: ((drawId: Long) -> Unit)?
        ): DrawViewHolder {
            val binding = parent.inflate(ItemCommonDrawBinding::inflate)
            return DrawViewHolder(binding, onDrawClicked)
        }
    }
}
