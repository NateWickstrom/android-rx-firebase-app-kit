package media.pixi.appkit.data.notifications

enum class NotificationType(private val type: String) {
    UNKNOWN("unknown"),
    FRIEND_REQUEST("friend_request");

    companion object {
        fun toEnum(type: String): NotificationType {
            return when (type) {
                FRIEND_REQUEST.type -> FRIEND_REQUEST
                else -> UNKNOWN
            }
        }
    }

}