package com.lifeapps.myhealth.db

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


/**
 * Created by varunbehl on 15/10/17.
 */
@Entity
class UserDbModel {
    @PrimaryKey(autoGenerate = true)
    var uid: Int = 0

    @ColumnInfo(name = "userName")
    var userName: String? = null

    @ColumnInfo(name = "userId")
    var userId: String? = null

    @ColumnInfo(name = "userPassword")
    var userPassword: String? = null

    @ColumnInfo(name = "userEmail")
    var userEmail: String? = null

    @ColumnInfo(name = "userMobile")
    var userMobile: String? = null


    @ColumnInfo(name = "userPhone")
    var userPhone: String? = null

    @ColumnInfo(name = "gymId")
    var gymId: String? = null

    @ColumnInfo(name = "dietId")
    var dietId: String? = null

    @ColumnInfo(name = "userLevel")
    var userLevel: Int? = null

    @ColumnInfo(name = "userImage")
    var userImage: String? = null

    @ColumnInfo(name = "userSex")
    var userSex: String? = null

    @ColumnInfo(name = "userDob")
    var userDob: String? = null

    @ColumnInfo(name = "gymSlot")
    var gymSlot: String? = null

    @ColumnInfo(name = "userWeight")
    var userWeight: String? = null

    @ColumnInfo(name = "userHeight")
    var userHeight: String? = null

    @ColumnInfo(name = "userBmi")
    var userBmi: String? = null

    @ColumnInfo(name = "userStatus")
    var userStatus: Boolean? = null

    @ColumnInfo(name = "userAttendence")
    var userAttendence: String? = null

    @ColumnInfo(name = "userType")
    var userType: String? = null

    @ColumnInfo(name = "userSubscription")
    var userSubscription: Boolean? = null
}
