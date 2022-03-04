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

package com.hms.hidraw.ui.adapter.drawwinner

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
