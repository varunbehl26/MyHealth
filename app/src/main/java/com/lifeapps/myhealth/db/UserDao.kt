package com.lifeapps.myhealth.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query


/**
 * Created by varunbehl on 15/10/17.
 */
@Dao
interface UserDao {
    @get:Query("SELECT * FROM UserDbModel")
    val all: List<UserDbModel>

    @Query("SELECT * FROM UserDbModel WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<UserDbModel>


    @Insert
    fun insertAll(vararg users: UserDbModel)

    @Delete
    fun delete(user: UserDbModel)
}