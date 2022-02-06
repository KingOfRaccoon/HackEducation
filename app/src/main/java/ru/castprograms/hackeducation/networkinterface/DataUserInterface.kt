package ru.castprograms.hackeducation.networkinterface

import androidx.lifecycle.MutableLiveData
import ru.castprograms.hackeducation.tools.Resource
import ru.castprograms.hackeducation.tools.Teacher

interface DataUserInterface {
    val currentTeacher: MutableLiveData<Resource<Teacher>>
    fun getTeacher(idTeacher: String): MutableLiveData<Resource<Teacher>>
    fun addTeacher(idTeacher: String, teacher: Teacher): MutableLiveData<Resource<String>>
    fun loadTeacher(idTeacher: String)
}