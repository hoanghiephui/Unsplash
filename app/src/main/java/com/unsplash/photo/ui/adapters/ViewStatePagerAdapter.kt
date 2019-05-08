package com.unsplash.photo.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewStatePagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    var listFragment: MutableList<Fragment> = mutableListOf()
    var listTitle: MutableList<String> = mutableListOf()
    override fun getItem(positions: Int): Fragment {
        return listFragment[positions]
    }

    override fun getCount(): Int {
        return listFragment.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return listTitle[position]
    }
}