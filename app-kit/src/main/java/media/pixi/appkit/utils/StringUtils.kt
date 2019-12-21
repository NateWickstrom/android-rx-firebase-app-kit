package media.pixi.appkit.utils

import java.lang.StringBuilder

object StringUtils {

    fun toChatTitle(names: List<Pair<String, String>>): String {
        val sb = StringBuilder()

        if (names.size == 1) {
            sb.append(names[0].first)
            sb.append(" ")
            sb.append(names[0].second)
            return sb.toString()
        }

        names
            .forEach { sb.append(it.first + ", ") }

        return sb.toString().removeSuffix(", ")
    }
}