package ru.castprograms.hackeducation.ui.start.instruction

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.castprograms.hackeducation.R
import ru.castprograms.hackeducation.databinding.FragmentInstruction1Binding

class InstructionFragment1: Fragment(R.layout.fragment_instruction1) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentInstruction1Binding.bind(view)
//        binding.skip1.setOnClickListener {
//            findNavController().navigate(R.id.action_instructionAllFragment_to_registrationFragment)
//        }
    }
}