package com.droibit.hello.firebase.signin

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View

import com.droibit.hello.firebase.R
import com.droibit.hello.firebase.databinding.ActivityEmailSignInBinding
import com.google.firebase.auth.FirebaseAuth

class EmailSignInActivity : AppCompatActivity() {

    enum class Request {
        SIGN_UP, SIGN_IN
    }

    companion object {

        private val KEY_REQUEST = "request"

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
            android.R.id.home -> { finish(); true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun onSignInOrSignUpClick(v: View) {
        if (!validForm()) {
            return
        }

        when (requestType) {
            Request.SIGN_UP -> doFirebaseSignUp("${binding.email}", "${binding.password}")
            Request.SIGN_IN -> doFirebaseSignIn("${binding.email}", "${binding.password}")
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

    }

    private fun doFirebaseSignIn(email: String, password: String) {

    }
}
