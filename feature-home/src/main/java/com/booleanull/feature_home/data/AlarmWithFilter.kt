package com.booleanull.feature_home.data

import com.booleanull.core.dto.AlarmWithFilterDTO

data class AlarmWithFilter(
    val alarm: Alarm,
    val filters: MutableList<Filter>
)

fun AlarmWithFilterDTO.toAlarmWithFilter() =
    AlarmWithFilter(
        alarm.toAlarm(),
        filters.map { it.toFilter() }.toMutableList()
    )

fun AlarmWithFilter.toAlarmWithFilterDTO() =
    AlarmWithFilterDTO(
        alarm.toAlarmDTO(),
        filters.map { it.toFilterDTO() }
    )