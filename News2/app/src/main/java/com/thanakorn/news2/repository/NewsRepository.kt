package com.thanakorn.news2.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.thanakorn.news2.api.RetrofitInstance
import com.thanakorn.news2.db.ArticleDatabase
import com.thanakorn.news2.model.Article
import com.thanakorn.news2.paging.PagingSource

class NewsRepository (val db : ArticleDatabase) {

    suspend fun getBreakingNews(countryCode: String,PageNumber:Int) =
        RetrofitInstance.api.getNews(countryCode,PageNumber)


    fun getBreakingNewsPaging(countryCode: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PagingSource(countryCode) }
        ).liveData

    suspend fun searchNews(searchQuery: String,PageNumber:Int) =
        RetrofitInstance.api.searchForNews(searchQuery,PageNumber)

    suspend fun upsert(article: Article) = db.getArticleDao().upsert(article)

    suspend fun deleteArticle(article: Article) = db.getArticleDao().deleteArticle(article)

    fun getFavoriteNews() = db.getArticleDao().getAllArticles()

}