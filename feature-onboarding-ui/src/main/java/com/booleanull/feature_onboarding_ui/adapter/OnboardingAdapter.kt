package com.booleanull.feature_onboarding_ui.adapter

import android.content.Context
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.booleanull.core_ui.base.InfoBottomSheetDialogFragment
import com.booleanull.feature_onboarding_ui.R
import com.booleanull.feature_onboarding_ui.data.OnboardingMessage
import com.booleanull.feature_onboarding_ui.fragment.OnboardingItemFragment

class OnboardingAdapter(
    private val context: Context,
    private val fragmentManager: FragmentManager
) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return OnboardingItemFragment().apply {
            arguments = Bundle().apply {
                putParcelable(
                    "message",
                    getOnboardingMessage(position, this@OnboardingAdapter.context)
                )
            }
        }
    }

    override fun getCount(): Int {
        return ONBOARDING_COUNT
    }

    private fun getOnboardingMessage(index: Int, context: Context): OnboardingMessage {
        return when (index) {
            0 -> OnboardingMessage(
                context.getString(R.string.app_name),
                context.getString(R.string.onboarding_description_first)
            )
            1 -> OnboardingMessage(
                context.getString(R.string.onboarding_title_second),
                SpannableString(context.getString(R.string.onboarding_description_second)).apply {
                    val substring = context.getString(R.string.onboarding_description_second_link)
                    setSpan(
                        object : ClickableSpan() {
                            override fun onClick(p0: View) {
                                InfoBottomSheetDialogFragment.create("Информация", "Какая-то")
                                    .also {
                                        it.showNow(
                                            fragmentManager,
                                            InfoBottomSheetDialogFragment::class.java.simpleName
                                        )
                                    }
                            }
                        },
                        indexOf(substring),
                        indexOf(substring) + substring.length,
                        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            )
            2 -> OnboardingMessage(context.getString(R.string.onboarding_title_third),
                SpannableString(context.getString(R.string.onboarding_description_third)).apply {
                    val substring = context.getString(R.string.play_market)
                    setSpan(
                        URLSpan(context.getString(R.string.play_market_url)),
                        indexOf(substring),
                        indexOf(substring) + substring.length,
                        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                })
            else -> throw IllegalArgumentException("Onboarding message with index was not found.")
        }
    }

    companion object {

        const val ONBOARDING_COUNT = 3
    }
}