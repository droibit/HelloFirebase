package com.droibit.hello.firebase.view.detail

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast

import com.droibit.hello.firebase.R
import com.droibit.hello.firebase.databinding.ActivityPostDetailBinding
import com.droibit.hello.firebase.model.Comment
import com.droibit.hello.firebase.model.Post
import com.droibit.hello.firebase.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class PostDetailActivity : AppCompatActivity(), ValueEventListener {

    companion object {

        private val EXTRA_POST_ID = "EXTRA_POST_ID"

        private val TAG = PostDetailActivity::class.java.simpleName

        @JvmStatic
        fun createIntent(context: Context, postId: String): Intent {
            return Intent(context, PostDetailActivity::class.java)
                    .putExtra(EXTRA_POST_ID, postId)
        }
    }

    private val postId: String by lazy { intent.getStringExtra(EXTRA_POST_ID) }

    private val database = FirebaseDatabase.getInstance()

    private val postDatabaseRef: DatabaseReference by lazy {
        database.reference.child("posts").child(postId)
    }

    private val commentsDatabaseRef: DatabaseReference by lazy {
        database.reference.child("post-comments").child(postId)
    }

    private val currentUser: FirebaseUser by lazy {
        FirebaseAuth.getInstance().currentUser ?: error("error.")
    }

    private lateinit var binding: ActivityPostDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityPostDetailBinding>(this, R.layout.activity_post_detail)
                .apply {
                    activity = this@PostDetailActivity
                }
    }

    override fun onStart() {
        super.onStart()

        postDatabaseRef.addValueEventListener(this)
        binding.commentList.adapter = CommentRecyclerAdapter(this, commentsDatabaseRef)
    }

    override fun onStop() {
        postDatabaseRef.removeEventListener(this)
        val adapter = binding.commentList.adapter as CommentRecyclerAdapter
        adapter.cleanup()
        super.onStop()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> { finish(); true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // ValueEventListener

    override fun onDataChange(dataSnapshot: DataSnapshot) {
        binding.post = dataSnapshot.getValue(Post::class.java)
    }

    override fun onCancelled(databaseError: DatabaseError) {
        Log.w(TAG, "loadPost:onCancelled", databaseError.toException())

        Toast.makeText(this, "Failed to load post.", Toast.LENGTH_SHORT).show()
    }

    // public

    fun onSendClick() {
        if (binding.inputComment.text?.isNullOrEmpty() == true) {
            return
        }

        database.reference.child("users").child(currentUser.uid)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(databaseError: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val user = dataSnapshot.getValue(User::class.java)
                        val text = "${binding.inputComment.text}"
                        val comment = Comment(currentUser.uid, user.userName, text)

                        commentsDatabaseRef.push().setValue(comment)

                        binding.inputComment.text = null
                    }
                })
    }
}
