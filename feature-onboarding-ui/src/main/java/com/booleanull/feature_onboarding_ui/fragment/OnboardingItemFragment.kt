package com.booleanull.feature_onboarding_ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.booleanull.core_ui.base.BaseFragment
import com.booleanull.core_ui.handler.NavigationDeeplinkHandler
import com.booleanull.feature_onboarding_ui.R
import kotlinx.android.synthetic.main.fragment_onboarding_item.*

class OnboardingItemFragment : BaseFragment() {

    private lateinit var description: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        description = arguments?.getString("description") ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        descriptionTextView.text = description
        descriptionTextView.setOnClickListener {
            router.back()
        }
    }
}