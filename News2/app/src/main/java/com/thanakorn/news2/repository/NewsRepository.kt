package com.thanakorn.news2.repository

import com.thanakorn.news2.api.RetrofitInstance
import com.thanakorn.news2.db.ArticleDatabase

class NewsRepository (val db : ArticleDatabase) {

    suspend fun getBreakingNews(countryCode: String,PageNumber:Int) =
        RetrofitInstance.api.getNews(countryCode,PageNumber)

    suspend fun searchNews(searchQuery: String,PageNumber:Int) =
        RetrofitInstance.api.searchForNews(searchQuery,PageNumber)
}