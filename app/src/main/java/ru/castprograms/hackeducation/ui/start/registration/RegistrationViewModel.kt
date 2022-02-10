package ru.castprograms.hackeducation.ui.start.registration

import ru.castprograms.hackeducation.firebase.DataUserFirebase
import ru.castprograms.hackeducation.tools.Teacher
import ru.castprograms.hackeducation.tools.ViewModelWithGoogleAccount

class RegistrationViewModel(dataUserFirebase: DataUserFirebase) :
    ViewModelWithGoogleAccount(dataUserFirebase) {
    fun registrationTeacher(idTeacher: String, teacher: Teacher) =
        dataUserFirebase.addTeacher(idTeacher, teacher)
}