package com.droibit.hello.firebase.view

import com.droibit.hello.firebase.PostListFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query

class RecentPostsFragment : PostListFragment() {

    override fun createQuery(databaseRef: DatabaseReference): Query {
        return databaseRef.child("posts").limitToFirst(100)
    }
}