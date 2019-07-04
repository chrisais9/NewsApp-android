package kr.koohyongmo.newsapp.adapters

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import kr.koohyongmo.newsapp.ui.NewsArticleView
import kr.koohyongmo.newsapp.R
import kr.koohyongmo.newsapp.data.Article

class NewsAdapter(var listArticle: List<Article>, var isFromOnlineFragment: Boolean) : RecyclerView.Adapter<NewsAdapter.MyViewHolder>(){

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val title = listArticle[position].title
        val contents = listArticle[position].description
        val image = listArticle[position].urlToImage
        val url = listArticle[position].url

        holder.newsTitle.text = "${title}"
        holder.newsContent.text = "${contents}"
        Picasso.get().load(image).resize(100,100).centerInside().into(holder.newsImage)

        holder.itemView.setOnClickListener { view ->

            val newsArticleViewIntent = Intent(view.context, NewsArticleView::class.java)
            newsArticleViewIntent.putExtra("title",title)
            newsArticleViewIntent.putExtra("contents",contents)
            newsArticleViewIntent.putExtra("image",image)
            newsArticleViewIntent.putExtra("url",url)
            newsArticleViewIntent.putExtra("onlineflag",isFromOnlineFragment.toString())

            view.context.startActivity(newsArticleViewIntent)
        }

    }

    override fun getItemCount(): Int {
        return listArticle.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return MyViewHolder(view)
    }

    inner class MyViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        var newsTitle: TextView
        var newsContent: TextView
        var newsImage : ImageView

        init {
            newsTitle = view.findViewById<View>(R.id.tv_news_title) as TextView
            newsContent = view.findViewById<View>(R.id.tv_news_content) as TextView
            newsImage = view.findViewById<View>(R.id.iv_article_image) as ImageView
        }
    }

}

