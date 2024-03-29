package ru.castprograms.hackeducation.ui.main.skills

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.castprograms.hackeducation.R
import ru.castprograms.hackeducation.databinding.FragmentSkillsBinding
import ru.castprograms.hackeducation.tools.Resource

class SkillsFragment : Fragment(R.layout.fragment_skills) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentSkillsBinding.bind(view)
        val viewModel: SkillsViewModel by viewModel()

        val adapter = SkillsAdapter()
        binding.recyclerViewSkills.adapter = adapter
        binding.recyclerViewSkills.layoutManager = LinearLayoutManager(requireContext())
        binding.containerButtonHideSkills.setOnClickListener {
//            findNavController().navigate(R.id.action_skillsFragment_to_coursesFragment)
        }

        viewModel.getAllTeachers().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Error -> {}

                is Resource.Loading -> {}

                is Resource.Success -> {
                    it.data?.indexOf(it.data
                        .find { it.first == viewModel.getGoogleAccount(requireContext())?.id })
                        ?.let { binding.positionTeachersInTop.text = (it+1).toString() }
                }
            }
        }

        viewModel.getAllSkills().observe(viewLifecycleOwner) {
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
}