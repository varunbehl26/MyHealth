package com.lifeapps.myhealth.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.lifeapps.myhealth.model.User


/**
 * Created by varunbehl on 15/10/17.
 */
@Dao
interface UserDao {
    @get:Query("SELECT * FROM User")
    val all: LiveData<List<User>>

    @Query("SELECT * FROM User WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>


    @Insert
    fun insertAll(users: List<User>): List<Long>

    @Delete
    fun delete(user: User)
}