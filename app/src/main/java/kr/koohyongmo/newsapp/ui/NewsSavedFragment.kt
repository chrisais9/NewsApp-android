package kr.koohyongmo.newsapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.Realm
import io.realm.kotlin.where
import kr.koohyongmo.newsapp.adapters.NewsAdapter
import kr.koohyongmo.newsapp.R
import kr.koohyongmo.newsapp.data.Article
import kr.koohyongmo.newsapp.realm.NewsDataModel



class NewsSavedFragment : androidx.fragment.app.Fragment() {

    private lateinit var newsAdapter: NewsAdapter

    val realm = Realm.getDefaultInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_news_list_view, container, false)
        val newsRecyclerView = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerView1)

        newsAdapter = NewsAdapter(emptyList(), false)
        newsRecyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        newsRecyclerView.adapter = newsAdapter

        return view
    }
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            // User is viewing the fragment,
            // or fragment is inside the screen
            val realmResult = realm.where<NewsDataModel>().findAll()
            val list : MutableList<Article> = arrayListOf()
            for( i in realmResult){
                val article = Article(i.title,i.contents,i.url,i.image)
                Log.d("article",article.toString())
                list.add(article)
            }
            Log.d("size",list.size.toString())

            newsAdapter.listArticle = list
            newsAdapter.notifyDataSetChanged()

        }
    }

}
