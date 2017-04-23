package com.droibit.hello.firebase.view.post

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem

import com.droibit.hello.firebase.R
import com.droibit.hello.firebase.databinding.ActivityNewPostBinding

class NewPostActivity : AppCompatActivity() {

    companion object {

        @JvmStatic
        fun createIntent(context: Context) = Intent(context, NewPostActivity::class.java)
    }

    private lateinit var binding: ActivityNewPostBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView<ActivityNewPostBinding>(this, R.layout.activity_new_post)
                .apply {
                    activity = this@NewPostActivity
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

    // public

    fun onDoneClick() {

        finish()
    }
}
