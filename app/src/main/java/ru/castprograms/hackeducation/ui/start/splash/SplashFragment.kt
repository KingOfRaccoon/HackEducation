package ru.castprograms.hackeducation.ui.start.splash

import android.animation.Animator
import android.os.Bundle
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginStart
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.jaredrummler.android.widget.AnimatedSvgView.STATE_FINISHED
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.castprograms.hackeducation.R
import ru.castprograms.hackeducation.StartActivity
import ru.castprograms.hackeducation.databinding.FragmentSplashBinding
import ru.castprograms.hackeducation.tools.Resource
import yanzhikai.textpath.PathAnimatorListener

class SplashFragment : Fragment(R.layout.fragment_splash) {
    lateinit var binding: FragmentSplashBinding
    private val splashViewModel: SplashViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSplashBinding.bind(view)
        splashViewModel.getGoogleAccount(requireContext())?.id?.let {
            splashViewModel.loadPerson(it)
        }
        binding.animatedSvgView.start()
        binding.animatedSvgView.setOnStateChangeListener {
            if (STATE_FINISHED == it)
                setupGoogleAccountLoad()
        }
    }

    private fun setupGoogleAccountLoad() {
        val id = splashViewModel.getGoogleAccount(requireContext())?.id
        if (id != null)
            splashViewModel.getTeacher().observe(viewLifecycleOwner) {
                when (it) {
                    is Resource.Error ->
                        findNavController()
                            .navigate(R.id.action_splashFragment_to_registrationFragment)
                    is Resource.Loading -> {}
                    is Resource.Success ->
                        (requireActivity() as StartActivity).goToMainActivity()
                }
            }
        else
            enableAuthorizationUI()
    }

    private fun enableAuthorizationUI() {
        binding.root.transitionToEnd()
    }
}