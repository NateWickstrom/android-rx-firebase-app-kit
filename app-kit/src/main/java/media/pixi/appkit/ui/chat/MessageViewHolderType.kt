package media.pixi.appkit.ui.chat

enum class MessageViewHolderType constructor(internal val id: Int) {
    MY_TEXT(100),
    MY_IMAGE(101),
    MY_LOCATION(102),
    THEIR_TEXT(200),
    THEIR_IMAGE(201),
    THEIR_LOCATION(202)
}
