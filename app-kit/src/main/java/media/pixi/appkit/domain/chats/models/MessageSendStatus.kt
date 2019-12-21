package media.pixi.appkit.domain.chats.models


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
