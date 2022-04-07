package ru.castprograms.hackeducation.ui.main.skills

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.castprograms.hackeducation.R
import ru.castprograms.hackeducation.databinding.ItemTopSkillBinding
import ru.castprograms.hackeducation.tools.Achievement

class SkillsAdapter :
    RecyclerView.Adapter<SkillsAdapter.ViewHolderRealize>() {
    var skills = listOf<Pair<String, Achievement>>()

    fun setData(newList: List<Pair<String, Achievement>>) {
            val diffCallback = object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return skills.size
                }

                override fun getNewListSize(): Int {
                    return newList.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return skills[oldItemPosition].first == newList[newItemPosition].first
                }

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    return skills[oldItemPosition].second == newList[newItemPosition].second
                }
            }
            DiffUtil.calculateDiff(diffCallback, true).dispatchUpdatesTo(this)
            skills = newList
        }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): SkillsAdapter.ViewHolderRealize {
        return ViewHolderRealize(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_top_skill, parent, false))
    }

    override fun onBindViewHolder(holder: SkillsAdapter.ViewHolderRealize, position: Int) {
        holder.onBing(skills[position].second, position)
    }

    override fun getItemCount(): Int = skills.size

    inner class ViewHolderRealize(item: View) : RecyclerView.ViewHolder(item) {
        val binding = ItemTopSkillBinding.bind(itemView)
        fun onBing(skill: Achievement, position: Int) {
            Log.d("FIRE", skill.skillImg)
            binding.skillName.text = skill.skillName
            Glide.with(itemView.context)
                .load(skill.skillImg)
                .into(binding.imageSkillItem)
        }
    }
}
