package com.droibit.hello.firebase.view.post

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast

import com.droibit.hello.firebase.R
import com.droibit.hello.firebase.databinding.ActivityNewPostBinding
import com.droibit.hello.firebase.model.Post
import com.droibit.hello.firebase.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NewPostActivity : AppCompatActivity() {

    companion object {

        private val TAG = NewPostActivity::class.java.simpleName

        @JvmStatic
        fun createIntent(context: Context) = Intent(context, NewPostActivity::class.java)
    }

    private lateinit var binding: ActivityNewPostBinding

    private val database = FirebaseDatabase.getInstance()

    private val auth = FirebaseAuth.getInstance()

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
        if (!validForm()) {
            return
        }

        setEditingEnabled(false)
        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();

        val userId = auth.currentUser?.uid!!
        val databaseRef = database.reference
        databaseRef.child("users").child(userId).addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user: User? = dataSnapshot.getValue(User::class.java)
                if (user == null) {
                    Log.e(TAG, "User $userId is unexpectedly null")
                    Toast.makeText(this@NewPostActivity, "Error: could not fetch user.", Toast.LENGTH_SHORT).show()
                } else {
                    writeNewPost(userId, author = user.userName, title = "${binding.title.text}", body = "${binding.body.text}")
                }
                setEditingEnabled(true)
                finish()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "getUser:onCancelled", databaseError.toException())
                setEditingEnabled(true)
            }

        })

        finish()
    }

    // private

    fun setEditingEnabled(enabled: Boolean) {
        binding.apply {
            title.isEnabled = enabled
            body.isEnabled = enabled
            done.visibility = if (enabled) View.VISIBLE else View.GONE
        }
    }

    private fun validForm(): Boolean {
        return binding.run {
            val title = "${this.title.text}"
            val body = "${this.body.text}"

            this.title.error = if (title.isEmpty()) "Required" else null
            this.body.error = if (body.isEmpty()) "Required" else null

            title.isNotEmpty() && body.isNotEmpty()
        }
    }

    private fun writeNewPost(userId: String, author: String, title: String, body: String) {
        // Create new post at /user-posts/$userid/$postid and at /posts/$postid simultaneously
        val databaseRef = database.reference
        val postId = databaseRef.child("posts").push().key
        val post = Post(userId, author, title, body).toMap()
        val childUpdates = hashMapOf<String, Any>(
                "/posts/$postId" to post,
                "/user-posts/$userId/$postId" to post
        )
        databaseRef.updateChildren(childUpdates).addOnCompleteListener { task ->
            Log.d(TAG, "post=${task.isSuccessful}, e=${task.exception}")
        }
    }
}
