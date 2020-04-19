package media.pixi.appkit.data.chats

enum class ChatAttachmentType constructor(internal val id: Long) {
    IMAGE(3),
    VIDEO(1),
    UNKNOWN(0)
}
