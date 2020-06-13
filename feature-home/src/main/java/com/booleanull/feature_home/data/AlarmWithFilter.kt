package com.booleanull.feature_home.data

import com.booleanull.core.dto.AlarmWithFilterDTO

data class AlarmWithFilter(
    val alarm: Alarm,
    val filters: MutableSet<Filter>
)

fun AlarmWithFilterDTO.toAlarmWithFilter() =
    AlarmWithFilter(
        alarm.toAlarm(),
        filters.map { it.toFilter() }.toMutableSet()
    )

fun AlarmWithFilter.toAlarmWithFilterDTO() =
    AlarmWithFilterDTO(
        alarm.toAlarmDTO(),
        filters.map { it.toFilterDTO() }.toSet()
    )