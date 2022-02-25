package ru.castprograms.hackeducation.ui.main.createcourse.items

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import ru.castprograms.hackeducation.R
import ru.castprograms.hackeducation.databinding.ItemPathListBinding
import ru.castprograms.hackeducation.databinding.PathCourseListBinding
import ru.castprograms.hackeducation.tools.AndroidTools


class PathCourseListItem : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val binding = PathCourseListBinding.bind(viewHolder.itemView)
        binding.recyclerCourseItem.adapter = CourseListAdapter()
        binding.buttonExpandList.setOnClickListener {
            if (binding.recyclerCourseItem.visibility != View.GONE) {
                val animation =
                    ObjectAnimator.ofFloat(binding.buttonExpandList, "rotationX", 0f, 180f)
                animation.duration = AndroidTools.collapse(binding.recyclerCourseItem)
                animation.interpolator = AccelerateDecelerateInterpolator()
                animation.start()
            } else {
                val animation =
                    ObjectAnimator.ofFloat(binding.buttonExpandList, "rotationX", 180f, 0f)
                animation.duration = AndroidTools.expand(binding.recyclerCourseItem)
                animation.interpolator = AccelerateDecelerateInterpolator()
                animation.start()
            }
        }
    }

    override fun getLayout() = R.layout.path_course_list

    inner class CourseListAdapter : RecyclerView.Adapter<CourseListAdapter.CourseListViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseListViewHolder {
            return CourseListViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_path_list, parent, false)
            )
        }

        override fun onBindViewHolder(holder: CourseListViewHolder, position: Int) {
            holder.bind()
        }

        override fun getItemCount() = 3

        inner class CourseListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val binding = ItemPathListBinding.bind(view)

            fun bind() {
                binding.buttonExpandItem.setOnClickListener {
                    if (binding.containerExpandLayout.isVisible) {
                        val animation =
                            ObjectAnimator.ofFloat(binding.buttonExpandItem, "rotationX", 0f, 180f)
                        animation.duration = AndroidTools.collapse(binding.containerExpandLayout)
                        animation.interpolator = AccelerateDecelerateInterpolator()
                        animation.start()
                    } else {
                        val animation =
                            ObjectAnimator.ofFloat(binding.buttonExpandItem, "rotationX", 180f, 0f)
                        animation.duration = AndroidTools.expand(binding.containerExpandLayout)
                        animation.interpolator = AccelerateDecelerateInterpolator()
                        animation.start()
                    }
                }
            }
        }
    }
}