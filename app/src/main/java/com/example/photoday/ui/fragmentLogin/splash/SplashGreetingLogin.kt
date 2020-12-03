package com.example.photoday.ui.fragmentLogin.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.photoday.R
import com.example.photoday.constants.SPLASH_TIME_OUT
import com.example.photoday.ui.activity.MainActivity

class SplashGreetingLogin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_greeting_login)

        supportActionBar?.hide()

        //define o tempo que a activity estará ativa atá passar para a outra
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_TIME_OUT)
    }

    companion object {
        fun newIntent(context: Context) = Intent(context, SplashGreetingLogin::class.java)
    }
}