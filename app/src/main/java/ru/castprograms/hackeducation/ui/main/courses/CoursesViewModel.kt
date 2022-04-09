package ru.castprograms.hackeducation.ui.main.courses

import ru.castprograms.hackeducation.firebase.DataUserFirebase
import ru.castprograms.hackeducation.tools.ViewModelWithGoogleAccount

class CoursesViewModel(dataUserFirebase: DataUserFirebase) :
    ViewModelWithGoogleAccount(dataUserFirebase) {
    fun getAllTeachers() = dataUserFirebase.getAllTeachers()
    fun getAllSkills() = dataUserFirebase.getAllSkills()
}