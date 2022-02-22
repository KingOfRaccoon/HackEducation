package ru.castprograms.hackeducation.tools.ui

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.view.HapticFeedbackConstants
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import ru.castprograms.hackeducation.R
import kotlin.math.ceil
import kotlin.math.min

class DeleteItemSwipeController(
    private val context: Context,
    private val deleteSwipeControllerActions: DeleteSwipeControllerActions
) : ItemTouchHelper.Callback() {

    private val shareRound: Drawable by lazy { context.getDrawable(R.drawable.reply_round)!! }
    private val deleteImage: Drawable by lazy { context.getDrawable(R.drawable.delete_outline)!! }
    private val metrics: DisplayMetrics by lazy { mView.context.resources.displayMetrics }

    private var currentItemViewHolder: RecyclerView.ViewHolder? = null
    private lateinit var mView: View
    private var dX = 0f

    private var swipeBack = false
    private var replyButtonProgress: Float = 0f
    private var lastReplyButtonAnimationTime: Long = 0
    private var isVibrate = false
    private var startTracking = false
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        mView = (viewHolder as GroupieViewHolder).containerView
        return makeMovementFlags(
            ItemTouchHelper.ANIMATION_TYPE_SWIPE_CANCEL,
            ItemTouchHelper.RIGHT
        )
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

    override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
        if (swipeBack) {
            swipeBack = false
            return 0
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            setTouchListener(recyclerView, viewHolder)
        }

        if (mView.translationX < metrics.widthPixels / 3 || dX < this.dX) {
            super.onChildDraw(
                c,
                recyclerView,
                viewHolder,
                dX,
                dY,
                actionState,
                isCurrentlyActive
            )
            this.dX = dX
            startTracking = true
        }
        currentItemViewHolder = viewHolder
        drawDeleteButton(c)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setTouchListener(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        recyclerView.setOnTouchListener { _, event ->
            swipeBack =
                event.action == MotionEvent.ACTION_CANCEL || event.action == MotionEvent.ACTION_UP
            if (swipeBack) {
                if (mView.translationX >= dp(100))
                    deleteSwipeControllerActions.deleteItem(viewHolder.adapterPosition)
            }
            false
        }
    }

    private fun drawDeleteButton(canvas: Canvas) {
        if (currentItemViewHolder == null) {
            return
        }
        val translationX = mView.translationX
        val newTime = System.currentTimeMillis()
        val dt = Math.min(17, newTime - lastReplyButtonAnimationTime)
        lastReplyButtonAnimationTime = newTime
        val showing = translationX >= convertTodp(30)
        if (showing) {
            if (replyButtonProgress < 1.0f) {
                replyButtonProgress += dt / 180.0f
                if (replyButtonProgress > 1.0f) {
                    replyButtonProgress = 1.0f
                } else {
                    mView.invalidate()
                }
            }
        } else if (translationX <= 0.0f) {
            replyButtonProgress = 0f
            startTracking = false
            isVibrate = false
        } else {
            if (replyButtonProgress > 0.0f) {
                replyButtonProgress -= dt / 180.0f
                if (replyButtonProgress < 0.1f) {
                    replyButtonProgress = 0f
                } else {
                    mView.invalidate()
                }
            }
        }
        val alpha: Int
        val scale: Float
        if (showing) {
            scale = if (replyButtonProgress <= 0.8f) {
                1.2f * (replyButtonProgress / 0.8f)
            } else {
                1.2f - 0.2f * ((replyButtonProgress - 0.8f) / 0.2f)
            }
            alpha = min(255f, 255 * (replyButtonProgress / 0.8f)).toInt()
        } else {
            scale = replyButtonProgress
            alpha = min(255f, 255 * replyButtonProgress).toInt()
        }

        deleteImage.alpha = alpha
        deleteImage.setTint(Color.RED)
        if (startTracking) {
            if (!isVibrate && mView.translationX >= convertTodp(100)) {
                mView.performHapticFeedback(
                    HapticFeedbackConstants.KEYBOARD_TAP,
                    HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
                )
                isVibrate = true
            }
        }

        val x: Int = if (mView.translationX > convertTodp(130)) {
            convertTodp(130) / 2
        } else {
            (mView.translationX / 2).toInt()
        }

        val y = (mView.top + mView.measuredHeight / 2).toFloat()
        deleteImage.setBounds(
            (x - convertTodp(12) * scale).toInt(),
            (y - convertTodp(11) * scale).toInt(),
            (x + convertTodp(12) * scale).toInt(),
            (y + convertTodp(10) * scale).toInt()
        )
        deleteImage.draw(canvas)
        deleteImage.alpha = 255
    }

    private fun convertTodp(pixel: Int): Int {
        return dp(pixel)
    }

    private fun dp(pixel: Int): Int {
        return ceil(context.resources.displayMetrics.density * pixel).toInt()
    }
}