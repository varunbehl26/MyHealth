//package com.lifeapps.myhealth.utils
//
//import android.os.AsyncTask
//import android.util.Log
//import com.google.firebase.crash.FirebaseCrash
//import com.lifeapps.myhealth.activity.LoginActivity
//import com.lifeapps.myhealth.R
//import com.lifeapps.myhealth.model.User
//import com.lifeapps.myhealth.network.RetrofitManager
//import kotlinx.android.synthetic.main.activity_login.*
//import rx.Subscriber
//import rx.android.schedulers.AndroidSchedulers
//import rx.schedulers.Schedulers
//
///**
// * Represents an asynchronous login/registration task used to authenticate
// * the user.
// */
//class UserLoginTask internal constructor(private val mEmail: String, private val mPassword: String, private val loginActivity: LoginActivity) : AsyncTask<Void, Void, Boolean>() {
//
//    override fun doInBackground(vararg params: Void): Boolean? {
//        // TODO: attempt authentication against a network service.
//
//        val retrofitManager = RetrofitManager.getInstance()
//        val userInfoObservable = retrofitManager.userInfo
//
//        userInfoObservable
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(object : Subscriber<List<User>>() {
//                    override fun onCompleted() {
//                        Log.v("t", loginActivity.userList.toString())
//                    }
//
//                    override fun onError(e: Throwable) {
//                        e.printStackTrace()
//                        FirebaseCrash.report(e)
//                    }
//
//                    override fun onNext(einfo: List<User>) {
//                        loginActivity.userList = einfo
//                    }
//                })
//
//
//
//        return true
//
//    }
//
//
//    override fun onPostExecute(success: Boolean?) {
//        loginActivity.mAuthTask = null
//        loginActivity.showProgress(false)
//
//        if (success!!) {
//            loginActivity.finish()
//        } else {
//            loginActivity.password.error = loginActivity.getString(R.string.error_incorrect_password)
//            loginActivity.password.requestFocus()
//        }
//    }
//
//    override fun onCancelled() {
//        loginActivity.mAuthTask = null
//        loginActivity.showProgress(false)
//    }
//}