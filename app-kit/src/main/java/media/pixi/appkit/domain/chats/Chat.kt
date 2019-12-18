package media.pixi.appkit.domain.chats

import media.pixi.appkit.data.chats.ChatMessageEntity
import media.pixi.appkit.ui.chat.MessageListItem

data class Chat(val latestMessage: ChatMessageEntity,
                val chatItem: ChatListItem,
                val messages: List<MessageListItem>)