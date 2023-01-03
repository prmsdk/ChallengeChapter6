package com.example.challengechapter6

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.challengechapter6.databinding.ActivityRegisterBinding
import com.example.challengechapter6.dao.AppDatabase
import com.example.challengechapter6.dao.player.PlayerEntity
import com.example.challengechapter6.player.PlayerModel
import com.example.challengechapter6.player.PlayerPresenterImp
import com.example.challengechapter6.player.PlayerView
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class RegisterActivity : AppCompatActivity(), PlayerView {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var playerPresenter: PlayerPresenterImp
    var mDb: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide()
        mDb = AppDatabase.getInstance(this@RegisterActivity)
        playerPresenter = PlayerPresenterImp(this)

        binding.btnLogin?.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            finish()
        })

        binding.btnRegister.setOnClickListener(View.OnClickListener {
            checkInputToAdd()
        })

    }

    fun checkInputToAdd(){
        if (TextUtils.isEmpty(binding.etName.text.toString()) ||
            TextUtils.isEmpty(binding.etEmail.text.toString()) ||
            TextUtils.isEmpty(binding.etPassword.text.toString()) ||
            TextUtils.isEmpty(binding.etRepassword.text.toString())){
            Toast.makeText(this@RegisterActivity, "Terdapat Kolom yang belum diisi", Toast.LENGTH_LONG).show()
        }else{
            if (!TextUtils.equals(binding.etPassword.text.toString(), binding.etRepassword.text.toString())){
                Toast.makeText(this@RegisterActivity, "Konfirmasi ulang penulisan password", Toast.LENGTH_LONG).show()
            }else{
                addData()
            }
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
                    Toast.makeText(this@RegisterActivity, "Sukses Menambahkan Player", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    finish()
                }else{
                    Toast.makeText(this@RegisterActivity, "Gagal Menambahkan Player", Toast.LENGTH_LONG).show()
                }
                finish()
            }
        }
    }

    override fun showMessage(message: String) {
        Toast.makeText(this@RegisterActivity, message, Toast.LENGTH_LONG).show()
    }

    override fun showData(data: String) {
        TODO("Not yet implemented")
    }

    override fun clearField() {
        binding.etName.setText("")
        binding.etEmail.setText("")
        binding.etPassword.setText("")
        binding.etRepassword.setText("")
    }
}