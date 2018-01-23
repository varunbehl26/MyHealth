package com.lifeapps.myhealth.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

/**
 * Created by varunbehl on 15/10/17.
 */
@Database(entities = arrayOf(UserDbModel::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}