package media.pixi.appkit.domain.chats.models

import media.pixi.appkit.data.profile.UserProfile
import org.joda.time.DateTime

data class ChatListItem(
    val title: String,
    val subtitle: String,
    val time: DateTime,
    val hasSeen: Boolean,
    val users: List<UserProfile>
)