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

    companion object {

        private val KEY_REQUEST = "request"

        @JvmStatic
        val EXTRA_REQUEST_SIGN_IN = "sign_in"

        @JvmStatic
        val EXTRA_REQUEST_SIGN_UP = "sign_up"

        @JvmStatic
        fun createSignUpIntent(context: Context): Intent {
            return Intent(context, EmailSignInActivity::class.java)
                    .putExtra(KEY_REQUEST, EXTRA_REQUEST_SIGN_UP)
        }

        @JvmStatic
        fun createSignInIntent(context: Context): Intent {
            return Intent(context, EmailSignInActivity::class.java)
                    .putExtra(KEY_REQUEST, EXTRA_REQUEST_SIGN_IN)
        }
    }

    private lateinit var binding: ActivityEmailSignInBinding

    private val requestType: String by lazy { intent.getStringExtra(KEY_REQUEST) }

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_email_sign_in)


        supportActionBar?.let {
            it.title = when (requestType) {
                EXTRA_REQUEST_SIGN_UP -> {
                    getString(R.string.sign_in_email_sign_up)
                }
                EXTRA_REQUEST_SIGN_IN -> getString(R.string.sign_in_email_sign_in)
                else -> error("unknown request")
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


    }

    private fun validForm(): Boolean {
        val email = "${binding.email.text}"
        val password = "${binding.password.text}"

        binding.inputEmail.error = if (email.isNotEmpty()) null else "Required."
        binding.inputPassword.error = if (password.isNotEmpty()) null else "Required."

        return binding.inputEmail != null && binding.password != null
    }
}
