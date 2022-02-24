package ru.castprograms.hackeducation.ui.main.top

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import ru.castprograms.hackeducation.R
import ru.castprograms.hackeducation.databinding.ItemTopBinding
import ru.castprograms.hackeducation.tools.Teacher

class TopAdapter() :
    RecyclerView.Adapter<TopAdapter.TopViewHolder>() {
    var teachers = listOf<Pair<String,Teacher>>()
    set(value) {
        val diffCallback = object: DiffUtil.Callback(){
            override fun getOldListSize(): Int {
                return field.size
            }

            override fun getNewListSize(): Int {
                return value.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return field[oldItemPosition].first == value[newItemPosition].first
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return field[oldItemPosition].second == value[newItemPosition].second
            }
        }
        DiffUtil.calculateDiff(diffCallback, true)
        field = value
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopViewHolder {
        return TopViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_top, parent, false))
    }

    override fun onBindViewHolder(holder: TopViewHolder, position: Int) {
        holder.onBing(teachers[position].second, position)
    }

    override fun getItemCount(): Int = teachers.size

    inner class TopViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = ItemTopBinding.bind(itemView)

        fun onBing(teacher: Teacher, position: Int) {
            binding.textNameTeacherItem.text = teacher.fullName()
            Glide.with(itemView.context)
                .load(teacher.img)
                .transform(CenterCrop())
                .error(R.drawable.test_image_for_user)
                .into(binding.imageTeacherItem)
            binding.cPosition.text = (position + 1).toString()
        }
    }
}