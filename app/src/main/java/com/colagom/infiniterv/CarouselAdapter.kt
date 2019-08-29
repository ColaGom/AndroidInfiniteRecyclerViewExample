package com.colagom.infiniterv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CarouselAdapter<T : CarouselEntity>(
    private val clicked: (T) -> Unit
) : RecyclerView.Adapter<CarouselHolder<T>>() {
    private var items: List<T> = listOf()
    val actualItemCount
        get() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselHolder<T> =
        LayoutInflater.from(parent.context).inflate(R.layout.holder_carousel, parent, false)
            .let {
                CarouselHolder(it, clicked)
            }

    override fun getItemCount(): Int = if (items.isEmpty()) 0 else Integer.MAX_VALUE

    override fun onBindViewHolder(holder: CarouselHolder<T>, position: Int) {
        items[position % actualItemCount].let(holder::bind)
    }

    fun setItems(value: List<T>) {
        items = value
        notifyDataSetChanged()
    }
}