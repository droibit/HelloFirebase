package com.droibit.hello.firebase.view

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.droibit.hello.firebase.R
import com.droibit.hello.firebase.databinding.ActivityMainBinding
import com.droibit.hello.firebase.view.signin.SignInActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    companion object {

        private val TAG = MainActivity::class.java.simpleName

        @JvmStatic
        fun createIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

    private lateinit var binding: ActivityMainBinding

    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentUser = auth.currentUser
        if (currentUser == null) {
            startSignInActivity()
            return
        }
        Log.d(TAG, "currentUser: ${currentUser.email}")

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val pagerAdapter = object : FragmentPagerAdapter(supportFragmentManager) {

            private val fragments = arrayOf(
                    RecentPostsFragment(),
                    MyPostsFragment(),
                    MyTopPostsFragment()
            )

            private val titles = arrayOf(
                    getString(R.string.main_tabs_recent),
                    getString(R.string.main_tabs_my_posts),
                    getString(R.string.main_tabs_my_top_posts)
            )

            override fun getItem(position: Int) = fragments[position]

            override fun getCount() = fragments.size

            override fun getPageTitle(position: Int) = titles[position]
        }
        binding.container.adapter = pagerAdapter
        binding.tabs.setupWithViewPager(binding.container)

        setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sign_out -> { doFirebaseSignOut(); true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // private

    private fun doFirebaseSignOut() {
        auth.signOut()
        // TODO: sign out with google account

        startSignInActivity()
    }

    private fun startSignInActivity() {
        startActivity(SignInActivity.createIntent(this))
        finish()
    }
}
