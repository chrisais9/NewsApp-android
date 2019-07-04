package kr.koohyongmo.newsapp.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.SearchView
import kr.koohyongmo.newsapp.BuildConfig
import kr.koohyongmo.newsapp.adapters.NewsAdapter
import kr.koohyongmo.newsapp.retrofit.NewsRequest
import kr.koohyongmo.newsapp.R
import kr.koohyongmo.newsapp.retrofit.NewsApiRepo
import kr.koohyongmo.newsapp.data.NewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NewsOnlineFragment : Fragment() {


    private val API_KEY = BuildConfig.NEWS_API_KEY

    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_news_list_view, container, false)
        val newsRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerView1)

        newsAdapter = NewsAdapter(emptyList(), true)
        newsRecyclerView.layoutManager = LinearLayoutManager(activity)
        newsRecyclerView.adapter = newsAdapter

        setHasOptionsMenu(true)

        return view
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()

        inflater.inflate(R.menu.main_menu, menu)

        val item = menu.findItem(R.id.search)
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)

        val searchView = item.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                RequestApiServer(query)

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    fun RequestApiServer(query : String) {

        val retrofit = Retrofit.Builder()
                .baseUrl(NewsApiRepo.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val apiServer = retrofit.create(NewsApiRepo::class.java)
        val newsRequest = NewsRequest(query)
        val call = apiServer.getNewsResponse(newsRequest.query,API_KEY)
        call.enqueue(object : Callback<NewsResponse> {


            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if(response.isSuccessful) {
                    Log.d("retro", response.body()?.totalResults.toString())
                    newsAdapter.listArticle = response.body()?.articles!!
                    newsAdapter.notifyDataSetChanged()
                }
                else{
                    Log.e("retro", response.raw().message())
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

}
