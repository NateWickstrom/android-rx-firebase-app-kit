package media.pixi.appkit.domain.chats

import org.joda.time.DateTime

interface Message {
    val id: String
    val message: String
    val date: DateTime
    val senderId: String
    val type: MessageType
    val messageSendStatus: MessageSendStatus
    val messageReadStatus: MessageReadStatus
}