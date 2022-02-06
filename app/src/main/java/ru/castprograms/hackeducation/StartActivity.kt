package ru.castprograms.hackeducation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.castprograms.hackeducation.databinding.ActivityStartBinding

class StartActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
    }

    fun goToMainActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}