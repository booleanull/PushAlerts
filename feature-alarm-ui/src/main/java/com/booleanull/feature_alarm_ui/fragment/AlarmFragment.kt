package com.booleanull.feature_alarm_ui.fragment

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
import androidx.lifecycle.Observer
import com.booleanull.core_ui.base.BaseFragment
import com.booleanull.core_ui.handler.NavigationDeepLinkHandler
import com.booleanull.core_ui.setOnLongClickListener
import com.booleanull.feature_alarm_ui.R
import com.booleanull.feature_alarm_ui.viewmodel.AlarmViewModel
import kotlinx.android.synthetic.main.fragment_alarm.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AlarmFragment : BaseFragment() {

    private lateinit var viewModel: AlarmViewModel
    private lateinit var ringtone: Ringtone

    private val vibrationDuration = 200L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_alarm, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = viewModel<AlarmViewModel> {
            parametersOf(
                requireActivity().intent?.getStringExtra(NavigationDeepLinkHandler.PACKAGE_NAME)
                    ?: ""
            )
        }.value

        if (savedInstanceState == null) {
            viewModel.loadApplication()
        }

        var alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        }
        ringtone = RingtoneManager.getRingtone(context, alarmUri)
        ringtone.play()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pulseView.start()

        alarmImageView.setOnLongClickListener(800,
            listenerStart = {
                pulseView.stop()
            },
            listenerEnd = {
                val vibrator =
                    requireActivity().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(
                        VibrationEffect.createOneShot(
                            vibrationDuration,
                            VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    )
                } else {
                    vibrator.vibrate(vibrationDuration)
                }

                ringtone.stop()
                onBack()
            },
            listenerCancel = {
                pulseView.start()
            })

        viewModel.application.observe(viewLifecycleOwner, Observer {
            iconImageView.setImageDrawable(it.icon)
            applicationTextView.text = it.name
        })

        viewModel.errorNotFound.observe(viewLifecycleOwner, Observer {
            onBack()
        })

        viewModel.finish.observe(viewLifecycleOwner, Observer {
            onBack()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        ringtone.stop()
    }

    private fun onBack() {
        if (parentFragmentManager.fragments.size > 1) {
            router.back()
        } else {
            requireActivity().finish()
        }
    }
}