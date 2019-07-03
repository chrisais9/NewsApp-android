package kr.koohyongmo.newsapp.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Article(

    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("urlToImage") val urlToImage: String?

) : Serializable
