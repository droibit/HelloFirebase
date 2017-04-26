package com.droibit.hello.firebase.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.droibit.hello.firebase.R
import com.droibit.hello.firebase.databinding.FragmentAllPostBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RecentPostsFragment : Fragment() {

    private lateinit var binding: FragmentAllPostBinding

    private lateinit var listAdapter: PostRecyclerAdapter

    private val auth = FirebaseAuth.getInstance()

    private val database = FirebaseDatabase.getInstance()

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

        val currentUser = auth.currentUser ?: error("Firebase user does not exist.")
        val recentPostsQuery = database.reference.child("posts").limitToFirst(100)
        listAdapter = PostRecyclerAdapter(userId = currentUser.uid, postsQuery = recentPostsQuery).also {
            binding.postList.adapter = it
        }
    }

    override fun onDestroy() {
        listAdapter.cleanup()
        super.onDestroy()
    }
}