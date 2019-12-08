package media.pixi.appkit.service.messages

enum class RemoteMessageType(val type: String) {
    UNKNOWN("unknown"),
    NEW_MESSAGE("new_message"),
    FRIEND_REQUEST("friend_request"),
    NEW_FRIEND("new_friend");

    companion object {
        fun toEnum(type: String?): RemoteMessageType {
            return when (type) {
                NEW_MESSAGE.type -> NEW_MESSAGE
                FRIEND_REQUEST.type -> FRIEND_REQUEST
                NEW_FRIEND.type -> NEW_FRIEND
                else -> UNKNOWN
            }
        }
    }

}