package ru.castprograms.hackeducation.tools.ui

import android.graphics.*
import android.graphics.drawable.Drawable

class MirroredDrawable constructor(private val mDrawable: Drawable) : Drawable() {
    private val matrix: Matrix = Matrix()
    override fun draw(canvas: Canvas) {
        matrix.reset()
        matrix.preScale(-1.0f, 1.0f, (canvas.width / 2).toFloat(), (canvas.height / 2).toFloat())
        canvas.setMatrix(matrix)
//        val drawingRect = Rect()
//        drawingRect.left = (bounds.width() - mDrawable.intrinsicWidth) / 2
//        drawingRect.top = (bounds.height() - mDrawable.intrinsicHeight) / 2
//        drawingRect.right = drawingRect.left + mDrawable.intrinsicWidth
//        drawingRect.bottom = drawingRect.top + mDrawable.intrinsicHeight
//        mDrawable.bounds = drawingRect
        mDrawable.draw(canvas)
    }

    // Other methods required to extend Drawable but aren't used here.
    override fun setAlpha(alpha: Int) {}
    override fun setColorFilter(colorFilter: ColorFilter?) {}
    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE
    }
}