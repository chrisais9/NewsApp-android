package kr.koohyongmo.newsapp

import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import kr.koohyongmo.newsapp.adapters.ContentsPagerAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pagerAdapter = ContentsPagerAdapter(supportFragmentManager)
        val pager = findViewById<androidx.viewpager.widget.ViewPager>(R.id.container)
        pager.adapter = pagerAdapter

        val tab = findViewById<TabLayout>(R.id.tab)
        tab.setupWithViewPager(pager)

        setTitle("Today TopNews")
    }
}