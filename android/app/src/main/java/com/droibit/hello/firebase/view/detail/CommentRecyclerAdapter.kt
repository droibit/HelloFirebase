package com.droibit.hello.firebase.view.detail

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.droibit.hello.firebase.R
import com.droibit.hello.firebase.databinding.ViewItemCommentBinding
import com.droibit.hello.firebase.model.Comment
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference

class CommentRecyclerAdapter(private val context: Context, private val databaseRef: DatabaseReference) :
        RecyclerView.Adapter<CommentRecyclerAdapter.ViewHolder>(), ChildEventListener {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding: ViewItemCommentBinding = DataBindingUtil.bind(itemView)

        fun bind(comment: Comment) {
            binding.comment = comment
        }
    }

    companion object {

        private val TAG = CommentRecyclerAdapter::class.java.simpleName
    }

    private val comments: MutableList<Comment> = arrayListOf()

    private val commentIds: MutableList<String> = arrayListOf()

    init {
        databaseRef.addChildEventListener(this)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(comment = comments[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        return ViewHolder(itemView = inflater.inflate(R.layout.view_item_comment, parent, false))
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    // ChildEventListener

    override fun onCancelled(databaseError: DatabaseError) {
        Log.w(TAG, "postComments:onCancelled", databaseError.toException())
        Toast.makeText(context, "Failed to load comments.", Toast.LENGTH_SHORT).show()
    }

    override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {
        Log.d(TAG, "onChildMoved: ${dataSnapshot.key}")
    }

    override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
        Log.d(TAG, "onChildChanged: ${dataSnapshot.key}")

        val newComment = dataSnapshot.getValue(Comment::class.java)
        val commentKey = dataSnapshot.key

        val commentIndex = commentIds.indexOf(commentKey)
        if (commentIndex > -1) {
            comments[commentIndex] = newComment
            notifyItemInserted(commentIndex)
        } else {
            Log.w(TAG, "onChildChanged:unknown_child:$commentKey")
        }
    }

    override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
        Log.d(TAG, "onChildAdded: ${dataSnapshot.key}")

        val comment = dataSnapshot.getValue(Comment::class.java)
        commentIds.add(dataSnapshot.key)
        comments.add(comment)

        notifyItemInserted(comments.size - 1)
    }

    override fun onChildRemoved(dataSnapshot: DataSnapshot) {
        Log.d(TAG, "onChildRemoved: ${dataSnapshot.key}")

        val commentKey = dataSnapshot.key
        val commentIndex = commentIds.indexOf(commentKey)
        if (commentIndex > -1) {
            commentIds.removeAt(commentIndex)
            comments.removeAt(commentIndex)
            notifyItemRemoved(commentIndex)
        } else {
            Log.w(TAG, "onChildRemoved:unknown_child:$commentKey")
        }
    }

    // public

    fun cleanup() {
        databaseRef.removeEventListener(this)
    }
}