package ru.castprograms.hackeducation.ui.main.courses

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager

open class CoursesLinearLayoutManager(context: Context): LinearLayoutManager(context) {
    constructor(context: Context, orientation: Int, reverseLayout: Boolean): this(context)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int): this(context)

    var isScrollEnabled = true

    override fun canScrollVertically(): Boolean {
        return super.canScrollVertically() && isScrollEnabled
    }
}