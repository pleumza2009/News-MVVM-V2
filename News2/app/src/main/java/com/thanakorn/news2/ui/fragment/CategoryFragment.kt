package com.thanakorn.news2.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.thanakorn.news2.R
import com.thanakorn.news2.databinding.FragmentCategoryBinding
import com.thanakorn.news2.databinding.FragmentHomeBinding
import com.thanakorn.news2.model.Article
import com.thanakorn.news2.ui.activity.MainActivity
import com.thanakorn.news2.ui.adapter.NewsAdapter
import com.thanakorn.news2.ui.adapter.NewsPagingAdapter
import com.thanakorn.news2.ui.viewModel.NewsViewModel


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class CategoryFragment : Fragment(),NewsPagingAdapter.OnItemClickListener {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding : FragmentCategoryBinding

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter

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
        binding = FragmentCategoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        val adapter = NewsPagingAdapter(this)

        binding.apply {
            rvCategoryNews.setHasFixedSize(true)
            rvCategoryNews.adapter = adapter
        }

        viewModel.articles.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }


    }

    override fun onItemClick(article: Article) {
        val bundle =  Bundle().apply {
            putSerializable("article",article)
        }
        findNavController().navigate(R.id.action_homeFragment_to_articelFragment,bundle)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}