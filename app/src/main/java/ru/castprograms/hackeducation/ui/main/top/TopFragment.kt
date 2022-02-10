package ru.castprograms.hackeducation.ui.main.top

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.castprograms.hackeducation.R
import ru.castprograms.hackeducation.databinding.FragmentTopBinding

class TopFragment: Fragment(R.layout.fragment_top) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentTopBinding.bind(view)
    }
}