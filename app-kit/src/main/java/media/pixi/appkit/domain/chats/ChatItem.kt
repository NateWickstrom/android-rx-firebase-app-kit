package media.pixi.appkit.domain.chats

import media.pixi.appkit.data.profile.UserProfile
import org.joda.time.DateTime

data class ChatItem(
    val title: String,
    val subtitle: String,
    val time: DateTime,
    val hasSeen: Boolean,
    val users: List<UserProfile>
)