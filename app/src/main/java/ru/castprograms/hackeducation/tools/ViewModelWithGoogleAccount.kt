package ru.castprograms.hackeducation.tools

import android.content.Context
import androidx.lifecycle.ViewModel
import ru.castprograms.hackeducation.firebase.DataUserFirebase

open class ViewModelWithGoogleAccount(private val dataUserFirebase: DataUserFirebase): ViewModel() {

    fun getGoogleAccount(context: Context) = dataUserFirebase.getGoogleSignInAccount(context)
    fun getTeacher() = dataUserFirebase.currentTeacher
}