package media.pixi.appkit.ui.chat

import java.util.*


class MessageItemSorter(private val descending: Boolean) : Comparator<MessageListItem> {

    override fun compare(m1: MessageListItem, m2: MessageListItem): Int {
        return if (descending) {
            if (m1.timeInMillis > m2.timeInMillis) -1 else 1
        } else {
            if (m1.timeInMillis > m2.timeInMillis) 1 else -1
        }

    }
}
