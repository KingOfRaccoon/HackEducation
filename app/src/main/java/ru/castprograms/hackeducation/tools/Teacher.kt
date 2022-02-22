package ru.castprograms.hackeducation.tools

data class Teacher(val img: String = "", val name: String = "", val surname: String = "", val skills : List<Skill> = listOf()){

    fun fullName() = "$name $surname"
}