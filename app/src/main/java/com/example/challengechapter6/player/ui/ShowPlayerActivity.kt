package com.example.challengechapter6.player.ui

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengechapter6.ProfileActivity
import com.example.challengechapter6.databinding.ActivityShowPlayerBinding
import com.example.challengechapter6.helper.ShowPlayerAdapter
import com.example.challengechapter6.model.AppDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ShowPlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowPlayerBinding
    private var mDb : AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShowPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        mDb = AppDatabase.getInstance(this@ShowPlayerActivity)
        binding.rvShowPlayer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        fetchData()

        binding.fab.setOnClickListener { view ->
            startActivity(Intent(this, AddPlayerActivity::class.java))
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, ProfileActivity::class.java))
        finish()
    }

    override fun onResume() {
        super.onResume()
        fetchData()
    }

    fun fetchData(){
        GlobalScope.launch {
            val listPlayer = mDb?.playerDao()?.getAllPlayer()

            runOnUiThread{
                listPlayer?.let {
                    val adapter = ShowPlayerAdapter(it)
                    binding.rvShowPlayer.adapter = adapter
                }
            }
        }
    }
}