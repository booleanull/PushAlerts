package com.booleanull.feature_home.data

import com.booleanull.core.dto.AlarmDTO

data class Alarm(val packageName: String, var isAlarm: Boolean, var isFilter: Boolean)

fun AlarmDTO.toAlarm() = Alarm(
    packageName,
    isAlarm,
    isFilter
)

fun Alarm.toAlarmDTO() = AlarmDTO(
    packageName,
    isAlarm,
    isFilter
)