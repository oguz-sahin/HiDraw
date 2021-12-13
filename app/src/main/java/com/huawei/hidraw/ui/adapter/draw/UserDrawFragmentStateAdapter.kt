package com.huawei.hidraw.ui.adapter.draw

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.huawei.hidraw.ui.profile.DrawListFragment
import com.huawei.hidraw.ui.profile.DrawListTypes.USER_ATTENDED
import com.huawei.hidraw.ui.profile.DrawListTypes.USER_CREATED

/**
 * Created by Oguz Sahin on 12/6/2021.
 */
class UserDrawFragmentStateAdapter constructor(fragment: Fragment) :
    FragmentStateAdapter(fragment) {


    override fun getItemCount(): Int = NUM_PAGES

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            USER_ATTENDED.pageIndex -> DrawListFragment.newInstance(USER_ATTENDED)
            USER_CREATED.pageIndex -> DrawListFragment.newInstance(USER_CREATED)
            else -> throw Exception("Not Found position")
        }
    }

    companion object {
        const val NUM_PAGES = 2
    }
}