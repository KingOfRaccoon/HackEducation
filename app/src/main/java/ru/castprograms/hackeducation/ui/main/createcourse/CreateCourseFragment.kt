package ru.castprograms.hackeducation.ui.main.createcourse

import android.animation.Animator
import android.animation.ObjectAnimator
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ItemTouchHelper
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import com.xwray.groupie.kotlinandroidextensions.Item
import ru.castprograms.hackeducation.R
import ru.castprograms.hackeducation.databinding.FragmentCreateCourseBinding
import ru.castprograms.hackeducation.databinding.LayoutAddPathCourseHeaderBinding
import ru.castprograms.hackeducation.databinding.LayoutAddPathCourseTextBinding
import ru.castprograms.hackeducation.databinding.SnackbarLayoutBinding
import ru.castprograms.hackeducation.tools.ui.DeleteItemSwipeController
import ru.castprograms.hackeducation.tools.ui.DeleteSwipeControllerActions
import ru.castprograms.hackeducation.ui.main.createcourse.items.PathCourseHeaderItem
import ru.castprograms.hackeducation.ui.main.createcourse.items.PathCourseTextItem

class CreateCourseFragment : Fragment(R.layout.fragment_create_course) {
    lateinit var binding: FragmentCreateCourseBinding
    private val adapter = ItemMoveGroupAdapter()
    private var imageUri = MutableLiveData(Uri.EMPTY)

    private val cropActivityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = CropImage.getActivityResult(it.data).uri
                imageUri.postValue(data)
            }
        }

    private val activityForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data = it.data
                if (data != null) {
                    val imageUri = data.data
                    if (imageUri != null)
                        cropActivityForResult.launch(
                            CropImage.activity(imageUri)
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .getIntent(requireContext())
                        )
                }
            }
        }

    private val pickIntent =
        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "image/*"
        }

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
        dialogBinding.imageSelectForTheme.setOnClickListener {
            activityForResult.launch(pickIntent, ActivityOptionsCompat.makeBasic())
            imageUri.observe(viewLifecycleOwner) {
                if (it != Uri.EMPTY) {
                    Glide.with(requireContext())
                        .load(it)
                        .into(dialogBinding.imageSelectForTheme)
                }
            }
        }

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
                            dialogBinding.themeNameText.text.trim().toString(),
                            if (imageUri.value != Uri.EMPTY) imageUri.value.toString() else ""
                        )
                    )
                )

                ad.dismiss()
            }
        }
    }

    private fun addText() {
        val view = LayoutInflater.from(requireContext())
            .inflate(R.layout.layout_add_path_course_text, null)
        val dialogBinding = LayoutAddPathCourseTextBinding.bind(view)

        val ad = createAlertDialog(view)
        dialogBinding.imageSelectForTheme.setOnClickListener {
            activityForResult.launch(pickIntent, ActivityOptionsCompat.makeBasic())
            imageUri.observe(viewLifecycleOwner) {
                if (it != Uri.EMPTY) {
                    Glide.with(requireContext())
                        .load(it)
                        .into(dialogBinding.imageSelectForTheme)
                }
            }
        }

        dialogBinding.button.setOnClickListener {
            if (validateCreateHeader(
                    dialogBinding.errorTextPath, dialogBinding.textPath,
                    dialogBinding.errorTextTitle, dialogBinding.textTitleText
                )
            ) {
                adapter.add(
                    PathCourseTextItem(
                        PathCourseTextItem.PathCourseTextData(
                            dialogBinding.textTitleText.text.trim().toString(),
                            dialogBinding.textPath.text.trim().toString(),
                            if (imageUri.value != Uri.EMPTY) imageUri.value.toString() else ""
                        )
                    )
                )

                ad.dismiss()
            }
        }
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
