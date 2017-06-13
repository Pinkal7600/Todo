package com.pinkal.todo.Intro.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.pinkal.todo.Intro.fragment.FirstIntroFragment
import com.pinkal.todo.Intro.fragment.FourthIntroFragment
import com.pinkal.todo.Intro.fragment.SecondIntroFragment
import com.pinkal.todo.Intro.fragment.ThirdIntroFragment
import com.pinkal.todo.utils.TOTAL_INTRO_PAGES

/**
 * Created by Pinkal on 13/6/17.
 */
class IntroAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {


    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return FirstIntroFragment.newInstance(0)
            1 -> return SecondIntroFragment.newInstance(1)
            2 -> return ThirdIntroFragment.newInstance(2)
            3 -> return FourthIntroFragment.newInstance(3)
            else -> return ThirdIntroFragment.newInstance(2)
        }
    }

    override fun getCount(): Int {
        return TOTAL_INTRO_PAGES
    }
}