package ru.castprograms.hackeducation.ui.main.createcourse.items

import android.view.View
import com.bumptech.glide.Glide
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import ru.castprograms.hackeducation.R
import ru.castprograms.hackeducation.databinding.PathCourseTextBinding
import ru.castprograms.hackeducation.tools.TypeData
import ru.castprograms.hackeducation.tools.ui.Data

class PathCourseTextItem(private val pathCourseTextData: PathCourseTextData) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val binding = PathCourseTextBinding.bind(viewHolder.itemView)
        binding.textTitleText.text = pathCourseTextData.title
        binding.textCourse.text = pathCourseTextData.text
        if (pathCourseTextData.image != "")
            Glide.with(binding.root.context)
                .load(pathCourseTextData.image)
                .into(binding.imageTextCourse)
        else
            binding.imageTextCourse.visibility = View.GONE
    }

    override fun getLayout() = R.layout.path_course_text

    class PathCourseTextData(
        title: String = "",
        val text: String = "",
        val image: String = ""
    ) : Data(title, TypeData.Text)
}