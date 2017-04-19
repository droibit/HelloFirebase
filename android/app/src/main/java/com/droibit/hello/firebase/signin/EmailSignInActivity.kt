package com.droibit.hello.firebase.signin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.droibit.hello.firebase.R
import com.droibit.hello.firebase.databinding.ActivityEmailSignInBinding
import com.google.firebase.auth.FirebaseAuth

class EmailSignInActivity : AppCompatActivity(),
        HasProgressDialog by HasProgressDialogImpl() {

    enum class Request {
        SIGN_UP, SIGN_IN
    }

    companion object {

        private val KEY_REQUEST = "request"

        private val TAG = EmailSignInActivity::class.java.simpleName

        @JvmStatic
        fun createSignUpIntent(context: Context): Intent {
            return Intent(context, EmailSignInActivity::class.java)
                    .putExtra(KEY_REQUEST, Request.SIGN_UP)
        }

        @JvmStatic
        fun createSignInIntent(context: Context): Intent {
            return Intent(context, EmailSignInActivity::class.java)
                    .putExtra(KEY_REQUEST, Request.SIGN_IN)
        }
    }

    private lateinit var binding: ActivityEmailSignInBinding

    private val requestType: Request by lazy { intent.getSerializableExtra(KEY_REQUEST) as Request }

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_email_sign_in)


        supportActionBar?.let {
            it.title = when (requestType) {
                Request.SIGN_UP -> getString(R.string.sign_in_email_sign_up)
                Request.SIGN_IN -> getString(R.string.sign_in_email_sign_in)
            }
            binding.signInOrUp.text = it.title
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish(); true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun onSignInOrSignUpClick(v: View) {
        if (!validForm()) {
            return
        }

        when (requestType) {
            Request.SIGN_UP -> doFirebaseSignUp("${binding.email.text}", "${binding.password.text}")
            Request.SIGN_IN -> doFirebaseSignIn("${binding.email.text}", "${binding.password.text}")
        }
    }

    private fun validForm(): Boolean {
        val email = "${binding.email.text}"
        val password = "${binding.password.text}"

        binding.inputEmail.error = if (email.isNotEmpty()) null else "Required."
        binding.inputPassword.error = if (password.isNotEmpty()) null else "Required."

        return binding.inputEmail != null && binding.password != null
    }

    private fun doFirebaseSignUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    Log.d(TAG, "createUserWithEmailAndPassword:onComplete:${task.isSuccessful}")
                    if (!task.isSuccessful) {
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                        return@addOnCompleteListener
                    }
                    Log.d(TAG, "Sign up(Email&Password) with ${task.result.user.email}")

                    hideProgress()
                    Toast.makeText(this, "Authentication successful.", Toast.LENGTH_SHORT).show()

                    backSignInActivity()
                }
    }

    private fun doFirebaseSignIn(email: String, password: String) {

    }

    private fun backSignInActivity() {
        setResult(Activity.RESULT_OK)
        finish()
    }
}
