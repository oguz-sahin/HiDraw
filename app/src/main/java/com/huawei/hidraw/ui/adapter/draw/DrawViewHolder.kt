/*
 * Copyright 2022. Explore in HMS. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
