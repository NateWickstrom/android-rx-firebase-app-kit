package media.pixi.appkit.domain.chats.models

import org.joda.time.DateTime


data class TextMessage(
    override val id: String,
    override val message: String,
    override val date: DateTime,
    override val senderId: String,
    override val type: MessageType = MessageType.TEXT,
    override val messageSendStatus: MessageSendStatus,
    override val messageReadStatus: MessageReadStatus
) : Message