package com.lifeapps.myhealth.model

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.lifeapps.myhealth.db.UserRepository
import com.lifeapps.myhealth.network.Resource
import com.lifeapps.myhealth.network.SourceService
import com.lifeapps.myhealth.utils.DatabaseCreator

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var usersListLiveData: LiveData<Resource<List<User>?>>

    init {
        val userRepository = UserRepository(
                DatabaseCreator.database(application).userDao(),
                SourceService.getSourceService())
    }

    /**
     * Return news articles to observe on the UI.
     */
    fun getUsersLiveData() = usersListLiveData

}


