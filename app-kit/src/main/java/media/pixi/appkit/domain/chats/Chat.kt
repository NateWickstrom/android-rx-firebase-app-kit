package media.pixi.appkit.domain.chats

import media.pixi.appkit.data.chats.ChatMessageEntity
import media.pixi.appkit.data.profile.UserProfile
import media.pixi.appkit.ui.chat.MessageListItem

data class Chat(val latestMessage: ChatMessageEntity,
                val title: String,
                val users: List<UserProfile>,
                val messages: List<MessageListItem>)