package ru.castprograms.hackeducation.ui.start.instruction

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.castprograms.hackeducation.ui.start.authorization.AuthorizationFragment

class ViewPager2FragmentAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> InstructionFragment1()
            1 -> InstructionFragment2()
            2 -> AuthorizationFragment()
            else -> InstructionFragment1()
        }
    }
    override fun getItemCount(): Int = 3
}