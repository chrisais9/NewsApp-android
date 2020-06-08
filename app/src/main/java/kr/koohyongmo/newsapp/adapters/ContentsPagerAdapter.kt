package kr.koohyongmo.newsapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import kr.koohyongmo.newsapp.ui.NewsOnlineFragment
import kr.koohyongmo.newsapp.ui.NewsSavedFragment


class ContentsPagerAdapter(fm: androidx.fragment.app.FragmentManager): androidx.fragment.app.FragmentPagerAdapter(fm)
{
    val PAGE_MAX_CNT = 2

    override fun getCount(): Int {
        return PAGE_MAX_CNT
    }

    override fun getItem(position: Int): androidx.fragment.app.Fragment? {
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