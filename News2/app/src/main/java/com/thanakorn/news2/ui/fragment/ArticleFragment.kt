package com.thanakorn.news2.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.thanakorn.news2.R
import com.thanakorn.news2.databinding.FragmentArticleBinding
import com.thanakorn.news2.databinding.FragmentFavoriteBinding
import com.thanakorn.news2.ui.activity.MainActivity
import com.thanakorn.news2.ui.adapter.NewsAdapter
import com.thanakorn.news2.ui.viewModel.NewsViewModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ArticleFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    lateinit var viewModel: NewsViewModel

    lateinit var newsAdapter: NewsAdapter

    private val args: ArticleFragmentArgs by navArgs()

    private lateinit var binding : FragmentArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentArticleBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        val article = args.article
        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }

        binding.fab.setOnClickListener {
            viewModel.saveNewsArticle(article)
            Snackbar.make(view,"Article saved Successfully",Snackbar.LENGTH_SHORT).show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ArticleFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}