package com.booleanull.feature_onboarding_ui.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.viewpager.widget.ViewPager
import com.booleanull.core_ui.base.BaseFragment
import com.booleanull.core_ui.permission.PermissionBadStatus
import com.booleanull.core_ui.permission.PermissionController
import com.booleanull.core_ui.widget.setPagerIndicator
import com.booleanull.feature_onboarding_ui.R
import com.booleanull.feature_onboarding_ui.adapter.OnboardingAdapter
import kotlinx.android.synthetic.main.fragment_onboarding.*
import org.koin.android.ext.android.inject


class OnboardingFragment : BaseFragment() {

    private val permissionController: PermissionController by inject()

    private var positionViewPager = 0
    private var positionOffsetViewPager = 0f

    private val buttonPageChangeListener = object : ViewPager.OnPageChangeListener {

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
            positionViewPager = position

            if (position in (viewPager.adapter?.count?.minus(2)
                    ?: 0)..(viewPager.adapter?.count?.minus(1) ?: 0)
            ) {
                nextButton.alpha = kotlin.math.abs(positionOffset - 0.5f) * 2
            } else if (position == viewPager.adapter?.count?.minus(3)) {
                nextButton.alpha =
                    if (positionOffset > 0.5f) kotlin.math.abs(positionOffset - 0.5f) * 2 else 0f
            }

            if (positionOffset > 0.4f && positionOffset < 0.6f) {
                if (position == viewPager.adapter?.count?.minus(2)) {
                    nextButton.text = if (positionOffset > positionOffsetViewPager) {
                        requireContext().getString(R.string.next)
                    } else {
                        requireContext().getString(R.string.take_permission)
                    }
                }
            }

            positionOffsetViewPager = positionOffset
            errorTextView.isInvisible = true
        }

        override fun onPageScrollStateChanged(state: Int) = Unit
        override fun onPageSelected(position: Int) = Unit
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
        viewPager.adapter = OnboardingAdapter(requireContext(), childFragmentManager)
        (viewPager as ViewPager).setPagerIndicator(pagerIndicator)
        viewPager.addOnPageChangeListener(buttonPageChangeListener)

        nextButton.setOnClickListener {
            if (positionOffsetViewPager == 0f) {
                when (positionViewPager) {
                    viewPager.adapter?.count?.minus(2) -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (!permissionController.getPermissionStatus().status) {
                                permissionController.requestPermission().forEach { request ->
                                    if (!request.permissionStatus.status) {
                                        startActivity(request.intent)
                                    }
                                }
                            }
                        }
                    }
                    viewPager.adapter?.count?.minus(1) -> {
                        permissionController.getPermissionStatus()
                            .let { permissionResponse ->
                                if (permissionResponse is PermissionBadStatus) {
                                    errorTextView.text = permissionResponse.message
                                    errorTextView.isInvisible = false
                                } else {
                                    router.back()
                                }
                            }
                    }
                }
            }
        }
    }

    override fun onBackPressed(): Boolean {
        requireActivity().finish()
        return false
    }
}