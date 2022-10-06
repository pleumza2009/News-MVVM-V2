package com.siamsquared.news2.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siamsquared.news2.model.NewsResponse
import com.siamsquared.news2.repository.NewsRepository
import com.siamsquared.news2.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel (val newsRepository: NewsRepository) : ViewModel() {


    val breakingNews : MutableLiveData<Resource<NewsResponse>>  = MutableLiveData()
    val breakingNewsPage = 1

    init {
        getBreakingNews("us")
    }


    private fun getBreakingNews(countryCode: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode,breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
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
}