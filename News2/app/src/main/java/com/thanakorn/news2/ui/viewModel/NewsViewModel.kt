package com.thanakorn.news2.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.thanakorn.news2.model.Article
import com.thanakorn.news2.model.NewsResponse
import com.thanakorn.news2.repository.NewsRepository
import com.thanakorn.news2.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel (val newsRepository: NewsRepository) : ViewModel() {


    val breakingNews : MutableLiveData<Resource<NewsResponse>>  = MutableLiveData()
    val breakingNewsPage = 1

    val searchNews : MutableLiveData<Resource<NewsResponse>>  = MutableLiveData()
    val searchNewsPage = 1

    /*
    init {
        getBreakingNews("us")
    }

     */


    private fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode,breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

     fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = newsRepository.searchNews(searchQuery,breakingNewsPage)
        searchNews.postValue(handleSearchNewsResponse(response))
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body().let {
                return Resource.Success(it)
            }
        }else{
            return  Resource.Error(response.message())
        }
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>) : Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body().let {
                return Resource.Success(it)
            }
        }else{
            return  Resource.Error(response.message())
        }
    }

    fun saveNewsArticle(article: Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun deleteNewsArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

    fun getFavoriteNews() = newsRepository.getFavoriteNews()


    //PAGING 3
    private val currentQuery = MutableLiveData(DEFAULT_QUERY)

    val articles = currentQuery.switchMap { countryCode ->
        newsRepository.getBreakingNewsPaging(countryCode).cachedIn(viewModelScope)
    }

    companion object {
        private const val DEFAULT_QUERY = "us"
    }
}