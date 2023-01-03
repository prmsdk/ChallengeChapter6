package com.example.challengechapter6.player.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.challengechapter6.databinding.ActivityEditPlayerBinding
import com.example.challengechapter6.helper.SessionManager
import com.example.challengechapter6.dao.AppDatabase
import com.example.challengechapter6.dao.player.PlayerEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class EditPlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditPlayerBinding
    private lateinit var sessionManager: SessionManager
    var mDb: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditPlayerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide()

        sessionManager = SessionManager(this@EditPlayerActivity)
        mDb = AppDatabase.getInstance(this@EditPlayerActivity)
        val objectPlayer = intent.getParcelableExtra<PlayerEntity>("player")

        binding.etName.setText(objectPlayer?.name.toString())
        binding.etEmail.setText(objectPlayer?.email.toString())

        binding.btnSave.setOnClickListener(View.OnClickListener {
            checkInputToUpdate(objectPlayer!!)
        })

        binding.btnBack.setOnClickListener(View.OnClickListener {
            onBackPressed()
        })
    }

    fun checkInputToUpdate(objectPlayerEntity: PlayerEntity){
        if (TextUtils.isEmpty(binding.etName.text.toString()) ||
            TextUtils.isEmpty(binding.etEmail.text.toString()) ){
            Toast.makeText(this@EditPlayerActivity, "Terdapat Kolom yang belum diisi", Toast.LENGTH_LONG).show()
        }else{
            updateData(objectPlayerEntity)
        }
    }

    fun updateData(objectPlayerEntity: PlayerEntity){

        objectPlayerEntity.name = binding.etName.text.toString()
        objectPlayerEntity.email = binding.etEmail.text.toString()

        GlobalScope.async {
            val result = mDb?.playerDao()?.updatePlayer(objectPlayerEntity)

            runOnUiThread{
                if (result != 0){
                    Toast.makeText(this@EditPlayerActivity, "Berhasil mengupdate data ${objectPlayerEntity.name}", Toast.LENGTH_LONG).show()
                    if(objectPlayerEntity.email == sessionManager.getPlayerDetail().get(sessionManager.EMAIL).toString()){
                        sessionManager.createSession(objectPlayerEntity.id.toString(), objectPlayerEntity.email, objectPlayerEntity.name)
                    }
                    onBackPressed()
                }else{
                    Toast.makeText(this@EditPlayerActivity, "Gagal mengupdate data ${objectPlayerEntity.name}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}