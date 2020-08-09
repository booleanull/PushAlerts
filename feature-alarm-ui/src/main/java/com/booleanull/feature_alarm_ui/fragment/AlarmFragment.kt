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
import com.booleanull.core_ui.setOnLongClickListener
import com.booleanull.feature_alarm_ui.R
import com.booleanull.feature_alarm_ui.viewmodel.AlarmViewModel
import kotlinx.android.synthetic.main.fragment_alarm.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class AlarmFragment : BaseFragment() {

    private lateinit var viewModel: AlarmViewModel
    private lateinit var ringtone: Ringtone

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
                requireActivity().intent?.getStringExtra("packageName") ?: ""
            )
        }.value

        var alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        }
        ringtone = RingtoneManager.getRingtone(context, alarmUri)

        if (savedInstanceState == null) {
            viewModel.loadApplication()
            ringtone.play()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                            100,
                            VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    )
                } else {
                    vibrator.vibrate(100)
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        ringtone.stop()
    }
}