package com.thanakorn.news2.paging
import com.thanakorn.news2.model.Article
import java.io.IOException
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.thanakorn.news2.api.RetrofitInstance
import retrofit2.HttpException

private const val STARTING_PAGE_INDEX = 1

class PagingSource(
    private val countryCode: String
) : PagingSource<Int, Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = RetrofitInstance.api.getNewsPaging(countryCode,position,params.loadSize)
            val articles = response.articles

            LoadResult.Page(
                data = articles,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (articles.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}