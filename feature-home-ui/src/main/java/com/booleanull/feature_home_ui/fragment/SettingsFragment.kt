package com.booleanull.feature_home_ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.booleanull.core_ui.fragment.ProblemBottomSheetDialogFragment
import com.booleanull.core_ui.handler.NavigationDeepLinkHandler
import com.booleanull.core_ui.helper.ThemeManager
import com.booleanull.feature_home_ui.R
import com.booleanull.feature_home_ui.viewmodel.SettingsViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : PreferenceFragmentCompat() {

    private val viewModel: SettingsViewModel by viewModel()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findPreference<Preference>("problem")?.setOnPreferenceClickListener {
            ProblemBottomSheetDialogFragment().showNow(childFragmentManager, null)
            true
        }

        findPreference<Preference>("mark")?.setOnPreferenceClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(getString(R.string.play_market_url))
                )
            )
            true
        }

        findPreference<Preference>("clear")?.setOnPreferenceClickListener {
            viewModel.clear()
            true
        }

        findPreference<SwitchPreferenceCompat>("theme")?.isChecked =
            ThemeManager.getCurrentTheme(requireContext()) == ThemeManager.THEME_DARK
        findPreference<SwitchPreferenceCompat>("theme")?.setOnPreferenceChangeListener { preference, newValue ->
            val intent = requireActivity().intent
            intent.putExtra(
                NavigationDeepLinkHandler.DEEP_LINK,
                NavigationDeepLinkHandler.SETTINGS_FRAGMENT
            )
            requireActivity().finish()
            requireActivity().startActivity(intent)
            requireActivity().overridePendingTransition(
                android.R.anim.fade_in,
                android.R.anim.fade_out
            )
            true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.complete.observe(viewLifecycleOwner, Observer {
            Snackbar.make(view, getString(R.string.alarm_clear), Snackbar.LENGTH_SHORT).show()
        })
    }
}