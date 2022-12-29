package com.example.challengechapter6.player

import android.content.Context

interface PlayerView {

    fun showMessage(message: String)

    fun showData(data: String)

    fun clearField()
}