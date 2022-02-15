package ru.castprograms.hackeducation.ui.start.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.jaredrummler.android.widget.AnimatedSvgView.STATE_FINISHED
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.castprograms.hackeducation.R
import ru.castprograms.hackeducation.StartActivity
import ru.castprograms.hackeducation.databinding.FragmentSplashBinding
import ru.castprograms.hackeducation.tools.Resource
import java.util.*
import kotlin.concurrent.timerTask

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
                        Timer().schedule(
                            timerTask {
                                binding.buttonStart.post {
                                    findNavController()
                                        .navigate(R.id.action_splashFragment_to_registrationFragment)
                                }
                            }, 300
                        )

                    is Resource.Loading -> {}
                    is Resource.Success ->
                        Timer().schedule(
                            timerTask {
                                binding.buttonStart.post {
                                    (requireActivity() as StartActivity).goToMainActivity()
                                }
                            }, 300
                        )

                }
            }
        else
            enableAuthorizationUI()
    }

    private fun enableAuthorizationUI() {
        binding.root.transitionToEnd()
        binding.buttonStart.setOnClickListener {
            findNavController().navigate(R.id.action_splashFragment_to_instructionAllFragment)
        }
    }
}