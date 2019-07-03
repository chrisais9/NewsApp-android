package kr.koohyongmo.newsapp.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.Realm
import io.realm.kotlin.where
import kr.koohyongmo.newsapp.NewsAdapter
import kr.koohyongmo.newsapp.R
import kr.koohyongmo.newsapp.data.Article
import kr.koohyongmo.newsapp.scrapNews.NewsDataModel




class NewsSavedFragment : Fragment() {

    private lateinit var newsAdapter: NewsAdapter

    val realm = Realm.getDefaultInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_news_list_view, container, false)
        val newsRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerView1)

        newsAdapter = NewsAdapter(emptyList(),false)
        newsRecyclerView.layoutManager = LinearLayoutManager(activity)
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
        } else {
            // User is not viewing the fragment,
            // or fragment is our of the screen
        }
    }

}
