package com.example.challengechapter6.player.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.challengechapter6.LoginActivity
import com.example.challengechapter6.R
import com.example.challengechapter6.databinding.ActivityAddPlayerBinding
import com.example.challengechapter6.model.AppDatabase
import com.example.challengechapter6.model.player.PlayerEntity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class AddPlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPlayerBinding
    var mDb: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPlayerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide()
        mDb = AppDatabase.getInstance(this@AddPlayerActivity)

        binding.btnBack?.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })

        binding.btnAdd.setOnClickListener(View.OnClickListener {
            checkInputToAdd()
        })
    }

    fun checkInputToAdd(){
        if (TextUtils.isEmpty(binding.etName.text.toString()) ||
            TextUtils.isEmpty(binding.etEmail.text.toString()) ||
            TextUtils.isEmpty(binding.etPassword.text.toString())){
            Toast.makeText(this@AddPlayerActivity, "Terdapat Kolom yang belum diisi", Toast.LENGTH_LONG).show()
        }else{
            addData()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun addData(){

        val objectPlayer = PlayerEntity(
            null,
            binding.etName.text.toString(),
            binding.etEmail.text.toString(),
            binding.etPassword.text.toString(),
        )

        GlobalScope.async {
            val result = mDb?.playerDao()?.insertPlayer(objectPlayer)
            runOnUiThread{
                if (result != 0.toLong()){
                    Toast.makeText(this@AddPlayerActivity, "Sukses Menambahkan Player", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@AddPlayerActivity, ShowPlayerActivity::class.java))
                    finish()
                }else{
                    Toast.makeText(this@AddPlayerActivity, "Gagal Menambahkan Player", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}