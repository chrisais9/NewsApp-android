package kr.koohyongmo.newsapp.ui

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import android.widget.Toast
import io.realm.Realm
import io.realm.kotlin.createObject

import kotlinx.android.synthetic.main.activity_news_article_view.*
import kr.koohyongmo.newsapp.R
import kr.koohyongmo.newsapp.realm.NewsDataModel



class NewsArticleView : AppCompatActivity() {

    private lateinit var articleContent : WebView
    val realm= Realm.getDefaultInstance()
    var url : String? = null
    var title : String? = null
    var contents : String? = null
    var image : String? = null
    var onlineFlag : String? = null



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_article_view)

        if(intent.hasExtra("url")){
            url = intent.getStringExtra("url")
            title = intent.getStringExtra("title")
            contents = intent.getStringExtra("contents")
            image = intent.getStringExtra("image")
            onlineFlag = intent.getStringExtra("onlineflag")

        }


        articleContent = findViewById(R.id.wv_article)
        articleContent.settings.loadsImagesAutomatically = true
        articleContent.settings.javaScriptEnabled = true
        articleContent.settings.domStorageEnabled = true
        articleContent.settings.setSupportZoom(true)

        articleContent.loadUrl(url)

        setSupportActionBar(toolbar)

        supportActionBar?.subtitle = url
        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        if(onlineFlag == "false") {
            fab.hide()
        }
        else {

            fab.setOnClickListener { view ->

                val snackbar = Snackbar.make(view, "스크랩하기", Snackbar.LENGTH_LONG)
                snackbar.setAction("OK") { insertNewsToRealm() }
                snackbar.show()
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun insertNewsToRealm(){

        realm.beginTransaction()

        val newItem = realm.createObject<NewsDataModel>(url)
        newItem.title = title
        newItem.contents = contents
        newItem.image = image

        realm.commitTransaction()

        Toast.makeText(this,"스크랩 되었습니다.",Toast.LENGTH_LONG).show()

    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close() //인스턴스해제
    }

}