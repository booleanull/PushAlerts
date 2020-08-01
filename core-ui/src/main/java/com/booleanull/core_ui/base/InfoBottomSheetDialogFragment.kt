package com.booleanull.core_ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.booleanull.core_ui.R
import kotlinx.android.synthetic.main.fragment_info.*

class InfoBottomSheetDialogFragment private constructor(): FullRoundedBottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titleTextView.text = arguments?.getString("title") ?: ""
        descriptionTextView.text = arguments?.getString("description") ?: ""
    }

    companion object {
        fun create(title: String, description: String): InfoBottomSheetDialogFragment {
            return InfoBottomSheetDialogFragment().apply {
                arguments = Bundle().apply {
                    putString("title", title)
                    putString("description", description)
                }
            }
        }
    }
}