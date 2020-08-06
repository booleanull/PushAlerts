package com.booleanull.core_ui.base

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.booleanull.core_ui.R

abstract class FullDialogFragment: DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme_Dialog_Full)
    }
}