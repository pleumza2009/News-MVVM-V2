package com.thanakorn.news2.repository

import com.thanakorn.news2.api.RetrofitInstance
import com.thanakorn.news2.db.ArticleDatabase
import com.thanakorn.news2.model.Article

class NewsRepository (val db : ArticleDatabase) {

    suspend fun getBreakingNews(countryCode: String,PageNumber:Int) =
        RetrofitInstance.api.getNews(countryCode,PageNumber)

    suspend fun searchNews(searchQuery: String,PageNumber:Int) =
        RetrofitInstance.api.searchForNews(searchQuery,PageNumber)

    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)

    fun getFavoriteNews() = db.getArticleDao().getAllArticles()

}