package com.colagom.infiniterv

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val dummyNotices = listOf(
        Cat("https://cdn2.thecatapi.com/images/6q6.jpg", "link1"),
        Cat("https://cdn2.thecatapi.com/images/9pi.jpg", "link2"),
        Cat("https://cdn2.thecatapi.com/images/c2b.jpg", "link3")
    )

    private val carouselAdapter by lazy {
        CarouselAdapter(::catClicked)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv_news.adapter = carouselAdapter
        carouselAdapter.setItems(dummyNotices)
    }

    private fun catClicked(cat: Cat) {
        tv.text = "clicked ${cat.linkUrl}"
    }
}


data class Cat(
    override val imgUrl: String,
    val linkUrl: String
) : CarouselEntity()
