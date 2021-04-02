package com.colagom.infiniterv

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.PagerSnapHelper
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

        rv_cat.run {
            PagerSnapHelper().attachToRecyclerView(this)
            adapter = carouselAdapter.apply {
                setItems(dummyNotices)
                addItemDecoration(CircularIndicatorDecoration(this@MainActivity))
            }
            resumeAutoScroll()
        }
    }

    private fun catClicked(cat: Cat) {
        tv.text = "clicked ${cat.linkUrl}"
    }
}

data class Cat(
    override val imgUrl: String,
    val linkUrl: String
) : CarouselEntity()
