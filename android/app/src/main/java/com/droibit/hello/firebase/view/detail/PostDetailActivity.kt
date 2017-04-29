package com.droibit.hello.firebase.view.detail

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

import com.droibit.hello.firebase.R
import com.droibit.hello.firebase.databinding.ActivityPostDetailBinding

class PostDetailActivity : AppCompatActivity() {

    companion object {

        private val EXTRA_POST_ID = "EXTRA_POST_ID"

        @JvmStatic
        fun createIntent(context: Context, postId: String): Intent {
            return Intent(context, PostDetailActivity::class.java)
                    .putExtra(EXTRA_POST_ID, postId)
        }
    }

    private val postId: String by lazy { intent.getStringExtra(EXTRA_POST_ID) }

    private lateinit var binding: ActivityPostDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_post_detail)
    }

    fun onSendClick() {

    }
}
