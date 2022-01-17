// Copyright 2022 John Hurst
// John Hurst
// 2022-01-06

package redx.asexml

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME
import java.time.format.DateTimeFormatter.ISO_LOCAL_TIME
import java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME
import java.time.format.DateTimeFormatter.ISO_OFFSET_TIME
import java.time.format.DateTimeParseException

object AseXmlUtils {
    fun localDateTime(s: String): LocalDateTime = try {
        LocalDateTime.parse(s, ISO_OFFSET_DATE_TIME)
    } catch (ex: DateTimeParseException) {
        LocalDateTime.parse(s, ISO_LOCAL_DATE_TIME)
    }

    fun localDate(s: String): LocalDate = LocalDate.parse(s, ISO_LOCAL_DATE)

    fun localTime(s: String): LocalTime = try {
        LocalTime.parse(s, ISO_OFFSET_TIME)
    } catch (ex: DateTimeParseException) {
        LocalTime.parse(s, ISO_LOCAL_TIME)
    }

}
