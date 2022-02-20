package ru.castprograms.hackeducation.ui.main.top

import android.os.Bundle
import android.view.FrameMetrics.ANIMATION_DURATION
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import at.wirecube.additiveanimations.additive_animator.AdditiveAnimator
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.castprograms.hackeducation.R
import ru.castprograms.hackeducation.databinding.FragmentTopBinding
import ru.castprograms.hackeducation.tools.Resource
import ru.castprograms.hackeducation.tools.Teacher

class TopFragment: Fragment(R.layout.fragment_top) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentTopBinding.bind(view)
        val viewModel: TopViewModel by viewModel()

        val adapter = RecyclerViewTop()
        binding.recyclerViewTop.adapter = adapter
        binding.recyclerViewTop.layoutManager = LinearLayoutManager(requireContext())

        binding.backToCourseFromTop.setOnClickListener {
            findNavController().navigate(R.id.action_topFragment_to_coursesFragment)
        }
        viewModel.getAllTeachers().observe(viewLifecycleOwner){
            when(it){
                is Resource.Error -> { }

                is Resource.Loading -> { }

                is Resource.Success -> {
                    if (it.data != null){
                        adapter.teachers = it.data.toMutableList()
                    }
                }
            }
        }
    }
}