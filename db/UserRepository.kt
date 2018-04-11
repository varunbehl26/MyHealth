package com.lifeapps.myhealth.db

import android.arch.lifecycle.LiveData
import com.lifeapps.myhealth.model.User
import com.lifeapps.myhealth.network.Resource
import com.lifeapps.myhealth.network.SourceService
import com.lifeapps.myhealth.utils.AppExecutors
import com.lifeapps.myhealth.utils.NetworkBoundResource

class UserRepository(
        private val userDao: UserDao,
        private val newsSourceService: SourceService,
        private val appExecutors: AppExecutors = AppExecutors()
) {
    fun getUsersLiveData(): LiveData<Resource<List<User>?>> {
        return object : NetworkBoundResource<List<User>, List<User>>(appExecutors) {
            override fun saveCallResult(item: List<User>) {
                userDao.insertAll(item)
            }

            override fun shouldFetch(data: List<User>?) = true

            override fun loadFromDb() = userDao.all

            override fun createCall() = newsSourceService.getUsers()
        }.asLiveData()
    }
}

