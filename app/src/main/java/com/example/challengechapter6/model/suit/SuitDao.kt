package com.example.challengechapter6.model.suit

import androidx.room.*
import com.example.challengechapter6.model.player.PlayerEntity

@Dao
interface SuitDao {
    @Query("SELECT * FROM SuitEntity")
    fun getAllSuit(): List<SuitEntity>

    @Query("SELECT * FROM SuitEntity, PlayerEntity WHERE " +
            "SuitEntity.player_id = PlayerEntity.id AND " +
            "PlayerEntity.id = :player_id")
    fun findSuitbyPlayer(player_id: Int): List<SuitEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSuit(suitEntity: SuitEntity):Long

    @Update
    fun updateSuit(suitEntity: SuitEntity):Int

    @Delete
    fun deleteSuit(suitEntity: SuitEntity):Int
}