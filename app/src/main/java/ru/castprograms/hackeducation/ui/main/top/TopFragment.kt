package ru.castprograms.hackeducation.ui.main.top

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.castprograms.hackeducation.R
import ru.castprograms.hackeducation.databinding.FragmentTopBinding
import ru.castprograms.hackeducation.tools.Resource

class TopFragment: Fragment(R.layout.fragment_top) {
    val viewModel: TopViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentTopBinding.bind(view)

        val adapter = TopAdapter()
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
                        adapter.teachers = it.data.toList()
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}