package media.pixi.appkit.domain.chats


enum class MessageSendStatus {
    None,
    Created,
    WillUpload,
    Uploading,
    DidUpload,
    WillSend,
    Sending,
    Sent,
    Delivered,
    Failed
}
