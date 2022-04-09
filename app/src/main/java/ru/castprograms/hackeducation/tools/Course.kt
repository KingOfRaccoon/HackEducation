package ru.castprograms.hackeducation.tools

import android.net.Uri
import ru.castprograms.hackeducation.tools.ui.Data
import java.io.Serializable

data class Course(
    val image: String = Uri.EMPTY.toString(),
    val name: String = "",
    var items: List<Data> = listOf()
): Serializable