package media.pixi.appkit.domain.chats;


public enum MessageSendStatus {
    None,
    Created,
    WillUpload,
    Uploading,
    DidUpload,
    WillSend,
    Sending,
    Sent,
    Delivered,
    Failed,
}
