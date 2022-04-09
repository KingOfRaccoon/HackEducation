package ru.castprograms.hackeducation.ui.main.showcourse

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import ru.castprograms.hackeducation.R
import ru.castprograms.hackeducation.databinding.FragmentShowCourseBinding
import ru.castprograms.hackeducation.tools.TypeData
import ru.castprograms.hackeducation.ui.main.createcourse.items.PathCourseHeaderItem
import ru.castprograms.hackeducation.ui.main.createcourse.items.PathCourseListItem
import ru.castprograms.hackeducation.ui.main.createcourse.items.PathCourseTextItem

class ShowCourseFragment: Fragment(R.layout.fragment_show_course) {
    private val course by lazy { ShowCourseFragmentArgs.fromBundle(requireArguments()).course }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentShowCourseBinding.bind(view)
        val adapter = GroupAdapter<GroupieViewHolder>()
        course.items.forEach {
            when(it.typeData){
                TypeData.Text -> adapter.add(
                    PathCourseTextItem(it as PathCourseTextItem.PathCourseTextData)
                )
                TypeData.Header -> adapter.add(
                    PathCourseHeaderItem(it as PathCourseHeaderItem.PathCourseHeaderData)
                )
                TypeData.List -> adapter.add(
                    PathCourseListItem(it as PathCourseListItem.PathCourseListData)
                )
                TypeData.SubHeader -> {
//                    adapter.add()
                }
            }
        }
        binding.recyclerCourse.adapter = adapter
    }
}