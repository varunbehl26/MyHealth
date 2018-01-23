package com.lifeapps.myhealth.utils

import android.os.AsyncTask
import com.google.firebase.crash.FirebaseCrash
import com.lifeapps.myhealth.LoginActivity
import com.lifeapps.myhealth.model.User
import com.lifeapps.myhealth.network.RetrofitManager
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by varunbehl on 02/10/17.
 */
/**
 * Attempts to sign in or register the account specified by the login form.
 * If there are form errors (invalid email, missing fields, etc.), the
 * errors are presented and no actual login attempt is made.
 */

class LoginUtil {
    companion object {


        public fun isEmailValid(email: String): Boolean {
            //TODO: Replace this with your own logic
            return email.contains("@")
        }

        public fun isPasswordValid(password: String): Boolean {
            //TODO: Replace this with your own logic
            return password.length > 4
        }


//        /**
//         * Shows the progress UI and hides the login form.
//         */
//        @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
//        fun showProgress(show: Boolean, context: Context) =// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
//                // for very easy animations. If available, use these APIs to fade-in
//                // the progress spinner.
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//                    val shortAnimTime = context.resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
//                    login_progress.visibility = if (show) View.VISIBLE else View.GONE
//                    login_progress.animate()
//                            .setDuration(shortAnimTime)
//                            .alpha((if (show) 1 else 0).toFloat())
//                            .setListener(object : AnimatorListenerAdapter() {
//                                override fun onAnimationEnd(animation: Animator) {
//                                    login_progress.visibility = if (show) View.VISIBLE else View.GONE
//                                }
//                            })
//                } else {
//                    // The ViewPropertyAnimator APIs are not available, so simply show
//                    // and hide the relevant UI components.
//                    login_progress.visibility = if (show) View.VISIBLE else View.GONE
//                    //            login_form.visibility = if (show) View.GONE else View.VISIBLE
//                }
    }

    inner class UserLoginTask internal constructor(private val mEmail: String, private val mPassword: String) : AsyncTask<Void, Void, Boolean>() {

        private lateinit var userList: List<User>

        override fun doInBackground(vararg params: Void): Boolean? {
            // TODO: attempt authentication against a network service.


            val retrofitManager = RetrofitManager.getInstance()

            val userInfoObservable = retrofitManager.userInfo
            userInfoObservable
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(object : Subscriber<List<User>>() {
                        override fun onCompleted() {
                            LoginActivity.loginTaskCompleted(userList);

                        }

                        override fun onError(e: Throwable) {
                            e.printStackTrace()
                            FirebaseCrash.report(e)
                        }

                        override fun onNext(einfo: List<User>) {
                            userList = einfo
                        }
                    })



            return true

        }


        override fun onPostExecute(success: Boolean?) {
//            mAuthTask = null
//            showProgress(false)

            if (success!!) {

            } else {
//                LoginActivity.loginTaskFailure(userList)
            }
        }

        override fun onCancelled() {
//            showProgress(false)
        }
    }


}