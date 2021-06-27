package org.booleanull.feature_onboarding_ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.fragment_onboarding.*
import org.booleanull.core_ui.base.BaseFragment
import org.booleanull.core_ui.widget.setPagerIndicator
import org.booleanull.feature_onboarding_ui.R
import org.booleanull.feature_onboarding_ui.adapter.OnboardingAdapter

internal class OnboardingFragment : BaseFragment() {

    private val onboardingAdapter by lazy {
        OnboardingAdapter(childFragmentManager)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewPager.adapter = onboardingAdapter
        (viewPager as ViewPager).setPagerIndicator(pagerIndicator)
    }

    override fun onBackPressed(): Boolean {
        requireActivity().finish()
        return false
    }
}