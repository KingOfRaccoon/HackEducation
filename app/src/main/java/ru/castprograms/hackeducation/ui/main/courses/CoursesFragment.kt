package ru.castprograms.hackeducation.ui.main.courses

import android.net.Uri
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.castprograms.hackeducation.R
import ru.castprograms.hackeducation.databinding.FragmentCoursesBinding
import ru.castprograms.hackeducation.tools.Course
import ru.castprograms.hackeducation.tools.Resource
import ru.castprograms.hackeducation.ui.main.createcourse.items.PathCourseHeaderItem
import ru.castprograms.hackeducation.ui.main.createcourse.items.PathCourseTextItem
import ru.castprograms.hackeducation.ui.main.skills.SkillsAdapter

class CoursesFragment : Fragment(R.layout.fragment_courses) {
    private val coursesViewModel: CoursesViewModel by viewModel()
    lateinit var binding: FragmentCoursesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println(savedInstanceState?.getInt("state"))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("state", binding.root.currentState)
    }

    override fun onStop() {
        super.onStop()
        coursesViewModel.state = binding.root.currentState
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCoursesBinding.bind(view)
        binding.root.setTransition(binding.root.currentState, coursesViewModel.state)
        binding.root.transitionToEnd()
        setUserData(binding)
//        binding.root.transitionToState(coursesViewModel.state, 0)
        val mapAdapter = MapCourseAdapter()
        binding.recyclerCourses.adapter = mapAdapter
        mapAdapter.updateCourses(
            listOf(
                "1" to Course(
                    Uri.parse(
                        "android.resource://" + requireContext().packageName
                                + "/" + R.drawable.ic_collaboration
                    ).toString(),
                    "Курс 1",
                    listOf(
                        PathCourseHeaderItem.PathCourseHeaderData(
                            0, "Введение", Uri.parse(
                                "android.resource://" + requireContext().packageName
                                        + "/" + R.drawable.ic_collaboration
                            ).toString()
                        ),
                        PathCourseTextItem.PathCourseTextData()
                    )
                ),
                "2" to Course(
                    Uri.parse(
                        "android.resource://" + requireContext().packageName
                                + "/" + R.drawable.ic_collaborative
                    ).toString(),
                    "Курс 2"
                )
            )
        )
        binding.buttonRecycler.setOnClickListener {
            if (binding.root.currentState == R.id.start_courses)
                binding.root.transitionToState(R.id.end_courses)
            else
                binding.root.transitionToState(R.id.start_courses)
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
                when (endId) {
                    R.id.end_courses -> removeTextFromButton(binding, progress)
                    R.id.achievement_courses -> reverseImageFromButton(binding, progress)
                    else -> {
                        reverseImageFromButton(binding, 1 - progress)
                        removeTextFromButton(binding, 1 - progress)
                    }
                }
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

//        binding.recyclerCourses.setOnTouchListener { _, event ->
//            if (!binding.recyclerCourses.canScrollVertically(1)
//                && binding.root.currentState != R.id.start_courses
//            ) {
//                binding.root.onTouchEvent(event)
//            }
//            return@setOnTouchListener false
//        }
        binding.recyclerCourses.layoutManager = CoursesLinearLayoutManager(requireContext()).apply {
            reverseLayout = true
            isScrollEnabled = false
        }
        binding.recyclerCourses.addItemDecoration(
            CustomItemDecorator(requireContext().getDrawable(R.drawable.vector)!!)
        )

        binding.containerButtonShowAllSkills.setOnClickListener {
//            findNavController().navigate(R.id.action_coursesFragment_to_skillsFragment)
            if (binding.root.currentState == R.id.start_courses)
                binding.root.transitionToState(R.id.achievement_courses)
            else
                binding.root.transitionToState(R.id.start_courses)
        }

        binding.cardUser.setOnClickListener {
            findNavController().navigate(R.id.action_coursesFragment_to_topFragment)
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_coursesFragment_to_createCourseFragment)
        }

        coursesViewModel.getAllTeachers().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {}

                is Resource.Loading -> {}

                is Resource.Success -> {
                    it.data?.indexOf(it.data
                        .find { it.first == coursesViewModel.getGoogleAccount(requireContext())?.id })
                        ?.let { binding.positionTeachersInTop.text = (it + 1).toString() }
                }
            }
        }
        val adapter = SkillsAdapter()
        binding.recyclerViewSkills.adapter = adapter
        binding.recyclerViewSkills.layoutManager = LinearLayoutManager(requireContext())

        coursesViewModel.getAllSkills().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    if (it.data != null) {
                        adapter.setData(it.data.toMutableList())
                    }
                }
            }
        }
    }

    private fun reverseImageFromButton(binding: FragmentCoursesBinding, progress: Float) {
        binding.imageButtonShowAllSkills.rotationX = 180 * (1 - progress)
    }

    private fun setUserData(binding: FragmentCoursesBinding) {
        coursesViewModel.getTeacher().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    it.data?.let {
                        Glide.with(requireContext())
                            .load(it.img)
                            .error(R.drawable.test_image_for_user)
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