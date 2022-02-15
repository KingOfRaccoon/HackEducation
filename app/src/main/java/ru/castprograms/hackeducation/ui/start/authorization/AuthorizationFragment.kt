package ru.castprograms.hackeducation.ui.start.authorization

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.view.animation.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.castprograms.hackeducation.R
import ru.castprograms.hackeducation.StartActivity
import ru.castprograms.hackeducation.databinding.FragmentAuthorizationBinding
import ru.castprograms.hackeducation.tools.Resource
import java.util.*
import kotlin.concurrent.timerTask


class AuthorizationFragment : Fragment(R.layout.fragment_authorization) {
    val viewModel: AuthorizationViewModel by viewModel()
    lateinit var binding: FragmentAuthorizationBinding
    val googleAuth = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK) {
            GoogleSignIn.getSignedInAccountFromIntent(it.data).addOnCompleteListener {
                if (it.isSuccessful) {
                    viewModel.loadPerson(it.result.id.toString())
                    viewModel.getTeacher().observe(viewLifecycleOwner) {
                        when (it) {
                            is Resource.Error ->
                                binding.icon.animation.repeatCount = 0
                            is Resource.Loading -> {}
                            is Resource.Success -> {
                                (requireActivity() as StartActivity).goToMainActivity()
                            }
                        }
                    }
                } else {

                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthorizationBinding.bind(view)

        binding.enter.setOnClickListener {
            googleAuth.launch(
                Intent(
                    GoogleSignIn.getClient(
                        requireContext(),
                        GoogleSignInOptions.DEFAULT_SIGN_IN
                    ).signInIntent
                )
            )
            binding.root.transitionToEnd()
            binding.textEnter.startAnimation(
                AnimationUtils.loadAnimation(requireContext(), R.anim.hide)
            )
            binding.textEnter.animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(p0: Animation?) {}

                override fun onAnimationEnd(p0: Animation?) {
                    binding.textEnter.visibility = View.GONE
                }

                override fun onAnimationRepeat(p0: Animation?) {}
            })

            binding.icon.startAnimation(
                AnimationUtils.loadAnimation(requireContext(), R.anim.rotate)
            )
            binding.icon.animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(p0: Animation?) {}

                override fun onAnimationEnd(p0: Animation?) {
                    setTimerToGoRegistr()
                }

                override fun onAnimationRepeat(p0: Animation?) {}
            })
        }
    }

    private fun setTimerToGoRegistr() {
        Timer().schedule(
            timerTask {
                binding.enter.post {
                    findNavController()
                        .navigate(R.id.action_instructionAllFragment_to_registrationFragment)
                }
            }, 300
        )
    }
}