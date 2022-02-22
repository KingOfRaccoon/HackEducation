package ru.castprograms.hackeducation.ui.main.createcourse

import android.animation.Animator
import android.animation.ObjectAnimator
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import com.google.android.material.snackbar.Snackbar
import com.xwray.groupie.kotlinandroidextensions.Item
import ru.castprograms.hackeducation.R
import ru.castprograms.hackeducation.databinding.FragmentCreateCourseBinding
import ru.castprograms.hackeducation.databinding.LayoutAddPathCourseHeaderBinding
import ru.castprograms.hackeducation.databinding.SnackbarLayoutBinding
import ru.castprograms.hackeducation.tools.ui.DeleteItemSwipeController
import ru.castprograms.hackeducation.tools.ui.DeleteSwipeControllerActions
import ru.castprograms.hackeducation.ui.main.createcourse.items.PathCourseHeaderItem
import ru.castprograms.hackeducation.ui.main.createcourse.items.PathCourseTextItem

class CreateCourseFragment : Fragment(R.layout.fragment_create_course) {
    lateinit var binding: FragmentCreateCourseBinding
    private val adapter = ItemMoveGroupAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateCourseBinding.bind(view)
        binding.recyclerNewCourse.adapter = adapter
        val deleteItemSwipeController = DeleteItemSwipeController(requireContext(),
            object : DeleteSwipeControllerActions {
                override fun deleteItem(position: Int) {
                    testSnack(position, adapter.getItem(position) as Item)
                    adapter.remove(adapter.getItem(position) as Item)
                }
            })
        ItemTouchHelper(deleteItemSwipeController).attachToRecyclerView(binding.recyclerNewCourse)

        binding.addHeader.setOnClickListener {
            addHeader()
        }

        binding.addText.setOnClickListener {
            addText()
        }
    }

    private fun addHeader() {
        val view = LayoutInflater.from(requireContext())
            .inflate(R.layout.layout_add_path_course_header, null)
        val dialogBinding = LayoutAddPathCourseHeaderBinding.bind(view)

        val ad = createAlertDialog(view)

        dialogBinding.button.setOnClickListener {
            if (validateCreateHeader(
                    dialogBinding.errorThemeNumber, dialogBinding.themeNumberText,
                    dialogBinding.errorNameTheme, dialogBinding.themeNameText
                )
            ) {
                adapter.add(
                    PathCourseHeaderItem(
                        PathCourseHeaderItem.PathCourseHeaderData(
                            dialogBinding.themeNumberText.text.trim().toString().toInt(),
                            dialogBinding.themeNameText.text.trim().toString()
                        )
                    )
                )

                ad.dismiss()
            }
        }
    }

    private fun addText() {
        adapter.add(
            PathCourseTextItem()
        )
    }

    private fun validateCreateHeader(
        firstError: TextView,
        firstEditText: EditText,
        secondError: TextView,
        secondEditText: EditText
    ) = !mutableListOf(
        isValidEditText(firstEditText, firstError),
        isValidEditText(secondEditText, secondError)
    ).contains(false)

    private fun isValidEditText(
        editText: EditText,
        editTextError: TextView
    ): Boolean {
        return if (editText.text.isNullOrEmpty() || editText.text.isNullOrBlank()) {
            editTextError.text = "Поле не должно быть пустым"
            editTextError.visibility = View.VISIBLE
            false
        } else {
            true
        }
    }

    private fun testSnack(position: Int, item: Item) {
        val snackBar = Snackbar.make(requireView(), "Test", Snackbar.LENGTH_INDEFINITE)
        val snackLayout = snackBar.view as Snackbar.SnackbarLayout
        val snackBarView = layoutInflater.inflate(R.layout.snackbar_layout, null)
        val snackBarBinding = SnackbarLayoutBinding.bind(snackBarView)
        val anim = ObjectAnimator.ofInt(snackBarBinding.progressBarSnackBar, "progress", 6000, 0)
        anim.duration = 6000
        snackBarBinding.textProgressSnackBar.setFactory {
            val textView = TextView(requireContext())
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            textView.gravity = Gravity.CENTER
            textView.setTextColor(Color.WHITE)
            textView
        }

        snackBarBinding.buttonUndoSnackBar.setOnClickListener {
            adapter.add(position, item)
            snackBar.dismiss()
        }

        anim.addUpdateListener {
            val time = ((anim.duration - it.currentPlayTime) / 1000).toInt().toString()
            if (time != (snackBarBinding.textProgressSnackBar[0] as TextView).text)
                snackBarBinding.textProgressSnackBar.setText(time)
        }

        anim.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {}
            override fun onAnimationEnd(p0: Animator?) {
                snackBar.dismiss()
            }

            override fun onAnimationCancel(p0: Animator?) {}
            override fun onAnimationRepeat(p0: Animator?) {}
        })
        anim.start()
        snackLayout.removeAllViewsInLayout()
        snackLayout.addView(snackBarView)
        snackBar.show()
    }

    private fun createAlertDialog(view: View): AlertDialog {
        return AlertDialog.Builder(requireContext()).setView(view).create().apply {
            window?.setBackgroundDrawable(ColorDrawable(0))
            show()
        }
    }
}
