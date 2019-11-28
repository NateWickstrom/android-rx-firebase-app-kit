package media.pixi.appkit.data.chats

import com.google.firebase.Timestamp

data class ChatEntity(val id: String,
                      val title: String?,
                      val lastMessageId: String?,
                      val usersHashCode: Int,
                      val timestamp: Timestamp,
                      val userIds: List<String>)