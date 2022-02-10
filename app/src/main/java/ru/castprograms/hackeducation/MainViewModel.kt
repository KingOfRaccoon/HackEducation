package ru.castprograms.hackeducation

import ru.castprograms.hackeducation.firebase.DataUserFirebase
import ru.castprograms.hackeducation.tools.ViewModelWithGoogleAccount

class MainViewModel(dataUserFirebase: DataUserFirebase) :
    ViewModelWithGoogleAccount(dataUserFirebase) {
    fun loadPerson(idTeacher: String){
        dataUserFirebase.loadTeacher(idTeacher)
    }
}