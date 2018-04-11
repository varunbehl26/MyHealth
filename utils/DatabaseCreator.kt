package com.lifeapps.myhealth.utils

import android.arch.persistence.room.Room
import android.content.Context
import com.lifeapps.myhealth.db.UserDatabase

object DatabaseCreator {

    /**
     * Create database instance when the singleton instance is created for the
     * first time.
     */
    fun database(context: Context): UserDatabase {
        return Room.databaseBuilder(context, UserDatabase::class.java, "user-db").build()
    }

}