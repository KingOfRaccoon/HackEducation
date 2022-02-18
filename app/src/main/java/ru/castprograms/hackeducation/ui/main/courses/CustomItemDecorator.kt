package ru.castprograms.hackeducation.ui.main.courses

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import ru.castprograms.hackeducation.tools.ui.MirroredDrawable

class CustomItemDecorator(private val dividerDrawable: Drawable) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        parent.adapter?.let { adapter ->
            outRect.top = when (parent.getChildAdapterPosition(view)) {
                RecyclerView.NO_POSITION,
                adapter.itemCount - 1 -> 0
                else -> dividerDrawable.intrinsicHeight
            }
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        parent.children.forEach { view ->
            val position = parent.getChildAdapterPosition(view)
                .let { if (it == RecyclerView.NO_POSITION) return else it }
            if (parent.adapter != null && position < parent.adapter!!.itemCount - 1) {
                val left = view.right * 0.175
                val bottom = view.top
                val top = bottom - dividerDrawable.intrinsicHeight
                val right = view.right * 0.825
                dividerDrawable.bounds = Rect(left.toInt(), top, right.toInt(), bottom)
                c.save(); //Saving the canvas and later restoring it so only this image will be rotated.
                if (position % 2 != 0)
                    MirroredDrawable(dividerDrawable).apply {
                        bounds = dividerDrawable.bounds
                    }.draw(c)
                else
                    dividerDrawable.draw(c)
                c.restore();
            }
        }
    }
}