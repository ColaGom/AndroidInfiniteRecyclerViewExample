package com.colagom.infiniterv

import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import java.lang.ref.WeakReference


class AutoScrollableRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {
    private val scrollHandler by lazy {
        ScrollHandler(this)
    }

    private val delayMillis: Long

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.AutoScrollableRecyclerView,
            0, 0
        ).apply {
            try {
                delayMillis = getInt(R.styleable.AutoScrollableRecyclerView_delay, 3000).toLong()
            } finally {
                recycle()
            }
        }
    }

    private fun createScroller(position: Int) = object : LinearSmoothScroller(context) {
        override fun getHorizontalSnapPreference(): Int {
            return SNAP_TO_END
        }
    }.apply {
        targetPosition = position
    }

    override fun dispatchTouchEvent(e: MotionEvent?): Boolean {
        when (e?.action) {
            MotionEvent.ACTION_UP -> resumeAutoScroll()
            MotionEvent.ACTION_DOWN -> pauseAutoScroll()
        }
        parent.requestDisallowInterceptTouchEvent(true)

        return super.dispatchTouchEvent(e)
    }

    private fun pauseAutoScroll() {
        scrollHandler.removeMessages(WHAT_SCROLL)
    }

    fun resumeAutoScroll() {
        scrollHandler.removeMessages(WHAT_SCROLL)
        scrollHandler.sendEmptyMessageDelayed(WHAT_SCROLL, delayMillis)
    }

    fun scrollNext() {
        (layoutManager as? LinearLayoutManager)?.let { layoutManager ->
            layoutManager.startSmoothScroll(
                createScroller(layoutManager.findLastVisibleItemPosition() + 1)
            )
        }
        scrollHandler.sendEmptyMessageDelayed(WHAT_SCROLL, delayMillis)
    }

    private class ScrollHandler(autoScrollableRecyclerView: AutoScrollableRecyclerView) :
        Handler() {

        private val autoScrollViewPager =
            WeakReference<AutoScrollableRecyclerView>(autoScrollableRecyclerView)

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            autoScrollViewPager.get()?.scrollNext()
        }
    }

    companion object {
        const val WHAT_SCROLL = 1
    }
}