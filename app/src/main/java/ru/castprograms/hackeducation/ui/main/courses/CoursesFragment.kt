package ru.castprograms.hackeducation.ui.main.courses

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.castprograms.hackeducation.R
import ru.castprograms.hackeducation.databinding.FragmentCoursesBinding
import kotlin.math.ceil
import kotlin.math.round

class CoursesFragment : Fragment(R.layout.fragment_courses) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCoursesBinding.bind(view)
        binding.recyclerCourses.adapter = MapCourseAdapter()
        binding.root.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
                if (startId == R.id.start_courses)
                    removeTextFromButton(binding, progress)
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                binding.recyclerCourses.layoutManager.let { it as CoursesLinearLayoutManager }
                    .isScrollEnabled = currentId != R.id.start_courses
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
            }
        })

        binding.recyclerCourses.setOnTouchListener { _, event ->
            if (!binding.recyclerCourses.canScrollVertically(1)) {
                binding.root.onTouchEvent(event)
            }
            return@setOnTouchListener false
        }
        binding.recyclerCourses.layoutManager = CoursesLinearLayoutManager(requireContext()).apply {
            reverseLayout = true
            isScrollEnabled = false
        }
        binding.recyclerCourses.addItemDecoration(
            CustomItemDecorator(requireContext().getDrawable(R.drawable.vector)!!)
        )
//        binding.recyclerCourses.addOnScrollListener(object: RecyclerView.OnScrollListener(){
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                println(dy)
//            }
//        })
    }

    private fun removeTextFromButton(binding: FragmentCoursesBinding, progress: Float) {
        val textButton = requireContext().resources.getString(R.string.all_skills)
        binding.buttonShowAllSkills.let {
            it.textSize = 18 * (1 - progress)
            it.text = textButton.take(ceil(textButton.length * (1 - progress)).toInt())
        }
    }
}