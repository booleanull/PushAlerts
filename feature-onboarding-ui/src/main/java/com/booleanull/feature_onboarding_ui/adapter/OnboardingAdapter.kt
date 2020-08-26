package com.booleanull.feature_onboarding_ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.booleanull.feature_onboarding_ui.fragment.OnboardingItemFragment

class OnboardingAdapter(
    fragmentManager: FragmentManager
) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return OnboardingItemFragment.newInstance(position)
    }

    override fun getCount(): Int {
        return ONBOARDING_COUNT
    }

    companion object {
        const val ONBOARDING_COUNT = 3
    }
}