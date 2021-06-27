package org.booleanull.feature_onboarding_ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.fragment_onboarding_item.*
import org.booleanull.core.permission.PermissionCompositeController
import org.booleanull.core.permission.PermissionStatus
import org.booleanull.core_ui.base.BaseFragment
import org.booleanull.core_ui.fragment.PermissionFragment
import org.booleanull.core_ui.fragment.ProblemBottomSheetDialogFragment
import org.booleanull.core_ui.getSpannableLink
import org.booleanull.core_ui.setSpannableClick
import org.booleanull.core_ui.setSpannableLink
import org.booleanull.feature_onboarding_ui.R
import org.booleanull.feature_onboarding_ui.adapter.OnboardingAdapter
import org.koin.android.ext.android.inject

internal class OnboardingItemFragment : BaseFragment() {

    private val permissionCompositeController: PermissionCompositeController by inject()

    private var onboardingItem: OnboardingAdapter.OnboardingItem =
        OnboardingAdapter.OnboardingItem.WELCOME_SCREEN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            onboardingItem = OnboardingAdapter.OnboardingItem.values()[bundle.getInt(ITEM)]
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding_item, container, false)
    }

    override fun onResume() {
        super.onResume()
        updateEndStatusScreen()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (onboardingItem) {
            OnboardingAdapter.OnboardingItem.WELCOME_SCREEN -> {
                setWelcomeScreen()
            }
            OnboardingAdapter.OnboardingItem.PERMISSION_SCREEN -> {
                setPermissionScreen()
            }
            OnboardingAdapter.OnboardingItem.END_SCREEN -> {
                setEndScreen()
            }
        }
    }

    private fun setWelcomeScreen() {
        iconImageView.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_notifications
            )
        )
        titleTextView.text = requireContext().getString(R.string.app_name)
        descriptionTextView.text =
            requireContext().getString(R.string.onboarding_description_first)
        nextButton.isVisible = false
        errorTextView.isVisible = false
    }

    private fun setPermissionScreen() {
        iconImageView.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_assignment
            )
        )
        titleTextView.text = requireContext().getString(R.string.onboarding_title_second)
        descriptionTextView.setSpannableClick(
            requireContext().getString(R.string.onboarding_description_second),
            requireContext().getString(R.string.problem_service)
        ) {
            ProblemBottomSheetDialogFragment().showNow(childFragmentManager, null)
        }
        nextButton.isVisible = true
        nextButton.text = requireContext().getString(R.string.take_permission)
        errorTextView.isVisible = false
        nextButton.setOnClickListener {
            PermissionFragment().showNow(childFragmentManager, null)
        }
    }

    private fun setEndScreen() {
        iconImageView.setImageDrawable(
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_star
            )
        )
        titleTextView.text = requireContext().getString(R.string.mark_app)
        descriptionTextView.setSpannableLink(
            getSpannableLink(
                requireContext().getString(R.string.mark_app_playmarket),
                requireContext().getString(R.string.privacy_policy),
                requireContext().getString(R.string.privacy_policy_url)
            ),
            requireContext().getString(R.string.play_market),
            requireContext().getString(R.string.play_market_url)
        )
        nextButton.isVisible = true
        nextButton.text = requireContext().getString(R.string.next)
        nextButton.setOnClickListener {
            router.back()
        }
    }

    private fun updateEndStatusScreen() {
        if (onboardingItem == OnboardingAdapter.OnboardingItem.END_SCREEN) {
            val permissionResponse = permissionCompositeController.getPermissionStatus()
            if (permissionResponse is PermissionStatus.PermissionBadStatus) {
                errorTextView.text = permissionResponse.message
                errorTextView.isVisible = true
                nextButton.isEnabled = false
            } else {
                errorTextView.isVisible = false
                nextButton.isEnabled = true
            }
        }
    }

    companion object {
        private const val ITEM = "item"

        fun newInstance(onboardingItem: OnboardingAdapter.OnboardingItem): OnboardingItemFragment {
            return OnboardingItemFragment().apply {
                arguments = Bundle(1).apply {
                    putInt(ITEM, onboardingItem.ordinal)
                }
            }
        }
    }
}