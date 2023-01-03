package com.example.challengechapter6

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challengechapter6.dao.AppDatabase
import com.example.challengechapter6.databinding.ActivityHistoryBinding
import com.example.challengechapter6.helper.ShowHistoryAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private var mDb : AppDatabase? = null
    var valID: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide()
        valID = intent.getStringExtra("valID").toString()
        Log.d("VAL_ID", valID)

        mDb = AppDatabase.getInstance(this@HistoryActivity)
        binding.rvShowPlayer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        fetchData()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, ProfileActivity::class.java))
        finish()
    }

    fun fetchData(){
        GlobalScope.launch {
            val listSuit = mDb?.suitDao()?.findSuitbyPlayer(valID.toInt())

            runOnUiThread{
                listSuit?.let {
                    val adapter = ShowHistoryAdapter(it)
                    binding.rvShowPlayer.adapter = adapter
                }
            }
        }
    }
}