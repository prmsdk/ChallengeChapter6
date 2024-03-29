package com.example.challengechapter6

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.challengechapter6.databinding.ActivityMainBinding
import com.example.challengechapter6.databinding.ActivityVsComBinding
import com.example.challengechapter6.databinding.CustomDialogBinding
import kotlin.system.exitProcess

class VsPlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVsComBinding
    var PILIHAN_PLAYER_1 = "PILIHAN_PLAYER_1"
    var PILIHAN_PLAYER_2 = "PILIHAN_PLAYER_2"
    var HASIL_GAME = "HASIL_GAME"
    var pilihanPlayer1: String = ""
    var pilihanPlayer2: String = ""
    var valName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVsComBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.hide()

        valName = intent.getStringExtra("valName").toString()

        binding.tvPlayer1.text = valName
        binding.tvPlayer2.text = "Player 2"

        binding.cardBatu2.isEnabled = false
        binding.cardGunting2.isEnabled = false
        binding.cardKertas2.isEnabled = false

        binding.cardBatu1.setOnClickListener(View.OnClickListener {
            player1Choosing("#eeeeee", "#ffffff", "#ffffff","batu")
        })

        binding.cardGunting1.setOnClickListener(View.OnClickListener {
            player1Choosing("#ffffff", "#eeeeee", "#ffffff", "gunting")
        })

        binding.cardKertas1.setOnClickListener(View.OnClickListener {
            player1Choosing("#ffffff", "#ffffff", "#eeeeee", "kertas")
        })

        binding.cardBatu2.setOnClickListener(View.OnClickListener {
            player2Choosing("#eeeeee", "#ffffff", "#ffffff", "batu")
        })

        binding.cardGunting2.setOnClickListener(View.OnClickListener {
            player2Choosing("#ffffff", "#eeeeee", "#ffffff", "gunting")
        })

        binding.cardKertas2.setOnClickListener(View.OnClickListener {
            player2Choosing("#ffffff", "#ffffff", "#eeeeee","kertas")
        })

        binding.ivRefresh.setOnClickListener(View.OnClickListener {
            buttonState(true, true, true)
        })
    }

    private fun player1Choosing(colorBatu: String, colorGunting: String, colorKertas: String,
                                pilihan: String){
        Log.d(PILIHAN_PLAYER_1, pilihan)
        binding.cardBatu1.setCardBackgroundColor(Color.parseColor(colorBatu))
        binding.cardGunting1.setCardBackgroundColor(Color.parseColor(colorGunting))
        binding.cardKertas1.setCardBackgroundColor(Color.parseColor(colorKertas))
        pilihanPlayer1 = pilihan

        buttonState(false, true, false)
    }

    private fun player2Choosing(colorBatu: String, colorGunting: String, colorKertas: String,
                                pilihan: String){
        Log.d(PILIHAN_PLAYER_2, pilihan)
        binding.cardBatu2.setCardBackgroundColor(Color.parseColor(colorBatu))
        binding.cardGunting2.setCardBackgroundColor(Color.parseColor(colorGunting))
        binding.cardKertas2.setCardBackgroundColor(Color.parseColor(colorKertas))
        pilihanPlayer2 = pilihan

        buttonState(false, false, false)
        playerVsPlayer(pilihanPlayer1, pilihanPlayer2)
    }

    private fun buttonState(enabledPilihanKiri: Boolean, enabledPilihanKanan: Boolean, refresh: Boolean){
        if (refresh) {
            binding.cardBatu1.setCardBackgroundColor(Color.parseColor("#ffffff"))
            binding.cardGunting1.setCardBackgroundColor(Color.parseColor("#ffffff"))
            binding.cardKertas1.setCardBackgroundColor(Color.parseColor("#ffffff"))
            binding.cardBatu2.setCardBackgroundColor(Color.parseColor("#ffffff"))
            binding.cardGunting2.setCardBackgroundColor(Color.parseColor("#ffffff"))
            binding.cardKertas2.setCardBackgroundColor(Color.parseColor("#ffffff"))
        }

        binding.cardBatu1.isEnabled = enabledPilihanKiri
        binding.cardGunting1.isEnabled = enabledPilihanKiri
        binding.cardKertas1.isEnabled = enabledPilihanKiri

        binding.cardBatu2.isEnabled = enabledPilihanKanan
        binding.cardGunting2.isEnabled = enabledPilihanKanan
        binding.cardKertas2.isEnabled = enabledPilihanKanan
    }

    private fun playerVsPlayer(pilihan1: String, pilihan2: String) {
        Log.d(PILIHAN_PLAYER_2, pilihan2)

        if (pilihan1 == pilihan2){
            resultGame("DRAW", "#1C8EDA", 36F, "SERI!" )
        } else if ((pilihan1 == "gunting" && pilihan2 == "kertas") ||
            (pilihan1 == "batu" && pilihan2 == "gunting") ||
            (pilihan1 == "kertas" && pilihan2 == "batu")) {
            resultGame("Player 1 Menang", "#4CAF50", 20F, "$valName\n Menang!")
        } else if ((pilihan1 == "batu" && pilihan2 == "kertas") ||
            (pilihan1 == "kertas" && pilihan2 == "gunting") ||
            (pilihan1 == "gunting" && pilihan2 == "batu")) {
            resultGame("Player 2 Menang", "#4CAF50", 20F, "Player 2\n Menang!")
        }
    }

    private fun resultGame(msg: String, color: String, fontSize: Float, dialogDesc: String ){
        Log.d(HASIL_GAME, msg)
        binding.textMiddle.text = msg
        binding.textMiddle.setTextColor(Color.parseColor(color))
        binding.textMiddle.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
        showDialog(dialogDesc, valName)
    }

    fun showDialog(deskripsi: String, valName: String){

        val dialog = AlertDialog.Builder(this).create()
        val bindingDialog: CustomDialogBinding = CustomDialogBinding.inflate(layoutInflater)
        dialog.setView(bindingDialog.root)

        bindingDialog.tvTitleDialog.setText("Hasil Permainan")
        bindingDialog.tvMessageDialog.setText(deskripsi)
        dialog.setIcon(R.drawable.splash_screen1)

        dialog.setCancelable(false)

        bindingDialog.btnDialogPositive.setOnClickListener(View.OnClickListener {
            buttonState(true, true, true)

            binding.textMiddle.text = "VS"
            binding.textMiddle.setTextColor(Color.parseColor("#E82E2E"))
            binding.textMiddle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 44F);
            dialog.dismiss()
        })

        bindingDialog.btnDialogNegative.setOnClickListener(View.OnClickListener {
            dialog.dismiss()
            startActivity(Intent(this, MainActivity::class.java).putExtra("valName", valName))
            finish()
        })

        bindingDialog.btnDialogNeutral.setOnClickListener(View.OnClickListener {
            finishAffinity()
            exitProcess(0)
        })

        dialog.show()
    }
}