package ru.castprograms.hackeducation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.children
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import ru.castprograms.hackeducation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupActionBarWithNavController(
            binding.containerView.findNavController(),
            AppBarConfiguration(binding.bnv.menu)
        )
        binding.bnv.setupWithNavController(binding.containerView.findNavController())
    }

    override fun onSupportNavigateUp(): Boolean {
        return binding.containerView.findNavController().navigateUp() || super.onSupportNavigateUp()
    }
}