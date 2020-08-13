package com.booleanull.feature_onboarding_ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.booleanull.core_ui.base.BaseFragment
import com.booleanull.core_ui.fragment.ProblemBottomSheetDialogFragment
import com.booleanull.core_ui.setSpannableClick
import com.booleanull.core_ui.setSpannableLink
import com.booleanull.feature_onboarding_ui.R
import com.booleanull.feature_onboarding_ui.adapter.OnboardingAdapter
import kotlinx.android.synthetic.main.fragment_onboarding_item.*

class OnboardingItemFragment : BaseFragment() {

    private var position: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            position = bundle.getInt(OnboardingAdapter.POSITION)
        }
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

        when (position) {
            0 -> {
                titleTextView.text = requireContext().getString(R.string.app_name)
                descriptionTextView.text =
                    requireContext().getString(R.string.onboarding_description_first)
            }
            1 -> {
                titleTextView.text = requireContext().getString(R.string.onboarding_title_second)
                descriptionTextView.setSpannableClick(
                    requireContext().getString(R.string.onboarding_description_second),
                    requireContext().getString(R.string.problem_service)
                ) {
                    ProblemBottomSheetDialogFragment().showNow(childFragmentManager, null)
                }
            }
            2 -> {
                titleTextView.text = requireContext().getString(R.string.mark_app)
                descriptionTextView.setSpannableLink(
                    requireContext().getString(R.string.mark_app_playmarket),
                    requireContext().getString(R.string.play_market),
                    requireContext().getString(R.string.play_market_url)
                )
            }
        }
    }
}