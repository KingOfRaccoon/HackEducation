package ru.castprograms.hackeducation.ui.start.registration

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.castprograms.hackeducation.R
import ru.castprograms.hackeducation.databinding.FragmentRegistrationBinding

class RegistrationFragment: Fragment(R.layout.fragment_registration) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentRegistrationBinding.bind(view)
    }
}