package com.droibit.hello.firebase.view

import com.droibit.hello.firebase.PostListFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query

class MyPostsFragment : PostListFragment() {

    override fun createQuery(databaseRef: DatabaseReference): Query {
        return databaseRef.child("user-posts").child(currentUser.uid)
    }
}