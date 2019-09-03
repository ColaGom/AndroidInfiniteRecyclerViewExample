package com.colagom.infiniterv

import android.animation.ArgbEvaluator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CircularIndicatorDecoration(context: Context) : RecyclerView.ItemDecoration() {

    private val selectedColor: Int = ContextCompat.getColor(context, R.color.selected)
    private val unselectedColor: Int = ContextCompat.getColor(context, R.color.unselected)

    private val diffRadius: Float = context.dpToPx(1.5f)
    private val unselectedRadius: Float = context.dpToPx(3f)
    private val margin: Float = unselectedRadius + context.dpToPx(8f)

    private val paint by lazy {
        Paint().apply {
            color = unselectedColor
            style = Paint.Style.FILL
        }
    }

    private val evaluator by lazy {
        ArgbEvaluator()
    }

    private val interpolator by lazy { AccelerateDecelerateInterpolator() }

    override fun onDrawOver(
        c: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.onDrawOver(c, parent, state)

        parent.adapter?.takeIf { it.itemCount > 0 }?.run {
            val itemCount = (this as CarouselAdapter<*>).actualItemCount

            (parent.layoutManager as? LinearLayoutManager)?.let { layoutManager ->
                val firstItemPosition = layoutManager.findFirstVisibleItemPosition()
                val actualItemPosition = firstItemPosition % itemCount

                layoutManager.findViewByPosition(firstItemPosition)?.let { firstView ->
                    val totalWidth = margin * (itemCount - 1)
                    val startX = (parent.width - totalWidth) / 2
                    val y = parent.height - margin

                    val left = firstView.left
                    val width = firstView.width
                    val progress = interpolator.getInterpolation(-1 * left / width.toFloat())

                    for (i in 0 until itemCount) {
                        var radius = unselectedRadius

                        when (i) {
                            actualItemPosition -> {
                                radius += diffRadius * (1 - progress)
                                paint.color = evaluator.evaluate(
                                    1 - progress,
                                    unselectedColor,
                                    selectedColor
                                ) as Int
                            }
                            actualItemPosition + 1 -> {
                                radius += diffRadius * progress
                                paint.color =
                                    evaluator.evaluate(
                                        progress,
                                        unselectedColor,
                                        selectedColor
                                    ) as Int

                            }
                            else -> paint.color = unselectedColor
                        }

                        c.drawCircle(startX + i * margin, y, radius, paint)
                    }
                }
            }
        }
    }
}