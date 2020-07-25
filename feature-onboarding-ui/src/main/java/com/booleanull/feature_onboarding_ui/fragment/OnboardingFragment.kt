package com.booleanull.feature_onboarding_ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.booleanull.core_ui.base.BaseFragment
import com.booleanull.core_ui.widget.setPagerIndicator
import com.booleanull.feature_onboarding_ui.R
import com.booleanull.feature_onboarding_ui.adapter.OnboardingAdapter
import kotlinx.android.synthetic.main.fragment_onboarding.*

class OnboardingFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager.adapter = OnboardingAdapter(childFragmentManager)
        (viewPager as ViewPager).setPagerIndicator(pagerIndicator)
    }
}