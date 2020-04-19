package media.pixi.appkit.domain.chats.models

import org.joda.time.DateTime


data class VideoMessage(
    override val id: String,
    override val message: String,
    override val date: DateTime,
    override val senderId: String,
    override val type: MessageType = MessageType.VIDEO,
    override val messageSendStatus: MessageSendStatus,
    override val messageReadStatus: MessageReadStatus,
    val thumbnailUrl: String,
    val fileUrl: String
) : Message