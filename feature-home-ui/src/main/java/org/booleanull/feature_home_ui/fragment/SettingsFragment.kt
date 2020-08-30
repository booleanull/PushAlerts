package org.booleanull.feature_home_ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.google.android.material.snackbar.Snackbar
import org.booleanull.core.facade.SettingsFacade
import org.booleanull.core.facade.ThemeFacade
import org.booleanull.core_ui.base.Router
import org.booleanull.core_ui.fragment.PermissionFragment
import org.booleanull.core_ui.fragment.ProblemBottomSheetDialogFragment
import org.booleanull.core_ui.handler.NavigationDeepLinkHandler
import org.booleanull.feature_home_ui.R
import org.booleanull.feature_home_ui.viewmodel.SettingsViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment : PreferenceFragmentCompat() {

    private val viewModel: SettingsViewModel by viewModel()

    private val router: Router by inject()

    private val themeFacade: ThemeFacade by inject()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findPreference<Preference>(SettingsFacade.PREFERENCE_PROBLEM)?.setOnPreferenceClickListener {
            ProblemBottomSheetDialogFragment().showNow(childFragmentManager, null)
            true
        }

        findPreference<Preference>(SettingsFacade.PREFERENCE_MARK)?.setOnPreferenceClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(getString(R.string.play_market_url))
                )
            )
            true
        }

        findPreference<Preference>(SettingsFacade.PREFERENCE_POLICY)?.setOnPreferenceClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(getString(R.string.privacy_policy_url))
                )
            )
            true
        }

        findPreference<Preference>(SettingsFacade.PREFERENCE_PERMISSIONS)?.setOnPreferenceClickListener {
            PermissionFragment().apply {
                onCheckDisabledListener = {
                    this@apply.dismiss()
                    router.navigateTo(router.resolve(NavigationDeepLinkHandler.ONBOARDING_FRAGMENT))
                }
            }.also {
                it.showNow(childFragmentManager, null)
            }
            true
        }

        findPreference<Preference>(SettingsFacade.PREFERENCE_CLEAR)?.setOnPreferenceClickListener {
            viewModel.clear()
            true
        }

        findPreference<SwitchPreferenceCompat>(SettingsFacade.PREFERENCE_SWITCH_THEME)?.isChecked =
            themeFacade.getCurrentTheme() == ThemeFacade.THEME_DARK
        findPreference<SwitchPreferenceCompat>(SettingsFacade.PREFERENCE_SWITCH_THEME)?.setOnPreferenceChangeListener { _, _ ->
            val intent = requireActivity().intent
            intent.flags = intent.flags and Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY.inv()
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