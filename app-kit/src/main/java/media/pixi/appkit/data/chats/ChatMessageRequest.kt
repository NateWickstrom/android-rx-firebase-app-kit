package media.pixi.appkit.data.chats

import com.google.firebase.Timestamp

data class ChatMessageRequest(
    val text: String,
    val senderId: String,
    val timestamp: Timestamp
)