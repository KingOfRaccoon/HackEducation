package ru.castprograms.hackeducation.ui.start.registration

import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.MaterialDatePicker
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.castprograms.hackeducation.MainActivity
import ru.castprograms.hackeducation.R
import ru.castprograms.hackeducation.StartActivity
import ru.castprograms.hackeducation.databinding.FragmentRegistrationBinding
import ru.castprograms.hackeducation.tools.Teacher

class RegistrationFragment : Fragment(R.layout.fragment_registration) {
    private val viewModel: RegistrationViewModel by viewModel()
    private lateinit var binding: FragmentRegistrationBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegistrationBinding.bind(view)
        setTextWatchers()
        binding.datePicker.setOnClickListener {
            binding.datePicker.error = null
            createDatePicker {
                val date = DateFormat.format("dd.MM.yyyy", it).toString()
                binding.date.text = date
            }.show(childFragmentManager, "datePicker")
        }

        binding.buttonRegistration.setOnClickListener {
            val googleAccountId = viewModel.getGoogleAccount(requireContext())
            if (validate() && googleAccountId != null) {
                viewModel.registrationTeacher(
                    googleAccountId.id.toString(),
                    Teacher(
                        googleAccountId.photoUrl.toString(),
                        binding.nameText.text.toString(),
                        binding.surnameText.text.toString()
                    )
                )

                (requireActivity() as StartActivity).goToMainActivity()
            }
        }
    }

    private fun validate(): Boolean {
        val listCheck = mutableListOf<Boolean>()
        binding.nameText.text?.let {
            if (it.trim().isEmpty()) {
                binding.errorName.visibility = View.VISIBLE
                binding.errorName.text = "Введите имя"
                listCheck.add(false)
            }
        }
        binding.surnameText.text?.let {
            if (it.trim().isEmpty()) {
                binding.errorSurname.visibility = View.VISIBLE
                binding.errorSurname.text = "Введите фамилию"
                listCheck.add(false)
            }
        }
        binding.date.text?.let {
            if (it.trim().isEmpty()) {
                binding.datePicker.error = "Укажите дату рождения"
                listCheck.add(false)
            }
        }
        return false !in listCheck
    }

    private fun createDatePicker(date: (millis: Long) -> Unit): MaterialDatePicker<Long> {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Выбор даты рождения")
            .build()
        datePicker.addOnPositiveButtonClickListener {
            date(it)
        }
        return datePicker
    }

    private fun setTextWatchers() {
//        binding.nameText.addTextChangedListener {
//            binding.nameTextContainer.error = null
//        }
//
//        binding.surnameText.addTextChangedListener {
//            binding.surnameTextContainer.error = null
//        }
    }
}