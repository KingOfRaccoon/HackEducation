package ru.castprograms.hackeducation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import ru.castprograms.hackeducation.databinding.ActivityStartBinding

class StartActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        supportActionBar?.hide()
    }

    fun goToMainActivity(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}