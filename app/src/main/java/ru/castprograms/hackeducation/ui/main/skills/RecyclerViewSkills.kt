package ru.castprograms.hackeducation.ui.main.skills

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import ru.castprograms.hackeducation.R
import ru.castprograms.hackeducation.databinding.ItemTopSkillBinding
import ru.castprograms.hackeducation.tools.Skill

class RecyclerViewSkills :
    RecyclerView.Adapter<RecyclerViewSkills.ViewHolderRealize>() {
    var skills = listOf<Pair<String, Skill>>()
        set(value) {
            val diffCallback = object : DiffUtil.Callback() {
                override fun getOldListSize(): Int {
                    return field.size
                }

                override fun getNewListSize(): Int {
                    return value.size
                }

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return field[oldItemPosition].first == value[newItemPosition].first
                }

                override fun areContentsTheSame(
                    oldItemPosition: Int,
                    newItemPosition: Int
                ): Boolean {
                    return field[oldItemPosition].second == value[newItemPosition].second
                }
            }
            DiffUtil.calculateDiff(diffCallback, true)
            field = value
        }

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): RecyclerViewSkills.ViewHolderRealize {
        return ViewHolderRealize(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_top_skill, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerViewSkills.ViewHolderRealize, position: Int) {
        holder.onBing(skills[position].second, position)
    }

    override fun getItemCount(): Int = skills.size

    inner class ViewHolderRealize(item: View) : RecyclerView.ViewHolder(item) {
        val binding = ItemTopSkillBinding.bind(itemView)
        fun onBing(skill: Skill, position: Int) {
            Log.d("FIRE", skill.skillImg)
            binding.skillName.text = skill.skillName
            Glide.with(itemView.context)
                .load(skill.skillImg)
                .error(R.drawable.test_image_for_user)
                .transform(CenterCrop())
                .into(binding.imageSkillItem)
        }
    }
}
