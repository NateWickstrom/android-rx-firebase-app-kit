package media.pixi.appkit.domain.notifications

import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import media.pixi.appkit.data.notifications.NotificationEntity
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

    private fun toProfiles(notes: List<NotificationEntity>) : Flowable<List<Notification>> {
        return zip(notes.map(this::createNote))
    }

    private fun createNote(note: NotificationEntity): Flowable<Notification> {
        return Flowable.zip(
            userProfileProvider.observerUserProfile(note.id),
            Flowable.just(note),
            MyZipper()
        )
    }

    private fun zip(list: List<Flowable<Notification>>): Flowable<List<Notification>> {
        return Flowable.zipIterable(list, { it.map { it as Notification } }, true, 1)
    }

    private class MyZipper: BiFunction<UserProfile, NotificationEntity, Notification> {

        override fun apply(user: UserProfile, entity: NotificationEntity): Notification {
            return toNotification(user, entity)
        }

        private fun toNotification(userProfile: UserProfile, entity: NotificationEntity): Notification {
            return FriendRequestNotification(
                imageUrl = userProfile.imageUrl,
                title = "Friend Request",
                subtitle = "${userProfile.firstName} ${userProfile.lastName}",
                userProfile = userProfile)
        }
    }
}