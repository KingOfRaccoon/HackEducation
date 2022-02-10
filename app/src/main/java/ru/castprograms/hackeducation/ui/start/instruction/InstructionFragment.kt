package ru.castprograms.hackeducation.ui.start.instruction

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.castprograms.hackeducation.R
import ru.castprograms.hackeducation.databinding.FragmentInstructionBinding

class InstructionFragment: Fragment(R.layout.fragment_instruction) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentInstructionBinding.bind(view)
        binding.buttonEndInstruction.setOnClickListener {
            findNavController().navigate(R.id.action_instructionFragment_to_authorizationFragment)
        }
    }
}