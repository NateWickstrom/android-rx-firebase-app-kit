package media.pixi.appkit.domain.chats

import org.joda.time.DateTime

data class ChatItem(
    val title: String,
    val subtitle: String,
    val time: DateTime,
    val hasSeen: Boolean,
    val profileImageUrls: List<String>
)