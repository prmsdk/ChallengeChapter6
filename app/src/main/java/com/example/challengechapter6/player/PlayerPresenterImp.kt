package com.example.challengechapter6.player

import android.text.TextUtils

class PlayerPresenterImp(private val view: PlayerView): PlayerPresenter {

    override fun addData(name: String, email: String, password: String, repassword: String) {
        if (TextUtils.isEmpty(name) ||
                TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(password) ||
                TextUtils.isEmpty(repassword)){
            view.showMessage("Terdapat Kolom yang belum diisi")
        }else{
            if (!TextUtils.equals(password, repassword)){
                view.showMessage("Konfirmasi ulang penulisan password")
            }else{
                view.showMessage("Ingin menambahkan data")
            }
        }
    }

    override fun loadData() {
        TODO("Not yet implemented")
    }

}