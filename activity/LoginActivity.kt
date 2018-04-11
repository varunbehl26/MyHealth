package com.lifeapps.myhealth.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import com.facebook.stetho.Stetho
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.credentials.Credential
import com.google.android.gms.auth.api.credentials.CredentialPickerConfig
import com.google.android.gms.auth.api.credentials.HintRequest
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.lifeapps.myhealth.R
import com.lifeapps.myhealth.model.User
import com.lifeapps.myhealth.utils.LoginUtil
import kotlinx.android.synthetic.main.activity_login.*

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : BaseActivity(), GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    lateinit var userList: List<User>
    private lateinit var userInfo: Any
    //    var mAuthTask: LoginUtil.UserLoginTask? = null
    private var mAuth: FirebaseAuth? = null
    private var mGoogleApiClient: GoogleApiClient? = null
    private val TAG = "MainActivity"
    private val RC_SIGN_IN = 9001
    private val RC_HINT: Int = 101;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Stetho.initializeWithDefaults(this)
        mAuth = FirebaseAuth.getInstance()
        google_sign_in_button.setSize(SignInButton.SIZE_STANDARD)

        password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })

        setOnClickListners()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(Auth.CREDENTIALS_API)
                .build()

        showNumber()
    }

    companion object {
        fun loginTaskCompleted(userList: List<User>) {
            Log.v("t", userList.toString())

        }

        fun loginTaskFailure(userList: List<User>, loginActivity: LoginActivity) {
            loginActivity.loginTaskFailed(userList)
        }
    }

    fun loginTaskFailed(userList: List<User>) {
        password.error = getString(R.string.error_incorrect_password)
        password.requestFocus()

    }

    private fun setOnClickListners() {
        google_sign_in_button.setOnClickListener({
            val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
            startActivityForResult(signInIntent, RC_SIGN_IN)
        })
        register_button.setOnClickListener({
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        })
        sign_in_button.setOnClickListener { attemptLogin() }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth!!.currentUser
//        updateUI(currentUser)
    }


    private fun showNumber() {
        val hintRequest = HintRequest.Builder()
                .setHintPickerConfig(CredentialPickerConfig.Builder()
                        .setShowCancelButton(true)
                        .build())
                .setPhoneNumberIdentifierSupported(true)
                .build()

        val intent = Auth.CredentialsApi.getHintPickerIntent(mGoogleApiClient, hintRequest)
        startIntentSenderForResult(intent.getIntentSender(), RC_HINT, null, 0, 0, 0);
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private fun attemptLogin() {
//        if (mAuthTask != null) {
//            return
//        }

        // Reset errors.
        email.error = null
        password.error = null

        // Store values at the time of the login attempt.
        val emailStr = email.text.toString()
        val passwordStr = password.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(passwordStr) && !LoginUtil.isPasswordValid(passwordStr)) {
            password.error = getString(R.string.error_invalid_password)
            focusView = password
            cancel = true
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(emailStr)) {
            email.error = getString(R.string.error_field_required)
            focusView = email
            cancel = true
        } else if (!LoginUtil.isEmailValid(emailStr)) {
            email.error = getString(R.string.error_invalid_email)
            focusView = email
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
//            LoginUtil.showProgress(true, this)
//            mAuthTask = UserLoginTask(emailStr, passwordStr)
//            mAuthTask!!.execute(null as Void?)

            signIn()
        }
    }


    fun signIn() {
        if (mAuth != null) {
            mAuth?.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                    ?.addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
                            val user = mAuth?.getCurrentUser()
                            updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(this@LoginActivity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
                            updateUI(null)
                        }
                    }
        }
    }


    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_HINT) {
            if (resultCode == Activity.RESULT_OK) {
                val cred = data.getParcelableExtra<Credential>(Credential.EXTRA_KEY)
                email.setText(cred.id.substring(3, 13))
            } /*else {
                ui.focusPhoneNumber();
            }*/
        }

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if (result.isSuccess) {
                // Google Sign In was successful, authenticate with Firebase
                val account = result.signInAccount
                firebaseAuthWithGoogle(account)
            } else {
                // Google Sign In failed, update UI appropriately
                // [START_EXCLUDE]
//                updateUI(null)
                // [END_EXCLUDE]
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount?) {

        if (acct != null) {
            val personName = acct.getDisplayName()
            val personGivenName = acct.getGivenName()
            val personFamilyName = acct.getFamilyName()
            val personEmail = acct.getEmail()
            val personId = acct.getId()
            val personPhoto = acct.getPhotoUrl()
        }

        Log.d(TAG, "firebaseAuthWithGoogle:" + acct!!.id!!)
        // [START_EXCLUDE silent]
        // [END_EXCLUDE]

        try {
            val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
            mAuth!!.signInWithCredential(credential)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success")
                            val user = mAuth!!.currentUser
                            updateUI(user)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.exception)
                            Toast.makeText(this@LoginActivity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
//                            updateUI(null)
                        }

                        // [START_EXCLUDE]
                        // [END_EXCLUDE]
                    }
        } catch (e: Exception) {
            e.printStackTrace();
        }
    }

    private fun updateUI(firebaseUser: FirebaseUser?) {
        try {
            if (firebaseUser != null) {
                Log.v("firebaseUser", firebaseUser.displayName)
                //        Log.v("firebaseUser", firebaseUser.phoneNumber)
                Log.v("firebaseUser", firebaseUser.email)
                Log.v("firebaseUser", firebaseUser.uid)
                Log.v("firebaseUser", firebaseUser.photoUrl.toString())
                Log.v("firebaseUser", firebaseUser.isEmailVerified.toString())

                val user = User()
                user.userName = firebaseUser.displayName
                user.userEmail = firebaseUser.email
                user.userImage = firebaseUser.photoUrl.toString()

//                val userObserver = RetrofitManager.getInstance().createUser(user)
//                userObserver.execute()

                val intent = Intent(this, MainActivity::class.java)

                startActivity(intent)
                finish()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult)
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show()
    }


    override fun onConnected(p0: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
