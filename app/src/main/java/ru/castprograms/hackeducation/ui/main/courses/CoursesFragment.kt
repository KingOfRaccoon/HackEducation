package ru.castprograms.hackeducation.ui.main.courses

import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.castprograms.hackeducation.R
import ru.castprograms.hackeducation.databinding.FragmentCoursesBinding
import ru.castprograms.hackeducation.tools.Resource

class CoursesFragment : Fragment(R.layout.fragment_courses) {
    val coursesViewModel: CoursesViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCoursesBinding.bind(view)
        setUserData(binding)
        binding.recyclerCourses.adapter = MapCourseAdapter()
        binding.buttonRecycler.setOnClickListener {
            if (binding.root.currentState == R.id.start_courses)
                binding.root.transitionToEnd()
            else
                binding.root.transitionToStart()
        }

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
                else
                    removeTextFromButton(binding, 1 - progress)
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                binding.recyclerCourses.layoutManager.let { it as CoursesLinearLayoutManager }
                    .isScrollEnabled = currentId != R.id.start_courses

                if (currentId == R.id.start_courses)
                    removeTextFromButton(binding, 0f)
                else
                    removeTextFromButton(binding, 1f)
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

        binding.containerButtonShowAllSkills.setOnClickListener {
            findNavController().navigate(R.id.action_coursesFragment_to_skillsFragment)
        }

        binding.cardUser.setOnClickListener {
            findNavController().navigate(R.id.action_coursesFragment_to_topFragment)
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_coursesFragment_to_createCourseFragment)
        }
    }

    private fun setUserData(binding: FragmentCoursesBinding) {
        coursesViewModel.getTeacher().observe(viewLifecycleOwner){
            when(it){
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    it.data?.let {
                        Glide.with(requireContext())
                            .load(it.img)
                            .placeholder(R.drawable.test_image_for_user)
                            .into(binding.imageUser)

                        binding.textNameUser.text = it.fullName()
                    }
                }
            }
        }
    }

    private fun removeTextFromButton(binding: FragmentCoursesBinding, progress: Float) {
        binding.containerRecyclerCourses.cardElevation = 10 * (1 - progress)
        binding.containerRecyclerCourses.radius = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            30f,
            requireActivity().resources.displayMetrics
        ) * (1 - progress)
        binding.buttonRecycler.elevation = 10 * (1 - progress)
    }
}