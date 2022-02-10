package ru.castprograms.hackeducation.ui.start.authorization

import androidx.lifecycle.ViewModel
import ru.castprograms.hackeducation.firebase.DataUserFirebase
import ru.castprograms.hackeducation.tools.ViewModelWithGoogleAccount

class AuthorizationViewModel(dataUserFirebase: DataUserFirebase) :
    ViewModelWithGoogleAccount(dataUserFirebase) {
    fun loadPerson(idTeacher: String){
        dataUserFirebase.loadTeacher(idTeacher)
    }
}