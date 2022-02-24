package ru.castprograms.hackeducation.ui.main.skills

import androidx.lifecycle.ViewModel
import ru.castprograms.hackeducation.firebase.DataUserFirebase

class SkillsViewModel(
    private val dataUserFirebase: DataUserFirebase
): ViewModel()  {
    fun getAllSkills() = dataUserFirebase.getAllSkills()
}