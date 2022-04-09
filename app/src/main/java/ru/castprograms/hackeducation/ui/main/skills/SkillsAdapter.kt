package ru.castprograms.hackeducation.ui.main.skills

import android.app.AlertDialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.castprograms.hackeducation.R
import ru.castprograms.hackeducation.databinding.ItemTopSkillBinding
import ru.castprograms.hackeducation.databinding.LayoutAddPathCourseTextBinding
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
        return ViewHolderRealize(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_top_skill, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SkillsAdapter.ViewHolderRealize, position: Int) {
        holder.onBing(skills[position].second, position)
    }

    override fun getItemCount(): Int = skills.size

    inner class ViewHolderRealize(item: View) : RecyclerView.ViewHolder(item) {
        val binding = ItemTopSkillBinding.bind(itemView)
        fun onBing(skill: Achievement, position: Int) {
            binding.skillName.text = skill.skillName
            Glide.with(itemView.context)
                .load(skill.skillImg)
                .into(binding.imageSkillItem)
            binding.cardSkill.setOnClickListener {
                showDescAch(itemView.context)
//                val builder = AlertDialog.Builder(itemView.context)
//                with(builder) {
//                    setTitle("Icon and Button Color")
//                    setMessage("We have a message")
//                    setPositiveButton("OK", null)
//                    setNegativeButton("CANCEL", null)
//                    setNeutralButton("NEUTRAL", null)
//                    setPositiveButtonIcon(resources.getDrawable(android.R.drawable.ic_menu_call, theme))
//                    setIcon(resources.getDrawable(android.R.drawable.ic_dialog_alert, theme))
//                }
//                val alertDialog = builder.create()
//                alertDialog.show()
//
//                val button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
//                with(button) {
//                    setBackgroundColor(Color.BLACK)
//                    setPadding(0, 0, 20, 0)
//                    setTextColor(Color.WHITE)
//                }
            }
        }
    }

    private fun createAlertDialog(view: View): AlertDialog {
        return AlertDialog.Builder(view.context).setView(view).create().apply {
            window?.setBackgroundDrawable(ColorDrawable(0))
            show()
        }
    }

    private fun showDescAch(context: Context) {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.layout_desc_achievement, null)
        val dialogBinding = LayoutAddPathCourseTextBinding.bind(view)

        val ad = createAlertDialog(view)
        dialogBinding.button.setOnClickListener {
            ad.dismiss()
        }
    }
}

