package ru.castprograms.hackeducation.ui.main.top

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.google.firebase.firestore.auth.User
import ru.castprograms.hackeducation.R
import ru.castprograms.hackeducation.databinding.ItemTopBinding
import ru.castprograms.hackeducation.tools.Teacher

class RecyclerViewTop() :
    RecyclerView.Adapter<RecyclerViewTop.ViewHolderRealize>() {

    var teachers = mutableListOf<Teacher>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderRealize {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_top, parent, false)

        return ViewHolderRealize(view)
    }

    override fun onBindViewHolder(holder: ViewHolderRealize, position: Int) {
        holder.onBing(teachers[position])
    }

    override fun getItemCount(): Int = teachers.size

    inner class ViewHolderRealize(item: View) : RecyclerView.ViewHolder(item) {
        val binding = ItemTopBinding.bind(itemView)
        fun onBing(teacher: Teacher) {
            binding.textNameTeacherItem.text = teachers[position].fullName() //????? or teacher.fullName() ????
            Glide.with(itemView.context)
                .load(teacher.img)
                .transform(CenterCrop())
                .into(binding.imageTeacherItem)
            binding.cPosition.text = (position + 1).toString()
        }
    }
}