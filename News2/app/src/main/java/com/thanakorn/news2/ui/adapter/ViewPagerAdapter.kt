package com.thanakorn.news2.ui.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.util.*

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle, fragmentList: ArrayList<Fragment>) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private var fragmentList = ArrayList<Fragment>()



    init {
        this.fragmentList = fragmentList

    }

    fun add(index: Int, fragment: Fragment) {
        fragmentList.add(index, fragment)
        notifyItemChanged(index)
    }

    fun refreshFragment(index: Int, fragment: Fragment) {
        fragmentList[index] = fragment
        notifyItemChanged(index)
    }

    fun remove(index: Int) {
        fragmentList.removeAt(index)
        notifyItemChanged(index)
    }

    fun reCreate(index: Int, fragment: Fragment) {
        fragmentList.removeAt(index)
        fragmentList.add(index, fragment)
        notifyItemChanged(index)
    }

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
      return  fragmentList[position]
    }


}