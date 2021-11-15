package com.example.util

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.util.*

object Utility {

    fun formatTimestamp(timestamp: Long): String{
        val dateFormatter = SimpleDateFormat("yyy-MM-dd")
        return dateFormatter.format(timestamp)
    }

    fun getStartOfTheDay(timestamp : Long) : Long {
        val date = Calendar.getInstance().apply {
            time = Date(timestamp)
        }

        val year = date.get(Calendar.YEAR)
        val month = date.get(Calendar.MONTH)
        val dayOfMonth = date.get(Calendar.DAY_OF_MONTH)

        val startOfDat = LocalDate.of(year, month, dayOfMonth).atTime(LocalTime.MAX)
        return startOfDat.toEpochSecond(ZoneOffset.UTC)
    }

    fun getEndOfTheDay(timestamp : Long) : Long {
        val date = Calendar.getInstance().apply {
            time = Date(timestamp)
        }

        val year = date.get(Calendar.YEAR)
        val month = date.get(Calendar.MONTH)
        val dayOfMonth = date.get(Calendar.DAY_OF_MONTH)

        val startOfDat = LocalDate.of(year, month, dayOfMonth).atTime(LocalTime.MIN)
        return startOfDat.toEpochSecond(ZoneOffset.UTC)
    }

}