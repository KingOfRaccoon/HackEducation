package ru.castprograms.hackeducation.ui.main.createcourse.items

import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import ru.castprograms.hackeducation.R
import ru.castprograms.hackeducation.databinding.PathCourseHeaderBinding

class PathCourseHeaderItem(val pathCourseHeaderData: PathCourseHeaderData): Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val binding = PathCourseHeaderBinding.bind(viewHolder.itemView)
        binding.textThemeNumber.text = "Тема ${pathCourseHeaderData.themeNumber}"
        binding.textNameTheme.text = pathCourseHeaderData.themeText
    }

    override fun getLayout() = R.layout.path_course_header

    class PathCourseHeaderData(val themeNumber: Int = 0, val themeText: String = "")
}