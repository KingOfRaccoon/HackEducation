package ru.castprograms.hackeducation.ui.start.instruction

import ViewPager2FragmentAdapter
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.castprograms.hackeducation.R
import ru.castprograms.hackeducation.databinding.FragmentInstructionAllBinding

class InstructionAllFragment : Fragment(R.layout.fragment_instruction_all) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentInstructionAllBinding.bind(view)

        val dotsIndicator = binding.dotsIndicator
        val viewPager2 = binding.pager
        viewPager2.adapter = ViewPager2FragmentAdapter(this)
        dotsIndicator.setViewPager2(viewPager2)

    }
}