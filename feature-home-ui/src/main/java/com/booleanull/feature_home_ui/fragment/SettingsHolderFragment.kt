package com.booleanull.feature_home_ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.booleanull.core_ui.base.BaseFragment
import com.booleanull.feature_home_ui.R
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsHolderFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.title = getString(R.string.settings)
        toolbar.navigationIcon =
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.ic_arrow_back
            )
        toolbar.setNavigationOnClickListener { onBackPressed() }
    }
}