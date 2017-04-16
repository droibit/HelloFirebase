package com.droibit.hello.firebase.signin

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.droibit.hello.firebase.R

class SignInActivity : AppCompatActivity() {

    companion object {

        private val REQUEST_GOOGLE_SIGN_UP = 0
        private val REQUEST_EMAIL_SIGN_UP = 1
        private val REQUEST_EMAIL_SIGN_IN = 2

        @JvmStatic
        fun createIntent(context: Context) = Intent(context, SignInActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
    }

    // SignInHandler

    fun onClickGoogleSignUp(v: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun onClickEmailSignUp(v: View) {
        val intent = EmailSignInActivity.createSignUpIntent(this)
        startActivityForResult(intent, REQUEST_EMAIL_SIGN_UP)
    }

    fun onClickEmailSignIn(v: View) {
        val intent = EmailSignInActivity.createSignInIntent(this)
        startActivityForResult(intent, REQUEST_EMAIL_SIGN_UP)

    }
}
