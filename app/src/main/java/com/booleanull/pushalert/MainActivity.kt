package com.booleanull.pushalert

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.booleanull.core_ui.base.BaseFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.container, MainFragment())
                    .commit()
        }
    }

    override fun onBackPressed() {
        (supportFragmentManager.fragments.firstOrNull() as? BaseFragment)?.onBackPressed()
    }
}