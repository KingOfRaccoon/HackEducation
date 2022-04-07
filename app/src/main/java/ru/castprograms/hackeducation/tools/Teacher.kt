package ru.castprograms.hackeducation.tools

data class Teacher(
    val img: String = "",
    val name: String = "",
    val surname: String = "",
    val achievements: MutableMap<Achievement, Int> = mutableMapOf()
) {

    fun fullName() = "$name\n$surname"
}