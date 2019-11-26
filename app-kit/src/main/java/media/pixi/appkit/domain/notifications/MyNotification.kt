package media.pixi.appkit.domain.notifications

import media.pixi.appkit.data.notifications.NotificationEntity
import media.pixi.appkit.data.profile.UserProfile

interface MyNotification {
    val id: String
    val imageUrl: String
    val title: String
    val subtitle: String
    val userProfile: UserProfile
    val entity: NotificationEntity
}