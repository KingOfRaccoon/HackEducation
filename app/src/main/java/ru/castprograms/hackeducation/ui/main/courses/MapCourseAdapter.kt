package ru.castprograms.hackeducation.ui.main.courses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.castprograms.hackeducation.R
import ru.castprograms.hackeducation.databinding.ItemMapCourseBinding

class MapCourseAdapter: RecyclerView.Adapter<MapCourseAdapter.MapCourseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapCourseViewHolder {
        return MapCourseViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_map_course, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MapCourseViewHolder, position: Int) {
        holder.bind(position)
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = 20

    inner class MapCourseViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = ItemMapCourseBinding.bind(view)
        fun bind(position: Int){
            changePlace(position)
        }

        private fun changePlace(position: Int){
            if (position % 2 == 0){
                binding.startImageItem.visibility = View.GONE
                binding.startTextNameItem.visibility = View.GONE
                binding.endImageItem.visibility = View.VISIBLE
                binding.endTextNameItem.visibility = View.VISIBLE
            }
        }
    }
}