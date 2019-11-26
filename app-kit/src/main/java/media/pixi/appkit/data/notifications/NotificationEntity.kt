package media.pixi.appkit.data.notifications

import com.google.firebase.Timestamp

data class NotificationEntity(val type: NotificationType, val id: String, val userId: String, val timestamp: Timestamp)