package com.example.challengechapter6

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.challengechapter6.databinding.ActivityLoginBinding
import com.example.challengechapter6.helper.SessionManager
import com.example.challengechapter6.model.AppDatabase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sessionManager: SessionManager
    var mDb: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide()
        mDb = AppDatabase.getInstance(this@LoginActivity)
        sessionManager = SessionManager(this@LoginActivity)

        binding.btnRegister?.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        })

        binding.btnLogin?.setOnClickListener(View.OnClickListener {
            checkInputToAdd()
        })
    }

    fun checkInputToAdd(){
        if (TextUtils.isEmpty(binding.etEmail?.text.toString()) ||
            TextUtils.isEmpty(binding.etPassword?.text.toString())){
            Toast.makeText(this@LoginActivity, "Terdapat Kolom yang belum diisi", Toast.LENGTH_LONG).show()
        }else{
            getData()
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun getData(){

        GlobalScope.async {
            val result = mDb?.playerDao()?.findPlayer(binding.etEmail?.text.toString(), binding.etPassword?.text.toString())
            runOnUiThread{
                if (result != null){
                    Toast.makeText(this@LoginActivity, "Selamat Datang " + result[0].name, Toast.LENGTH_LONG).show()
                    sessionManager.createSession(result[0].id.toString(), result[0].email, result[0].name)
                    startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    finish()
                }else{
                    Toast.makeText(this@LoginActivity, "Email dan Password Salah", Toast.LENGTH_LONG).show()
                }
                finish()
            }
        }
    }

}