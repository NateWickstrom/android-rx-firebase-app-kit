package media.pixi.appkit.data.notifications

enum class NotificationType(val type: String) {
    UNKNOWN("unknown"),
    FRIEND_REQUEST("friend_request"),
    NEW_FRIEND("new_friend");

    companion object {
        fun toEnum(type: String): NotificationType {
            return when (type) {
                FRIEND_REQUEST.type -> FRIEND_REQUEST
                NEW_FRIEND.type -> NEW_FRIEND
                else -> UNKNOWN
            }
        }
    }

}