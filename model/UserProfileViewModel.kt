package com.lifeapps.myhealth.model

import android.arch.lifecycle.ViewModel

/**
 * Created by varunbehl on 25/01/18.
 */
class UserProfileViewModel : ViewModel() {
    private var userId: String? = null
    val user: User? = null

    fun init(userId: String) {
        this.userId = userId
    }
}