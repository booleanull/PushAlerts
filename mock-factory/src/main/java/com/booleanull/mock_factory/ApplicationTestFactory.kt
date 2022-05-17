package com.booleanull.mock_factory

import android.content.pm.PackageInfo
import org.booleanull.core.entity.Application

object ApplicationTestFactory {

    const val APPLICATION_NAME = "PushAlerts"
    val APPLICATION_ICON = null

    val EXAMPLE_PACKAGE_INFO = PackageInfo().apply {
        packageName = AlarmTestFactory.PACKAGE_NAME
    }

    val EXAMPLE_APPLICATION = Application(
        APPLICATION_NAME,
        AlarmTestFactory.PACKAGE_NAME,
        APPLICATION_ICON,
        AlarmTestFactory.EXAMPLE_ALARM.hasAlarm,
        AlarmTestFactory.EXAMPLE_ALARM.isFavorite
    )

    const val APPLICATION_NAME_1 = "PushAlerts_1"
    val APPLICATION_ICON_1 = null

    val EXAMPLE_PACKAGE_INFO_1 = PackageInfo().apply {
        packageName = AlarmTestFactory.PACKAGE_NAME_1
    }

    val EXAMPLE_APPLICATION_1 = Application(
        APPLICATION_NAME_1,
        AlarmTestFactory.PACKAGE_NAME_1,
        APPLICATION_ICON_1,
        AlarmTestFactory.EXAMPLE_ALARM_1.hasAlarm,
        AlarmTestFactory.EXAMPLE_ALARM_1.isFavorite
    )

    const val APPLICATION_NAME_2 = "PushAlerts_2"
    val APPLICATION_ICON_2 = null

    val EXAMPLE_PACKAGE_INFO_2 = PackageInfo().apply {
        packageName = AlarmTestFactory.PACKAGE_NAME_2
    }

    val EXAMPLE_APPLICATION_2 = Application(
        APPLICATION_NAME_2,
        AlarmTestFactory.PACKAGE_NAME_2,
        APPLICATION_ICON_2,
        AlarmTestFactory.EXAMPLE_ALARM_2.hasAlarm,
        AlarmTestFactory.EXAMPLE_ALARM_2.isFavorite
    )

    fun getAlarms() = listOf(EXAMPLE_APPLICATION, EXAMPLE_APPLICATION_1, EXAMPLE_APPLICATION_2)
}