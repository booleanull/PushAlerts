package com.booleanull.mock_factory

import org.booleanull.core.entity.Alarm
import org.booleanull.core.entity.AlarmWithFilter
import org.booleanull.core.entity.Filter

object AlarmTestFactory {

    const val PACKAGE_NAME = "package_name"
    const val FILTER = "filter"

    val EXAMPLE_ALARM = Alarm(
        packageName = PACKAGE_NAME,
        hasAlarm = false,
        hasFilter = false,
        isFavorite = false,
        count = 0
    )
    val EXAMPLE_FILTER = Filter(
        packageName = PACKAGE_NAME,
        filter = FILTER
    )
    val EXAMPLE_ALARM_WITH_FILTER = AlarmWithFilter(
        alarm = EXAMPLE_ALARM,
        filters = setOf(EXAMPLE_FILTER)
    )

    const val PACKAGE_NAME_1 = "package_name_1"
    const val FILTER_1 = "filter_1"

    val EXAMPLE_ALARM_1 = Alarm(
        packageName = PACKAGE_NAME_1,
        hasAlarm = false,
        hasFilter = false,
        isFavorite = false,
        count = 0
    )
    val EXAMPLE_FILTER_1 = Filter(
        packageName = PACKAGE_NAME_1,
        filter = FILTER_1
    )
    val EXAMPLE_ALARM_WITH_FILTER_1 = AlarmWithFilter(
        alarm = EXAMPLE_ALARM_1,
        filters = setOf(EXAMPLE_FILTER_1)
    )

    const val PACKAGE_NAME_2 = "package_name_2"
    const val FILTER_2 = "filter_2"

    val EXAMPLE_ALARM_2 = Alarm(
        packageName = PACKAGE_NAME_2,
        hasAlarm = false,
        hasFilter = false,
        isFavorite = false,
        count = 0
    )
    val EXAMPLE_FILTER_2 = Filter(
        packageName = PACKAGE_NAME_2,
        filter = FILTER_2
    )
    val EXAMPLE_ALARM_WITH_FILTER_2 = AlarmWithFilter(
        alarm = EXAMPLE_ALARM_2,
        filters = setOf(EXAMPLE_FILTER_2)
    )

    fun getAlarms() =
        listOf(EXAMPLE_ALARM_WITH_FILTER, EXAMPLE_ALARM_WITH_FILTER_1, EXAMPLE_ALARM_WITH_FILTER_2)
}