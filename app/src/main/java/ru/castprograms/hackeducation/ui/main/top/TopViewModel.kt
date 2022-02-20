package ru.castprograms.hackeducation.ui.main.top

import androidx.lifecycle.ViewModel
import ru.castprograms.hackeducation.firebase.DataUserFirebase

class TopViewModel(
    private val dataUserFirebase: DataUserFirebase) : ViewModel() {
    fun getAllTeachers() = dataUserFirebase.getAllTeachers()
}