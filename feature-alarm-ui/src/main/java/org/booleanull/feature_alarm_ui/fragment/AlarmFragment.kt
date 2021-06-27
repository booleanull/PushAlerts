package org.booleanull.feature_alarm_ui.fragment

import android.app.KeyguardManager
import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_alarm.*
import org.booleanull.core_ui.base.BaseFragment
import org.booleanull.core_ui.handler.NavigationDeepLinkHandler
import org.booleanull.core_ui.setOnLongClickListener
import org.booleanull.feature_alarm_ui.R
import org.booleanull.feature_alarm_ui.viewmodel.AlarmViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

internal class AlarmFragment : BaseFragment() {

    private var _viewModel: AlarmViewModel? = null
    private val viewModel: AlarmViewModel
        get() {
            return checkNotNull(_viewModel)
        }

    private val ringtone: Ringtone by lazy {
        var alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        }
        RingtoneManager.getRingtone(context, alarmUri)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_alarm, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showWhenLock()

        _viewModel = viewModel<AlarmViewModel> {
            parametersOf(
                requireActivity().intent?.getStringExtra(NavigationDeepLinkHandler.PACKAGE_NAME)
                    ?: ""
            )
        }.value

        if (savedInstanceState == null) {
            viewModel.loadApplication()
        }
        ringtone.play()
    }

    override fun onDestroy() {
        super.onDestroy()
        hideWhenLock()
        ringtone.stop()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val vibrator = requireActivity().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        pulseView.start()

        alarmImageView.setOnLongClickListener(
            800,
            listenerStart = {
                pulseView.stop()
            },
            listenerEnd = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(
                        VibrationEffect.createOneShot(
                            VIBRATION_DURATION,
                            VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    )
                } else {
                    vibrator.vibrate(VIBRATION_DURATION)
                }

                ringtone.stop()
                router.back()
            },
            listenerCancel = {
                pulseView.start()
            })

        viewModel.application.observe(viewLifecycleOwner, Observer {
            iconImageView.setImageDrawable(it.icon)
            applicationTextView.text = it.name
        })

        viewModel.errorNotFound.observe(viewLifecycleOwner, Observer {
            router.back()
        })

        viewModel.finish.observe(viewLifecycleOwner, Observer {
            router.back()
        })
    }

    private fun showWhenLock() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            requireActivity().setShowWhenLocked(true)
            requireActivity().setTurnScreenOn(true)
            val keyguardManager =
                requireActivity().getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
            keyguardManager.requestDismissKeyguard(requireActivity(), null)
        } else {
            requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
            requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD)
            requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
        }
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON)
    }

    private fun hideWhenLock() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            requireActivity().setShowWhenLocked(false)
            requireActivity().setTurnScreenOn(false)
        } else {
            requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
            requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD)
            requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)
        }
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON)
    }

    companion object {
        private const val VIBRATION_DURATION = 200L
    }
}