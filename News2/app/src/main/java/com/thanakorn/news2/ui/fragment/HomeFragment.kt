package com.thanakorn.news2.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.thanakorn.news2.R
import com.thanakorn.news2.databinding.FragmentHomeBinding
import com.thanakorn.news2.model.Article
import com.thanakorn.news2.ui.activity.MainActivity
import com.thanakorn.news2.ui.adapter.NewsAdapter
import com.thanakorn.news2.ui.adapter.NewsPagingAdapter
import com.thanakorn.news2.ui.adapter.ViewPagerAdapter
import com.thanakorn.news2.ui.viewModel.NewsViewModel
import com.thanakorn.news2.util.Resource
import java.util.ArrayList

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment(),NewsPagingAdapter.OnItemClickListener {
    private var param1: String? = null
    private var param2: String? = null

    lateinit var viewModel: NewsViewModel
    lateinit var newsAdapter: NewsAdapter
    lateinit var viewPagerAdapter: ViewPagerAdapter

    private lateinit var binding : FragmentHomeBinding

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
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel
        /*
        setupRecycleView()
/*
        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        newsAdapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        //show message
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
                else -> {}
            }
        })

        newsAdapter.setOnItemClickListener {
            val bundle =  Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(R.id.action_homeFragment_to_articelFragment,bundle)
        }

 */

        val adapter = NewsPagingAdapter(this)

        binding.apply {
            rvBreakingNews.setHasFixedSize(true)
            rvBreakingNews.adapter = adapter
        }

        viewModel.articles.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

         */

        setupViewPagerAndTabs()

    }

    override fun onItemClick(article: Article) {
        val bundle =  Bundle().apply {
            putSerializable("article",article)
        }
        findNavController().navigate(R.id.action_homeFragment_to_articelFragment,bundle)
    }

    private fun hideProgressBar() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.paginationProgressBar.visibility = View.VISIBLE
    }


    private fun setupViewPagerAndTabs(){
        val fragmentList: ArrayList<Fragment> = ArrayList()
        fragmentList.add(CategoryFragment.newInstance("",""))
        fragmentList.add(CategoryFragment.newInstance("",""))
        viewPagerAdapter = ViewPagerAdapter(childFragmentManager, lifecycle, fragmentList)
        binding.vpCategory.apply {
            adapter  = viewPagerAdapter
            isUserInputEnabled = false
        }

        binding.categoryTabs.apply {
            addTab(this.newTab().setText("business"))
            addTab(this.newTab().setText("entertainment"))
            addTab(this.newTab().setText("entertainment"))
            addTab(this.newTab().setText("general"))
            addTab(this.newTab().setText("health"))
            addTab(this.newTab().setText("science"))
            addTab(this.newTab().setText("sports"))
            addTab(this.newTab().setText("technology"))


            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab) {
                    binding.vpCategory.currentItem = tab.position

                }

                override fun onTabUnselected(tab: TabLayout.Tab) {}

                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }


    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}