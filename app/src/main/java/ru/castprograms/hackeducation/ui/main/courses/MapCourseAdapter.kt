package ru.castprograms.hackeducation.ui.main.courses

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.castprograms.hackeducation.R
import ru.castprograms.hackeducation.databinding.ItemMapCourseBinding
import ru.castprograms.hackeducation.tools.Course
import ru.castprograms.hackeducation.ui.main.createcourse.items.PathCourseHeaderItem

class MapCourseAdapter(var courses: List<Pair<String, Course>> = listOf()) :
    RecyclerView.Adapter<MapCourseAdapter.MapCourseViewHolder>() {

    fun updateCourses(newCourses: List<Pair<String, Course>>) {
        val diff = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return courses.size
            }

            override fun getNewListSize(): Int {
                return newCourses.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return courses[oldItemPosition].first == newCourses[newItemPosition].first
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return courses[oldItemPosition].second == newCourses[newItemPosition].second
            }
        }
        courses = newCourses
        DiffUtil.calculateDiff(diff, true).dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapCourseViewHolder {
        return MapCourseViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_map_course, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MapCourseViewHolder, position: Int) {
        holder.bind(courses[position].second, position)
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = courses.size

    inner class MapCourseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemMapCourseBinding.bind(view)

        fun bind(course: Course, position: Int) {
            changePlace(position)
            setImage(
                course.image,
                if (position % 2 == 0) binding.endImageItem else binding.startImageItem
            )
            setText(
                course.name,
                if (position % 2 == 0) binding.endTextNameItem else binding.startTextNameItem
            )
            setOnClickListener(if (position % 2 == 0) binding.endImageItem else binding.startImageItem, course, position)
        }

        private fun setOnClickListener(view: View, course: Course, position: Int) {
            view.setOnClickListener {
                it.findNavController().navigate(
                    CoursesFragmentDirections.actionCoursesFragmentToShowCourseFragment(course.apply {
                        this.items.forEach {
                            if (it is PathCourseHeaderItem.PathCourseHeaderData)
                                it.themeNumber = position + 1
                        }
                    })
                )
            }
        }

        private fun setText(name: String, textView: TextView) {
            textView.text = name
        }

        private fun setImage(image: String, imageView: ImageView) {
            Glide.with(itemView.context)
                .load(Uri.parse(image))
                .error(R.drawable.google_icon)
                .into(imageView)
        }

        private fun changePlace(position: Int) {
            if (position % 2 == 0) {
                binding.startImageItem.visibility = View.GONE
                binding.startTextNameItem.visibility = View.GONE
                binding.endImageItem.visibility = View.VISIBLE
                binding.endTextNameItem.visibility = View.VISIBLE
            }
        }
    }
}