package ru.castprograms.hackeducation.ui.main.createcourse

import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import ru.castprograms.hackeducation.tools.ui.ItemTouchHelperAdapter

class ItemMoveGroupAdapter: GroupAdapter<GroupieViewHolder>(), ItemTouchHelperAdapter {
    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        println(this.itemCount)
        println(fromPosition)
        println(toPosition)
        val items = List(itemCount){ getItem(it) }

        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                removeAll(items)
                addAll(replace((items as List<Item>).toMutableList(), i, i+1))
            }
        }
        else{
            for (i in toPosition downTo fromPosition step 1){
                removeAll(items)
                addAll(replace((items as List<Item>).toMutableList(), i, i+1))
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    private fun replace(list: MutableList<Item>, fromPosition: Int, toPosition: Int): List<Item>{
        val firstPair = list[fromPosition]
        val secondPair = list[toPosition]

        list[fromPosition] = secondPair
        list[toPosition] = firstPair
        return list
    }
}