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
