package ru.castprograms.hackeducation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.castprograms.hackeducation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModel()
    private lateinit var navHostController: NavController
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        navHostController =
            supportFragmentManager.findFragmentById(R.id.container_view)?.findNavController()!!
        setupActionBarWithNavController(
            navHostController,
            AppBarConfiguration(binding.bnv.menu)
        )
        binding.bnv.setupWithNavController(navHostController)
        viewModel.getGoogleAccount(this)?.id?.let {
            viewModel.loadPerson(it)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navHostController.navigateUp() || super.onSupportNavigateUp()
    }
}