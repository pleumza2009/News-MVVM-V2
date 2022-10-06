package com.thanakorn.news2.model

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)