package ru.castprograms.hackeducation.tools

data class Teacher(val img: String = "", val name: String = "", val surname: String = ""){

    fun fullName() = "$name $surname"
}