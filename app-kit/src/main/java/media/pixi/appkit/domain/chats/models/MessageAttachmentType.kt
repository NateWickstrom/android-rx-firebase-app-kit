package media.pixi.appkit.domain.chats.models

enum class MessageAttachmentType constructor(internal val id: Int) {
    MY_TEXT(100),
    MY_IMAGE(101),
    MY_LOCATION(102),
    THEIR_TEXT(200),
    THEIR_IMAGE(201),
    THEIR_LOCATION(202)
}
