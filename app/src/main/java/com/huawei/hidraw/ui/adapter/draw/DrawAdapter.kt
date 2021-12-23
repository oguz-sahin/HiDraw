package com.huawei.hidraw.ui.adapter.draw

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.huawei.hidraw.data.model.DrawModel
import javax.inject.Inject

/**
 * Created by Oguz Sahin on 12/1/2021.
 */
class DrawAdapter @Inject constructor() : RecyclerView.Adapter<DrawViewHolder>() {

    var draws = listOf<DrawModel>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var onDrawClicked: ((drawId: Long) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrawViewHolder =
        DrawViewHolder.create(parent, onDrawClicked)

    override fun onBindViewHolder(holder: DrawViewHolder, position: Int) {
        holder.bind(draws[position])
    }

    override fun getItemCount(): Int = draws.size
}
