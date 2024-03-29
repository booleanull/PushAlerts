package org.booleanull.core_ui.base

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.booleanull.core_ui.dp

abstract class RoundedBottomSheetDialogFragment(private val margin: Int) :
    BottomSheetDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BottomSheetDialog(requireContext()).apply {
            setOnShowListener {
                val container =
                    (findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout).apply {
                        layoutParams = (layoutParams as CoordinatorLayout.LayoutParams).apply {
                            setMargins(0, dp(margin), 0, 0)
                        }
                    }
                BottomSheetBehavior.from(container).apply {
                    state = BottomSheetBehavior.STATE_EXPANDED
                    peekHeight = 0
                    addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                        override fun onSlide(bottomSheet: View, slideOffset: Float) = Unit

                        override fun onStateChanged(bottomSheet: View, newState: Int) {
                            if (state == BottomSheetBehavior.STATE_COLLAPSED) {
                                state = BottomSheetBehavior.STATE_HIDDEN
                            }
                        }
                    })
                }
            }
        }
    }
}