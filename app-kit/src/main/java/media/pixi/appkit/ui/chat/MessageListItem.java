package media.pixi.appkit.ui.chat;

import media.pixi.appkit.domain.chats.Message;

public class MessageListItem {

    public Message message;
    public MessageViewHolderType type;
    public boolean isMe;
    public String senderImageUrl;
    public float progress;

    public MessageListItem(Message message) {
        this.message = message;
    }

    public Message getMessage () {
        return message;
    }

    public MessageViewHolderType geMessageViewHolderType() {
        return type;
    }

    public long getTimeInMillis() {
        return message.getDate().toDate().getTime();
    }


}
