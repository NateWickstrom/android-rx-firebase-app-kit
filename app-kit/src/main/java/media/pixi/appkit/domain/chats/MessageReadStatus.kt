package media.pixi.appkit.domain.chats

enum class MessageReadStatus(val value: Int) {
    HIDE(-1),
    NONE(0),
    DELIVERED(1),
    READ(2);
}
