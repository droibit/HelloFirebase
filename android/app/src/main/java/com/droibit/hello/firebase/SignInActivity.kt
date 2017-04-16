package com.droibit.hello.firebase

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class SignInActivity : AppCompatActivity() {

    companion object {

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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun onClickEmailSignIn(v: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
