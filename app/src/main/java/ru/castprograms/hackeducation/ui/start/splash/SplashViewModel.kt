package ru.castprograms.hackeducation.ui.start.splash

import androidx.lifecycle.MutableLiveData
import ru.castprograms.hackeducation.firebase.DataUserFirebase
import ru.castprograms.hackeducation.tools.Resource
import ru.castprograms.hackeducation.tools.Teacher
import ru.castprograms.hackeducation.tools.ViewModelWithGoogleAccount

class SplashViewModel(private val dataUserFirebase: DataUserFirebase) :
    ViewModelWithGoogleAccount(dataUserFirebase) {
    fun loadPerson(idTeacher: String){
        dataUserFirebase.loadTeacher(idTeacher)
    }
}