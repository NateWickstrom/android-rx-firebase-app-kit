package media.pixi.appkit.domain.notifications

import media.pixi.appkit.data.profile.UserProfile

data class FriendRequestNotification(
    override val id: String,
    override val imageUrl: String,
    override val title: String,
    override val subtitle: String,
    override val userProfile: UserProfile
) : Notification