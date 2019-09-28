package media.pixi.appkit.domain.notifications

import io.reactivex.Flowable
import media.pixi.appkit.data.notifications.NotificationProvider
import media.pixi.appkit.data.profile.UserProfile
import media.pixi.appkit.data.profile.UserProfileProvider
import javax.inject.Inject

class GetNotifications @Inject constructor(private val notificationProvider: NotificationProvider,
                                           private val userProfileProvider: UserProfileProvider) {

    fun getNotifications(): Flowable<List<Notification>> {
        return notificationProvider.getNotifications()
            .flatMap { toProfiles(it) }
    }

    private fun toProfiles(userIds: List<String>) : Flowable<List<Notification>> {
        val flowables = userIds.map { userProfileProvider.observerUserProfile(it) }
        return zip(flowables)
    }

    private fun zip(list: List<Flowable<UserProfile>>): Flowable<List<Notification>> {
        return Flowable.zipIterable(list, { toNotifications(it.map { it as UserProfile }) }, true, 1)
    }

    private fun toNotifications(userProfiles: List<UserProfile>): List<Notification> {
        return userProfiles.map { toNotification(it) }
    }

    private fun toNotification(userProfile: UserProfile): Notification {
        return MyNotification(
            imageUrl = userProfile.imageUrl,
            title = "Friend Request",
            subtitle = "${userProfile.firstName} ${userProfile.lastName}")
    }

    data class MyNotification(override val imageUrl: String,
                              override val title: String,
                              override val subtitle: String
    ): Notification
}