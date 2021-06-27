package org.booleanull.feature_onboarding_ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import org.booleanull.feature_onboarding_ui.fragment.OnboardingItemFragment

internal class OnboardingAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return OnboardingItemFragment.newInstance(OnboardingItem.values()[position])
    }

    override fun getCount(): Int {
        return OnboardingItem.values().size
    }

    enum class OnboardingItem {
        WELCOME_SCREEN,
        PERMISSION_SCREEN,
        END_SCREEN
    }
}