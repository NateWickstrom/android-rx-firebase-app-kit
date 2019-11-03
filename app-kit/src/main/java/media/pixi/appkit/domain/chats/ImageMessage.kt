package media.pixi.appkit.domain.chats

import org.joda.time.DateTime


data class ImageMessage(
    override val id: String,
    override val message: String,
    override val date: DateTime,
    override val senderId: String,
    override val type: MessageType = MessageType.IMAGE,
    override val messageSendStatus: MessageSendStatus,
    override val messageReadStatus: MessageReadStatus
) : Message