package media.pixi.appkit.domain.chats.models

enum class MessageAttachmentType constructor(internal val id: Int) {
    IMAGE(100),
    VIDEO(101)
}
