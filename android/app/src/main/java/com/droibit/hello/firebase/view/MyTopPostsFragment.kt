package com.droibit.hello.firebase.view

import com.droibit.hello.firebase.model.Post
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query

class MyTopPostsFragment : PostListFragment() {

    override fun createQuery(databaseRef: DatabaseReference): Query {
        return databaseRef.child("user-posts")
                .child(currentUser.uid)
                .orderByChild(Post::starCount.name)
    }
}