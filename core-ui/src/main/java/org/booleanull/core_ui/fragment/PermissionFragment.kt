package org.booleanull.core_ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.fragment_permission.*
import org.booleanull.core.permission.PermissionCompositeController
import org.booleanull.core.permission.PermissionCompositeControllerImpl
import org.booleanull.core.permission.PermissionStatus
import org.booleanull.core_ui.R
import org.booleanull.core_ui.base.RoundedBottomSheetDialogFragment
import org.booleanull.core_ui.setChecked
import org.koin.android.ext.android.inject

class PermissionFragment : RoundedBottomSheetDialogFragment(128) {

    fun interface OnCheckDisabledListener {
        fun checkDisabled()
    }

    var onCheckDisabledListener: OnCheckDisabledListener? = null

    private val permissionCompositeController: PermissionCompositeController by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_permission, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        autoStartButton.isVisible =
            permissionCompositeController.getPermissionStatus(PermissionCompositeControllerImpl.PERMISSION_CHINE) !is PermissionStatus.PermissionNoneStatus

        alertSwitch.setOnCheckedChangeListener { _, _ ->
            callPermission(PermissionCompositeControllerImpl.PERMISSION_ALERT)
        }
        notificationSwitch.setOnCheckedChangeListener { _, _ ->
            callPermission(PermissionCompositeControllerImpl.PERMISSION_NOTIFICATION)
        }
        autoStartButton.setOnClickListener {
            callPermission(PermissionCompositeControllerImpl.PERMISSION_CHINE)
        }
    }

    override fun onResume() {
        super.onResume()
        updateSwitch(alertSwitch, PermissionCompositeControllerImpl.PERMISSION_ALERT)
        updateSwitch(notificationSwitch, PermissionCompositeControllerImpl.PERMISSION_NOTIFICATION)
    }

    private fun callPermission(permissionId: Int) {
        permissionCompositeController.requestPermission(permissionId).forEach {
            startActivity(it.intent)
        }
    }

    private fun updateSwitch(switch: Switch, permissionId: Int) {
        when (permissionCompositeController.getPermissionStatus(permissionId)) {
            is PermissionStatus.PermissionBadStatus -> {
                switch.setChecked(
                    false
                ) { _, _ ->
                    callPermission(permissionId)
                }
                view?.handler?.post {
                    onCheckDisabledListener?.checkDisabled()
                }
            }
            is PermissionStatus.PermissionOkStatus -> {
                switch.setChecked(
                    true
                ) { _, _ ->
                    callPermission(permissionId)
                }
            }
        }
    }
}