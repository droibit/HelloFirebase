package com.droibit.hello.firebase.signin

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem

import com.droibit.hello.firebase.R
import com.droibit.hello.firebase.databinding.ActivityEmailSignInBinding

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

    val binding: ActivityEmailSignInBinding by lazy {
        DataBindingUtil.setContentView<ActivityEmailSignInBinding>(this, R.layout.activity_email_sign_in)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_email_sign_in)

        supportActionBar?.let {
            it.title = when (intent.extras[KEY_REQUEST]) {
                EXTRA_REQUEST_SIGN_UP -> getString(R.string.sign_in_email_sign_up)
                EXTRA_REQUEST_SIGN_IN -> getString(R.string.sign_in_email_sign_in)
                else -> error("unknown request")
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> { finish(); true }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
