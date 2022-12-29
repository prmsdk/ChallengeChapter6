package com.example.challengechapter6

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.challengechapter6.databinding.ActivityProfileBinding
import com.example.challengechapter6.helper.SessionManager
import com.example.challengechapter6.model.AppDatabase
import com.example.challengechapter6.model.player.PlayerEntity
import com.example.challengechapter6.player.ui.ShowPlayerActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class ProfileActivity : AppCompatActivity() {

    lateinit var binding: ActivityProfileBinding
    private lateinit var sessionManager: SessionManager
    var mDb: AppDatabase? = null
    lateinit var email: String
    private lateinit var objectPlayer: PlayerEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide()
        mDb = AppDatabase.getInstance(this@ProfileActivity)
        sessionManager = SessionManager(this@ProfileActivity)

        objectPlayer = PlayerEntity(
            null, "", "", ""
        )
        email = sessionManager.getPlayerDetail()[sessionManager.EMAIL].toString()

        getData()

        binding.btnShowAll.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, ShowPlayerActivity::class.java))
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun getData(){
        GlobalScope.async {
            val result = mDb?.playerDao()?.findPlayerbyEmail(email)
            runOnUiThread{
                if (result != null){
                    Log.d("NAME", result[0].name.toString())
                    objectPlayer = PlayerEntity(
                        id = result[0].id,
                        name = result[0].name.toString(),
                        email = result[0].email.toString(),
                        password = result[0].password.toString()
                    )

                    binding.tvName.setText(objectPlayer.name)
                    binding.tvEmail.setText(objectPlayer.email)
                }else{
                    Toast.makeText(this@ProfileActivity, "Pengguna tidak ditemukan", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}