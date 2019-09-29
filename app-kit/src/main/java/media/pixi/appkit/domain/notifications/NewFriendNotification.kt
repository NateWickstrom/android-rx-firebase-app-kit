package media.pixi.appkit.domain.notifications

import media.pixi.appkit.data.profile.UserProfile

data class NewFriendNotification(
    override val imageUrl: String,
    override val title: String,
    override val subtitle: String,
    val userProfile: UserProfile
) : Notification