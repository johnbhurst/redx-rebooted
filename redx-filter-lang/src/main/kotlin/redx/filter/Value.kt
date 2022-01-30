// Copyright 2022 John Hurst
// John Hurst
// 2022-01-22

package redx.filter

import java.time.LocalDate
import java.time.LocalDateTime

interface Value {
    data class StringValue(val value: String) : Value
    data class IntegerValue(val value: Int) : Value
    data class DateValue(val value: LocalDate) : Value
    data class DateTimeValue(val value: LocalDateTime) : Value
}
