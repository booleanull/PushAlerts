package com.booleanull.feature_home_ui.fragment

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.booleanull.feature_home_ui.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}