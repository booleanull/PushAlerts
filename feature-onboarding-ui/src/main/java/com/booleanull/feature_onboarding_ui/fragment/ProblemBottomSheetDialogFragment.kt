package com.booleanull.feature_onboarding_ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.booleanull.core_ui.base.FullRoundedBottomSheetDialogFragment
import com.booleanull.feature_onboarding_ui.R
import kotlinx.android.synthetic.main.fragment_problem_bottom_sheet_dialog.*

class ProblemBottomSheetDialogFragment : FullRoundedBottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_problem_bottom_sheet_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val span = getString(R.string.email)
        descriptionTextView.text =
            SpannableString(getString(R.string.onboarding_problems_description)).apply {
                setSpan(
                    object : ClickableSpan() {
                        override fun onClick(p0: View) {
                            val intent = Intent(Intent.ACTION_SEND).apply {
                                putExtra(Intent.EXTRA_EMAIL, span)
                            }
                            startActivity(Intent.createChooser(intent, getString(R.string.ChooseEmailSend)))
                        }
                    },
                    indexOf(span),
                    indexOf(span) + span.length,
                    SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        descriptionTextView.movementMethod = LinkMovementMethod.getInstance()
    }
}