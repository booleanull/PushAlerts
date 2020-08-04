package com.booleanull.core_ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import com.booleanull.core_ui.R
import com.booleanull.core_ui.base.FullRoundedBottomSheetDialogFragment
import com.booleanull.core_ui.setSpannableClick
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
        descriptionTextView.setSpannableClick(
            getString(R.string.problem_service_description),
            getString(R.string.email)
        ) {
            startActivity(
                ShareCompat.IntentBuilder.from(requireActivity())
                    .setType("text/html")
                    .addEmailTo(getString(R.string.email))
                    .setSubject(getString(R.string.app_name))
                    .setChooserTitle(getString(R.string.choose_email_send))
                    .createChooserIntent()
            )
        }
    }
}