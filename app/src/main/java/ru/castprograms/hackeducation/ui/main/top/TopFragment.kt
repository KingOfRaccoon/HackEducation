package ru.castprograms.hackeducation.ui.main.top

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.castprograms.hackeducation.R
import ru.castprograms.hackeducation.databinding.FragmentTopBinding
import ru.castprograms.hackeducation.tools.Teacher

class TopFragment: Fragment(R.layout.fragment_top) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentTopBinding.bind(view)

        val t_list : List<Teacher> = listOf(
            Teacher("https://www.big-ben.ru/sites/default/files/reviews/flat-faces-icons-circle-woman-7_17.png", "Elena", "Sergeevna"),
            Teacher("https://banner2.cleanpng.com/20180430/jge/kisspng-computer-icons-font-awesome-hamburger-button-5ae723a4ebfc72.3953800615250973809666.jpg", "ALena", "Vasilevna"),
            Teacher("https://yt3.ggpht.com/ytc/AAUvwngn8SLWqd9pJjSKeeKWjpKzYA0imRDlQbi6yWf8=s900-c-k-c0x00ffffff-no-rj", "Sergey", "Petrovich"),
        )

        binding.recyclerViewTop.adapter = RecyclerViewTop(t_list)
        binding.recyclerViewTop.layoutManager = LinearLayoutManager(requireContext())
    }
}