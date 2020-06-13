package com.booleanull.feature_onboarding_ui.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.booleanull.feature_onboarding_ui.fragment.OnboardingItemFragment

class OnboardingAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return OnboardingItemFragment().apply {
            arguments = Bundle().apply { putString("description", "description $position") }
        }
    }

    override fun getCount(): Int {
        return 5
    }
}