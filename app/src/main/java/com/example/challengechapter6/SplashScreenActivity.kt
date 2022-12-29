package com.example.challengechapter6

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.challengechapter6.databinding.ActivitySplashScreenBinding

@Suppress("DEPRECATION")
@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private val SPLASH_SCREEN = 1500
    private lateinit var onBoardingScreen: SharedPreferences
    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide()

        //splash
        Handler().postDelayed({
            onBoardingScreen = getSharedPreferences("onBoardingScreen", MODE_PRIVATE)
            val isfirsttime = onBoardingScreen.getBoolean("firsttime", true)

            if (isfirsttime) {
                val editr = onBoardingScreen.edit()
                editr.putBoolean("firsttime", false)
                editr.commit()
                val intent = Intent(this@SplashScreenActivity, LandingPageActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, SPLASH_SCREEN.toLong())
    }
}