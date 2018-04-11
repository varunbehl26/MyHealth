package com.lifeapps.myhealth.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.lifeapps.myhealth.model.User


@Database(entities = [(User::class)], version = 1)
abstract class UserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

}
