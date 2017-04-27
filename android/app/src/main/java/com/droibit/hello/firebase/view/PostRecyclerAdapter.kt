package com.droibit.hello.firebase.view

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import com.droibit.hello.firebase.R
import com.droibit.hello.firebase.databinding.ViewItemPostBinding
import com.droibit.hello.firebase.model.Post
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.google.firebase.database.Query

class PostRecyclerAdapter(val userId: String, postsQuery: Query) :
        FirebaseRecyclerAdapter<Post, PostRecyclerAdapter.ViewHolder>(
                Post::class.java, R.layout.view_item_post, ViewHolder::class.java, postsQuery) {

    interface OnClickListener {

        fun onItemClick(postId: String)

        fun onStarClick(postId: String, post: Post)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding: ViewItemPostBinding = DataBindingUtil.bind(itemView)

        fun bind(userId: String, post: Post, listener: (View)->Unit) {
            binding.apply {
                this.userId = userId
                this.post = post
                this.setListener(listener)
            }
        }
    }

    var clickListener: OnClickListener? = null

    override fun populateViewHolder(viewHolder: ViewHolder, post: Post, position: Int) {
        val databaseRef = getRef(position)
        val postId = databaseRef.key

        viewHolder.bind(userId, post) {
            when (it.id) {
                R.id.item_container -> clickListener?.onItemClick(postId)
                R.id.star_container -> clickListener?.onStarClick(postId, post)
            }
        }
    }
}