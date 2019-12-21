package media.pixi.appkit.domain.chats.models

import media.pixi.appkit.data.chats.ChatMessageEntity
import media.pixi.appkit.data.profile.UserProfile

data class Chat(val latestMessage: ChatMessageEntity,
                val title: String,
                val users: List<UserProfile>,
                val messages: List<MessageListItem>)