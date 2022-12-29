package com.example.challengechapter6.helper

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.example.challengechapter6.LoginActivity
import com.example.challengechapter6.MainActivity

class SessionManager(context: Context) {

    val PREF_NAME = "LOGIN_PLAYER"
    val LOGIN = "IS_LOGIN"
    val ID = "ID"
    val EMAIL = "EMAIL"
    val NAME = "NAME"
    var context: Context? = context
    val PRIVATE_MODE: Int = 0

    private var sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    private var editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun createSession(id: String?, email: String?, name: String?) {
        editor.putBoolean(LOGIN, true)
        editor.putString(ID, id)
        editor.putString(EMAIL, email)
        editor.putString(NAME, name)
        editor.apply()
    }

    private fun isLogin(): Boolean {
        return sharedPreferences.getBoolean( LOGIN, false )
    }

    fun checkLogin() {
        if (!isLogin()) {
            val intent = Intent(context, LoginActivity::class.java)
            context?.startActivity(intent)
            (context as MainActivity).finish()
        }
    }

    fun getPlayerDetail(): HashMap<String, String?> {
        val player = HashMap<String, String?>()
        player[ID] = sharedPreferences.getString(ID, null)
        player[EMAIL] = sharedPreferences.getString(EMAIL, null)
        player[NAME] = sharedPreferences.getString(NAME, null)
        return player
    }

    fun logout() {
        editor.clear()
        editor.commit()
        val intent = Intent(context, LoginActivity::class.java)
        context?.startActivity(intent)
        (context as MainActivity).finish()
    }

    fun clear() {
        editor.clear()
        editor.commit()
    }
}