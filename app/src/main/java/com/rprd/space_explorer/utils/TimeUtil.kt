package com.rprd.space_explorer.utils

interface TimeUtil {
    fun getYesterdayDate(): String
    fun getTodayDate(): String
    fun getPreviousDate(numberDays: Int): String
    fun getPreviousDateFromDate(currentDate: String): String
    fun getNextDateFromDate(currentDate: String): String
    fun compareDates(d1: String, d2: String): Int
    fun isTomorrow(date: String): Boolean
    fun convertToStringDate(date: Long): String
    fun getCurrentDateInLong(): Long
    fun getTomorrowDateInLong(): Long
}