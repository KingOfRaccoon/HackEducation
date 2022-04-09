package ru.castprograms.hackeducation.ui.main.createcourse.items

import android.net.Uri
import android.view.View
import com.bumptech.glide.Glide
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import ru.castprograms.hackeducation.R
import ru.castprograms.hackeducation.databinding.PathCourseHeaderBinding
import ru.castprograms.hackeducation.tools.TypeData
import ru.castprograms.hackeducation.tools.ui.Data

class PathCourseHeaderItem(private val pathCourseHeaderData: PathCourseHeaderData) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        val binding = PathCourseHeaderBinding.bind(viewHolder.itemView)
        binding.textThemeNumber.text = pathCourseHeaderData.themeAndNumber()
        binding.textNameTheme.text = pathCourseHeaderData.title
        if (pathCourseHeaderData.image != "")
            Glide.with(viewHolder.containerView.context)
                .load(Uri.parse(pathCourseHeaderData.image))
                .into(binding.imageTitleTheme)
        else
            binding.imageTitleTheme.visibility = View.GONE
    }

    override fun getLayout() = R.layout.path_course_header

    class PathCourseHeaderData(
        var themeNumber: Int = 0,
        themeText: String = "",
        val image: String = ""
    ): Data(themeText, TypeData.Header){
        fun themeAndNumber() = "Тема $themeNumber"
    }
}