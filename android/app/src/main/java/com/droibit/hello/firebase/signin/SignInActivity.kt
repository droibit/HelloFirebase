package com.droibit.hello.firebase.signin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.droibit.hello.firebase.MainActivity
import com.droibit.hello.firebase.ProgressDialogFragment
import com.droibit.hello.firebase.R
import com.droibit.hello.firebase.databinding.ActivitySignInBinding
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class SignInActivity : AppCompatActivity(),
        GoogleApiClient.OnConnectionFailedListener,
        HasProgressDialog by HasProgressDialogImpl() {

    companion object {

        private val REQUEST_GOOGLE_SIGN_UP = 0
        private val REQUEST_EMAIL_SIGN_UP = 1
        private val REQUEST_EMAIL_SIGN_IN = 2

        private val TAG = SignInActivity::class.java.simpleName

        @JvmStatic
        fun createIntent(context: Context) = Intent(context, SignInActivity::class.java)
    }

    private val googleApiClient: GoogleApiClient by lazy {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()
    }

    private val auth = FirebaseAuth.getInstance()

    private lateinit var binding: ActivitySignInBinding

    private var requestHideProgress: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<ActivitySignInBinding>(this, R.layout.activity_sign_in).apply {
            signUpGoogle.setOnClickListener { onClickGoogleSignUp(it) }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_GOOGLE_SIGN_UP -> {
                if (resultCode == Activity.RESULT_OK) {
                    val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
                    doFirebaseAuthWithGoogle(result.signInAccount!!)
                } else {
                    Toast.makeText(this, "Failed sign in with Google.", Toast.LENGTH_SHORT).show()
                    requestHideProgress = true
                }
            }
            REQUEST_EMAIL_SIGN_UP, REQUEST_EMAIL_SIGN_IN -> {
                if (resultCode == Activity.RESULT_OK) {
                    startMainActivity()
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()

        if (requestHideProgress) {
            hideProgress()
        }
        requestHideProgress = false
    }

    // SignInHandler

    fun onClickGoogleSignUp(v: View) {
        showProgress()

        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient)
        startActivityForResult(signInIntent, REQUEST_GOOGLE_SIGN_UP)
    }

    fun onClickEmailSignUp(v: View) {
        val intent = EmailSignInActivity.createSignUpIntent(this)
        startActivityForResult(intent, REQUEST_EMAIL_SIGN_UP)
    }

    fun onClickEmailSignIn(v: View) {
        val intent = EmailSignInActivity.createSignInIntent(this)
        startActivityForResult(intent, REQUEST_EMAIL_SIGN_IN)
    }

    // OnConnectionFailedListener

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    // Private

    private fun doFirebaseAuthWithGoogle(account: GoogleSignInAccount) {
        Log.d(TAG, "Sign up(Google) with : ${account.email}")

        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    Log.d(TAG, "signInWithCredential:onComplete:${task.isSuccessful}")
                    if (!task.isSuccessful) {
                        val msg = task.exception?.message ?: "Authentication failed."
                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                        return@addOnCompleteListener
                    }
                    Log.d(TAG, "Sign up(Firebase) with ${task.result.user.email}")

                    hideProgress()
                    Toast.makeText(this, "Authentication successful.", Toast.LENGTH_SHORT).show()

                    startMainActivity()
                }
    }

    private fun startMainActivity() {
        val intent = MainActivity.createIntent(this)
        startActivity(intent)
        finish()
    }
}
