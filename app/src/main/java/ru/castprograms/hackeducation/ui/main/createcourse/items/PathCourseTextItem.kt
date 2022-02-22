package ru.castprograms.hackeducation.ui.main.createcourse.items

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import ru.castprograms.hackeducation.R
import ru.castprograms.hackeducation.databinding.PathCourseTextBinding

class PathCourseTextItem: Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val binding = PathCourseTextBinding.bind(viewHolder.itemView)
    }

    override fun getLayout() = R.layout.path_course_text
}