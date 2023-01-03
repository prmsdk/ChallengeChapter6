package com.example.challengechapter6

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.challengechapter6.databinding.ActivityMainBinding
import com.example.challengechapter6.helper.SessionManager
import com.example.challengechapter6.player.PlayerPresenterImp
import com.google.android.material.snackbar.Snackbar
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var onBoardingScreen: SharedPreferences
    private lateinit var playerDetail: HashMap<String, String?>
    private lateinit var playerName: String
    private lateinit var playerID: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide()

        sessionManager = SessionManager(this@MainActivity)
        sessionManager.checkLogin()

        playerDetail = sessionManager.getPlayerDetail()
        playerName = playerDetail.get(sessionManager.NAME).toString()
        playerID = playerDetail.get(sessionManager.ID).toString()

        binding.tvVsPlayer.text = playerName + " VS Pemain"
        binding.tvVsCom.text = playerName + " VS COM"

        onBoardingScreen = getSharedPreferences("onBoardingScreen", MODE_PRIVATE)
        val isfirsttime = onBoardingScreen.getBoolean("firsttime", true)
        if (isfirsttime) {
            val snackbar = Snackbar.make(view, "Selamat Datang " + playerName, Snackbar.LENGTH_LONG)
            snackbar.setAction("Tutup") {
                snackbar.dismiss()
            }
            snackbar.show()
        }

        binding.btnProfile.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        })

        binding.btnLogout.setOnClickListener(View.OnClickListener {
            sessionManager.logout()
        })

        binding.btnExit.setOnClickListener(View.OnClickListener {
            finishAffinity()
            exitProcess(0)
        })

        binding.cvVsPlayer.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, VsPlayerActivity::class.java).putExtra("valName", playerName).putExtra("valID", playerID))
            finish()
        })

        binding.cvVsCom.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, VsComActivity::class.java).putExtra("valName", playerName).putExtra("valID", playerID))
            finish()
        })

    }

    override fun onResume() {
        super.onResume()
        playerDetail = sessionManager.getPlayerDetail()
        playerName = playerDetail.get(sessionManager.NAME).toString()
        binding.tvVsPlayer.text = playerName + " VS Pemain"
        binding.tvVsCom.text = playerName + " VS COM"
    }
}