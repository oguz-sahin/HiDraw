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
