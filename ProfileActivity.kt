package com.lifeapps.myhealth

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.lifeapps.myhealth.model.User
import com.lifeapps.myhealth.network.RetrofitManager
import kotlinx.android.synthetic.main.activity_profile.*
import java.math.BigInteger


class ProfileActivity : BaseActivity() {

    private var mAuth: FirebaseAuth? = null
    private val TAG = "ProfileActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        mAuth = FirebaseAuth.getInstance()


        btn_submit.setOnClickListener {
//            createAccount(email_et.text.toString(), password_et.text.toString())

            val newUser = User()
            newUser.userName = name_et.text.toString()
            newUser.userEmail = email_et.text.toString()
            newUser.userPassword = password_et.text.toString()
            newUser.userMobile = BigInteger(mobile_et.text.toString())

            DataCall(1,newUser).start()


        }

    }

    fun createAccount(email: String, password: String) {

        if (mAuth != null) {
            mAuth!!.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            val user = mAuth?.getCurrentUser()
                            updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(this@ProfileActivity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
                            updateUI(null)
                        }
                    }
        }
    }


    fun getCurrentUser() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            // Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user.uid
        }
    }

    fun updateUI(user: FirebaseUser?) {

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth?.getCurrentUser()
        updateUI(currentUser)
    }

    private inner class DataCall internal constructor(internal val requestType: Int,val newUser:User) : Thread() {


        override fun run() {
            super.run()
            var threadAlreadyRunning=false

            try {
                if (threadAlreadyRunning) {
                } else {
                    threadAlreadyRunning = true
                    val userObserver = RetrofitManager.getInstance().createUser(newUser)
                    userObserver.execute()

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }
}




