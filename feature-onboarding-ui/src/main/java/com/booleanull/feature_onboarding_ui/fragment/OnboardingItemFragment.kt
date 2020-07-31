package com.booleanull.feature_onboarding_ui.fragment

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.booleanull.core_ui.base.BaseFragment
import com.booleanull.feature_onboarding_ui.R
import com.booleanull.feature_onboarding_ui.data.OnboardingMessage
import kotlinx.android.synthetic.main.fragment_onboarding_item.*

class OnboardingItemFragment : BaseFragment() {

    private lateinit var onboardingMessage: OnboardingMessage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<OnboardingMessage>("message")?.let { message ->
            onboardingMessage = message
        } ?: throw IllegalStateException("Required value was null.")
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

        titleTextView.text = onboardingMessage.title
        descriptionTextView.text = onboardingMessage.description
        descriptionTextView.movementMethod = LinkMovementMethod.getInstance()
    }
}