package media.pixi.appkit.data.chats

import com.google.firebase.Timestamp

data class ChatMessageEntity(
    val id: String,
    val chatId: String,
    val text: String,
    val timestamp: Timestamp,
    val senderId: String)