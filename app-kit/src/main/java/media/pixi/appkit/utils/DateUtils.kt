package media.pixi.appkit.utils

import org.joda.time.DateTime
import java.util.*
import org.joda.time.format.DateTimeFormat


object DateUtils {

    fun isToday(date: Date): Boolean {
        val c1 = Calendar.getInstance() // today

        val c2 = Calendar.getInstance()
        c2.time = date

        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)
    }

    fun isYesterday(date: Date): Boolean {
        val c1 = Calendar.getInstance() // today
        c1.add(Calendar.DAY_OF_YEAR, -1) // yesterday

        val c2 = Calendar.getInstance()
        c2.time = date

        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)
    }

    fun toPrettyString(date: Date): String {
        if (isToday(date)) return toTimeString(date)
        if (isYesterday(date)) return "Yesterday"
        return toDateString(date)
    }

    fun toTimeString(date: Date): String {
        val calendar = Calendar.getInstance() // today
        calendar.time = date

        return "${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}"
    }

    fun toDateString(date: Date): String {
        val dateTime = DateTime(date)
        val format = DateTimeFormat.mediumDate()
        return format.print(dateTime)
    }
}