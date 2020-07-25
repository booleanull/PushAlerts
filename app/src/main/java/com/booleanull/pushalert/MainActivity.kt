package com.booleanull.pushalert

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.provider.Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS
import android.text.TextUtils
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.booleanull.core_ui.base.BaseFragment


class MainActivity : AppCompatActivity() {

    private var enableNotificationListenerAlertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, MainFragment())
                    .commit()
        }

        if(!isNotificationServiceEnabled()){
            enableNotificationListenerAlertDialog = buildNotificationServiceAlertDialog();
            enableNotificationListenerAlertDialog?.show();
        }
    }

    override fun onBackPressed() {
        (supportFragmentManager.fragments.firstOrNull() as? BaseFragment)?.onBackPressed()
    }

    private fun isNotificationServiceEnabled(): Boolean {
        val pkgName = packageName
        val flat: String = Settings.Secure.getString(
            contentResolver,
            "enabled_notification_listeners"
        )
        if (!TextUtils.isEmpty(flat)) {
            val names = flat.split(":".toRegex()).toTypedArray()
            for (i in names.indices) {
                val cn = ComponentName.unflattenFromString(names[i])
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.packageName)) {
                        return true
                    }
                }
            }
        }
        return false
    }

    private fun buildNotificationServiceAlertDialog(): AlertDialog? {
        val alertDialogBuilder =
            AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("")
        alertDialogBuilder.setMessage("+")
        alertDialogBuilder.setPositiveButton(
            "yes"
        ) { dialog, id -> startActivity(Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")) }
        alertDialogBuilder.setNegativeButton(
            "no"
        ) { dialog, id ->

        }
        return alertDialogBuilder.create()
    }
}
