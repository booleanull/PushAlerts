package com.booleanull.feature_home.data

import com.booleanull.core.dto.FilterDTO

data class Filter(val packageName: String, val filter: String)

fun FilterDTO.toFilter() = Filter(
    packageName,
    filter
)

fun Filter.toFilterDTO() = FilterDTO(
    packageName,
    filter
)

