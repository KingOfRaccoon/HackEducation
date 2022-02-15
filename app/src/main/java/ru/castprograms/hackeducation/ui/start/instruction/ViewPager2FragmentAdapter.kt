import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import ru.castprograms.hackeducation.ui.start.authorization.AuthorizationFragment
import ru.castprograms.hackeducation.ui.start.instruction.InstructionAllFragment
import ru.castprograms.hackeducation.ui.start.instruction.InstructionFragment1
import ru.castprograms.hackeducation.ui.start.instruction.InstructionFragment2


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