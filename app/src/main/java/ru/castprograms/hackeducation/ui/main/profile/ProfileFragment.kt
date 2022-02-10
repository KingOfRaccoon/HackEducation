package ru.castprograms.hackeducation.ui.main.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.castprograms.hackeducation.R
import ru.castprograms.hackeducation.databinding.FragmentProfileBinding

class ProfileFragment: Fragment(R.layout.fragment_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentProfileBinding.bind(view)
    }
}