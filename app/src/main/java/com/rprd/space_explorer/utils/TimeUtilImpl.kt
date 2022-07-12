package com.rprd.space_explorer.utils

import java.text.SimpleDateFormat
import java.util.*

class TimeUtilImpl : TimeUtil {

    private val dateFormat = SimpleDateFormat(TIME_FORMAT)
    private val calendar = Calendar.getInstance()

    override fun getYesterdayDate(): String {
        calendar.add(Calendar.DATE, -1)
        return dateFormat.format(calendar.time)
    }

    override fun getTodayDate(): String {
        return dateFormat.format(calendar.time)
    }

    override fun getPreviousDate(numberDays: Int): String {
        calendar.add(Calendar.DATE, -numberDays)
        return dateFormat.format(calendar.time)
    }

    override fun getPreviousDateFromDate(currentDate: String): String {
        val date = dateFormat.parse(currentDate)
        calendar.time = date
        calendar.add(Calendar.DATE, -1)
        return dateFormat.format(calendar.time)
    }

    override fun getNextDateFromDate(currentDate: String): String {
        val date = dateFormat.parse(currentDate)
        calendar.time = date
        calendar.add(Calendar.DATE, +1)
        return dateFormat.format(calendar.time)
    }

    override fun compareDates(d1: String, d2: String): Int {
        val date1 = dateFormat.parse(d1).time
        val date2 = dateFormat.parse(d2).time
        return when {
            date1 > date2 -> 1
            date1 < date2 -> -1
            else -> 0
        }
    }

    override fun isTomorrow(date: String): Boolean {
        val date1 = dateFormat.parse(date).time
        val today = dateFormat.parse(getTodayDate()).time
        return when {
            date1 > today -> true
            else -> false
        }
    }

    override fun convertToStringDate(date: Long): String {
        return SimpleDateFormat(TIME_FORMAT, Locale.getDefault()).format(date)
    }

    override fun getCurrentDateInLong(): Long {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.getDefault())
        return calendar.timeInMillis
    }

    override fun getTomorrowDateInLong(): Long {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.getDefault())
        calendar.add(Calendar.DATE, +1)
        return calendar.timeInMillis
    }

    companion object {
        private const val TIME_FORMAT = "yyyy-MM-dd"
    }
}