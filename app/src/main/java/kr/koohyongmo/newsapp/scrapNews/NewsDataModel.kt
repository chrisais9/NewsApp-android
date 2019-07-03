package kr.koohyongmo.newsapp.scrapNews

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class NewsDataModel : RealmObject(){
    @PrimaryKey
    open var url : String? = null

    open var title : String? = null
    open var contents : String? = null
    open var image : String? = null
}