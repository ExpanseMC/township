package com.expansemc.township.plugin.util

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object DateFormatters {

    private val primaryFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm a")

    fun format(instant: Instant): String =
        LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).format(primaryFormat)
}