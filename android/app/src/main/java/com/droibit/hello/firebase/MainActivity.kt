package com.droibit.hello.firebase

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.droibit.hello.firebase.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
}
