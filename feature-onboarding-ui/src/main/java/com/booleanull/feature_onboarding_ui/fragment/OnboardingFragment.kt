package com.booleanull.feature_onboarding_ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.booleanull.core_ui.base.BaseFragment
import com.booleanull.feature_onboarding_ui.R
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
        button.setOnClickListener {
            Toast.makeText(requireContext(), "Hello", Toast.LENGTH_SHORT).show()
        }
    }
}