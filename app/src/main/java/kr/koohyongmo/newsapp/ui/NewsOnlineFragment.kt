package kr.koohyongmo.newsapp.ui

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.SearchView
import kotlinx.android.synthetic.main.activity_main.*
import kr.koohyongmo.newsapp.BuildConfig
import kr.koohyongmo.newsapp.R
import kr.koohyongmo.newsapp.adapters.NewsAdapter
import kr.koohyongmo.newsapp.data.NewsResponse
import kr.koohyongmo.newsapp.retrofit.NewsApiRepo
import kr.koohyongmo.newsapp.retrofit.NewsRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NewsOnlineFragment : androidx.fragment.app.Fragment() {


    private val API_KEY = BuildConfig.NEWS_API_KEY

    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_news_list_view, container, false)
        val newsRecyclerView = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerView1)

        newsAdapter = NewsAdapter(emptyList(), true)
        newsRecyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        newsRecyclerView.adapter = newsAdapter

        initHeadline()

        setHasOptionsMenu(true)

        return view
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        val waitingTime = 500
        var cntr : CountDownTimer? = null


        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()

        inflater.inflate(R.menu.main_menu, menu)

        val item = menu.findItem(R.id.search)
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW or MenuItem.SHOW_AS_ACTION_IF_ROOM)

        val searchView = item.actionView as SearchView


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                requestApiServer(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (cntr != null) {
                    cntr!!.cancel()
                }
                cntr = object : CountDownTimer(waitingTime.toLong(), 500) {

                    override fun onTick(millisUntilFinished: Long) {
                    }

                    override fun onFinish() {

                        requestApiServer(newText)
                    }
                }
                cntr!!.start()
                return false
            }
        })
    }

    fun initHeadline(){

        val retrofit = Retrofit.Builder()
                .baseUrl(NewsApiRepo.baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val apiServer = retrofit.create(NewsApiRepo::class.java)
        val call = apiServer.getHeadlines("us",API_KEY)
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

    fun requestApiServer(query : String) {

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
