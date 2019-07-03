package kr.koohyongmo.newsapp

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import kr.koohyongmo.newsapp.ui.NewsOnlineFragment
import kr.koohyongmo.newsapp.ui.NewsSavedFragment


class ContentsPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm)
{
    val PAGE_MAX_CNT = 2

    override fun getCount(): Int {
        return PAGE_MAX_CNT
    }

    override fun getItem(position: Int): Fragment? {
        val fragment = when(position)
        {
            1 -> NewsSavedFragment()
            else -> NewsOnlineFragment()
        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val title = when(position)
        {
            1 -> "저장됨"
            else -> "뉴스"
        }
        return title
    }
}