package com.samkt.intellisoft.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun getTodaysDate(): String {
    return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now())
}

fun LocalDate.formatDate(): String {
    return this.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
}
