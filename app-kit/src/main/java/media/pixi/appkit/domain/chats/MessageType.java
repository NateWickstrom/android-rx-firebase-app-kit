package media.pixi.appkit.domain.chats;

public enum MessageType {
    NONE(-1),
    TEXT(0),
    LOCATION(1),
    IMAGE(2),
    AUDIO(3),
    VIDEO(4),
    STICKER(5),
    FILE(6),
    CONTACT(7),
    SYSTEM(8),
    MAX(1000000);

    public final int value;

    MessageType(int value) {
        this.value = value;
    }
}
