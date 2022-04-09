package ru.castprograms.hackeducation.ui.main.skills

import androidx.lifecycle.ViewModel
import ru.castprograms.hackeducation.firebase.DataUserFirebase
import ru.castprograms.hackeducation.tools.ViewModelWithGoogleAccount

class SkillsViewModel(
    dataUserFirebase: DataUserFirebase
): ViewModelWithGoogleAccount(dataUserFirebase)  {
    fun getAllSkills() = dataUserFirebase.getAllSkills()
    fun getAllTeachers() = dataUserFirebase.getAllTeachers()
}