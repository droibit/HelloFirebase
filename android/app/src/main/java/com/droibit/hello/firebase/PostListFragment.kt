package com.droibit.hello.firebase

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.droibit.hello.firebase.databinding.FragmentAllPostBinding
import com.droibit.hello.firebase.model.Post
import com.droibit.hello.firebase.view.PostRecyclerAdapter
import com.droibit.hello.firebase.view.detail.PostDetailActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

abstract class PostListFragment : Fragment(), PostRecyclerAdapter.OnClickListener {

    protected val currentUser: FirebaseUser
        get() {
            return auth.currentUser ?: error("Firebase user does not exist.")
        }

    private lateinit var binding: FragmentAllPostBinding

    private lateinit var listAdapter: PostRecyclerAdapter

    private val auth = FirebaseAuth.getInstance()

    private val database = FirebaseDatabase.getInstance()

    protected abstract fun createQuery(databaseRef: DatabaseReference): Query

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_post, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.postList.layoutManager = LinearLayoutManager(context).apply {
            reverseLayout = true
            stackFromEnd = true
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val recentPostsQuery = createQuery(databaseRef = database.reference)
        listAdapter = PostRecyclerAdapter(userId = currentUser.uid, postsQuery = recentPostsQuery).also {
            binding.postList.adapter = it
            it.clickListener = this
        }
    }

    override fun onDestroy() {
        listAdapter.cleanup()
        super.onDestroy()
    }

    // PostRecyclerAdapter.OnClickListener

    override fun onItemClick(postId: String) {
        val intent = PostDetailActivity.createIntent(context, postId)
        context.startActivity(intent)
    }

    override fun onStarClick(postId: String, post: Post) {
        // Need to write to both places the post is stored
        val databaseRef = database.reference
        val globalPostRef = databaseRef.child("posts").child(postId)
        val globalUserPostRef = databaseRef.child("user-posts").child(post.uid).child(postId)

        val currentUser = auth.currentUser ?: error("Firebase user does not exist.")
        toggleStar(currentUser.uid, globalPostRef)
        toggleStar(currentUser.uid, globalUserPostRef)
    }

    // Private

    private fun toggleStar(userId: String, postRef: DatabaseReference) {
        postRef.runTransaction(object : Transaction.Handler {
            override fun doTransaction(mutableData: MutableData): Transaction.Result {
                val p: Post = mutableData.getValue(Post::class.java)
                        ?: return Transaction.success(mutableData)

                if (p.stars.containsKey(userId)) {
                    p.starCount = p.starCount - 1
                    p.stars.remove(userId)
                } else {
                    p.starCount = p.starCount + 1
                    p.stars[userId] = true
                }

                // Set value and report transaction success
                mutableData.value = p
                return Transaction.success(mutableData)
            }

            override fun onComplete(databaseError: DatabaseError?, b: Boolean, dataSnapshot: DataSnapshot) {
                Log.d("Error", "postTransaction:onComplete:" + databaseError)

                val e = databaseError?.toException()
                if (e != null) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}