package ru.castprograms.hackeducation.ui.main.skills

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.castprograms.hackeducation.R
import ru.castprograms.hackeducation.databinding.ItemTopSkillBinding
import ru.castprograms.hackeducation.tools.Skill

class RecyclerViewSkills : RecyclerView.Adapter<RecyclerViewSkills.ViewHolderRealize>() {
    private val skills: List<Skill> = listOf(
        Skill("Общения в Интернете", 90, R.color.c_red),
        Skill("Анализ резултатов", 87,  R.color.c_yellow),
        Skill("Работа с информацией", 66, R.color.c_blue),
        Skill("Интерактивные платформы", 59,  R.color.c_red),
        Skill("Тайм-менеджемент", 40, R.color.c_yellow),
        Skill("Правильные дедлайны", 31, R.color.c_blue),
        Skill("Еще один навык", 9,  R.color.c_red),
        Skill("Анализ резултатов", 87,  R.color.c_yellow),
        Skill("Работа с информацией", 66, R.color.c_blue),
        Skill("Интерактивные платформы", 59,  R.color.c_red),
        Skill("Тайм-менеджемент", 40, R.color.c_yellow),
        Skill("Правильные дедлайны", 31, R.color.c_blue),
        Skill("Еще один навык", 9,  R.color.c_red),
        Skill("Анализ резултатов", 87,  R.color.c_yellow),
        Skill("Работа с информацией", 66, R.color.c_blue),
        Skill("Интерактивные платформы", 59,  R.color.c_red),
        Skill("Тайм-менеджемент", 40, R.color.c_yellow),
        Skill("Правильные дедлайны", 31, R.color.c_blue),
        Skill("Еще один навык", 9,  R.color.c_red),
    )

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewSkills.ViewHolderRealize {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_top_skill, parent, false)

        return ViewHolderRealize(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewSkills.ViewHolderRealize, position: Int) {
        holder.onBing(skills[position])
    }

    override fun getItemCount(): Int = skills.size

    inner class ViewHolderRealize(item: View) : RecyclerView.ViewHolder(item) {
        val binding = ItemTopSkillBinding.bind(itemView)
        fun onBing(skill: Skill) {
            binding.skillName.text = skill.skillName
            binding.skillProc.text = "${skill.skillPercent}%"
            binding.skillTopProc.progress = skill.skillPercent
            binding.skillTopProc.setIndicatorColor(skill.color)
//            binding.skillTopProc.indicatorColor.iterator(skill.color)
        }
    }
}
