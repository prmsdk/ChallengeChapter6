package com.example.challengechapter6.dao.player

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface PlayerDao {
    @Query("SELECT * FROM PlayerEntity")
    fun getAllPlayer(): List<PlayerEntity>

    @Query("SELECT * FROM PlayerEntity WHERE email = :emailEt AND password = :passwordEt")
    fun findPlayer(emailEt: String, passwordEt: String): List<PlayerEntity>

    @Query("SELECT * FROM PlayerEntity WHERE email = :emailEt")
    fun findPlayerbyEmail(emailEt: String): List<PlayerEntity>

    @Insert(onConflict = REPLACE)
    fun insertPlayer(playerEntity: PlayerEntity):Long

    @Update
    fun updatePlayer(playerEntity: PlayerEntity):Int

    @Delete
    fun deletePlayer(playerEntity: PlayerEntity):Int
}