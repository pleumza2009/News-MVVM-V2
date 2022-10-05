package com.siamsquared.news2.repository

import com.siamsquared.news2.api.RetrofitInstance
import com.siamsquared.news2.db.ArticleDatabase

class NewsRepository (val db : ArticleDatabase) {

    suspend fun getBreakingNews(countryCode: String,PageNumber:Int) =
        RetrofitInstance.api.getNews(countryCode,PageNumber)
}