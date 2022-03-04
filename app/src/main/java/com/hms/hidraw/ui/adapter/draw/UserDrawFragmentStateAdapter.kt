/*
 * Copyright 2022. Explore in HMS. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hms.hidraw.ui.adapter.draw

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hms.hidraw.ui.profile.DrawListFragment
import com.hms.hidraw.ui.profile.DrawListTypes.USER_ATTENDED
import com.hms.hidraw.ui.profile.DrawListTypes.USER_CREATED

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
