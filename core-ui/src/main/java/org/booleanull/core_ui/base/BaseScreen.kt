package org.booleanull.core_ui.base

import androidx.fragment.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

abstract class BaseScreen : SupportAppScreen() {

    abstract override fun getFragment(): Fragment
}