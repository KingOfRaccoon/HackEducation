package ru.castprograms.hackeducation.ui.main.courses

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.castprograms.hackeducation.R
import ru.castprograms.hackeducation.databinding.FragmentCoursesBinding

class CoursesFragment: Fragment(R.layout.fragment_courses) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCoursesBinding.bind(view)
        binding.recyclerCourses.adapter = MapCourseAdapter()
        binding.recyclerCourses.layoutManager = LinearLayoutManager(requireContext()).apply {
            reverseLayout = true
        }
        binding.recyclerCourses.addItemDecoration(
            CustomItemDecorator(requireContext().getDrawable(R.drawable.ic_untitled_2_copy)!!)
        )
    }
}