package ru.castprograms.hackeducation.ui.start.instruction

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.castprograms.hackeducation.R
import ru.castprograms.hackeducation.databinding.FragmentInstruction2Binding

class InstructionFragment2 : Fragment(R.layout.fragment_instruction2) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentInstruction2Binding.bind(view)
//        binding.skip2.setOnClickListener {
//            findNavController().navigate(R.id.action_instructionAllFragment_to_registrationFragment)
//        }
    }
}