package com.colagom.infiniterv

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.holder_carousel.view.*

class CarouselHolder<T : CarouselEntity>(
    itemView: View,
    private val clicked: (T) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val iv by lazy {
        itemView.iv
    }

    fun bind(data: T) {
        Glide.with(itemView)
            .load(data.imgUrl)
            .into(iv)

        itemView.setOnClickListener {
            clicked(data)
        }
    }
}
