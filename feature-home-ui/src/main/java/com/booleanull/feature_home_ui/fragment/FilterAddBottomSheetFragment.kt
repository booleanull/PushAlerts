package com.booleanull.feature_home_ui.fragment

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.booleanull.core_ui.RoundedBottomSheetDialogFragment
import com.booleanull.feature_home_ui.R
import kotlinx.android.synthetic.main.fragment_filter_add_bottom_sheet.*

class FilterAddBottomSheetFragment : RoundedBottomSheetDialogFragment() {

    interface Delegate {
        fun onFinished(filter: String)
    }

    var delegate: Delegate? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_filter_add_bottom_sheet, container, false)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.decorView?.findViewById<View>(R.id.touch_outside)
            ?.setOnClickListener(null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filterAddButton.setOnClickListener {
            if (!filterEditText.text.isNullOrBlank()) {
                delegate?.onFinished(filterEditText.text.toString())
                dismiss()
            }
        }
        filterEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_DONE
                || event.action == KeyEvent.ACTION_DOWN
                && event.keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                if (!filterEditText.text.isNullOrBlank()) {
                    delegate?.onFinished(filterEditText.text.toString())
                    dismiss()
                }
                true
            }
            false
        }
    }
}