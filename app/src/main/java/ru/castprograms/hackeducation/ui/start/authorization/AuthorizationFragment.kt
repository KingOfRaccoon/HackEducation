package ru.castprograms.hackeducation.ui.start.authorization

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import ru.castprograms.hackeducation.R
import ru.castprograms.hackeducation.databinding.FragmentAuthorizationBinding

class AuthorizationFragment: Fragment(R.layout.fragment_authorization) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentAuthorizationBinding.bind(view)
    }
}