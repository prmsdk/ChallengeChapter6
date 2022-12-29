package com.example.challengechapter6.player

import android.content.Context
import android.view.View

interface PlayerPresenter {

    fun addData(name:String, email:String, password:String, repassword:String)

    fun loadData()
}