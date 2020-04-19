package media.pixi.appkit.data.chats

enum class ChatMessageType constructor(internal val id: Long) {
    TEXT(0),
    IMAGE(1),
    VIDEO(2),
    UNKNOWN(-1)
}
