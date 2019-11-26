package media.pixi.appkit.domain.notifications

import io.reactivex.Flowable
import io.reactivex.functions.BiFunction
import media.pixi.appkit.data.notifications.NotificationEntity
import media.pixi.appkit.data.notifications.NotificationProvider
import media.pixi.appkit.data.notifications.NotificationType
import media.pixi.appkit.data.profile.UserProfile
import media.pixi.appkit.data.profile.UserProfileProvider
import javax.inject.Inject

class GetNotifications @Inject constructor(private val notificationProvider: NotificationProvider,
                                           private val userProfileProvider: UserProfileProvider) {

    fun getNotifications(): Flowable<List<MyNotification>> {
        return notificationProvider.getNotifications()
            .flatMap { toProfiles(it) }
    }

    fun getNotification(notificationId: String): Flowable<MyNotification> {
        return notificationProvider.getNotification(notificationId)
            .toFlowable()
            .flatMap { createNote(it) }
    }

    private fun toProfiles(notes: List<NotificationEntity>) : Flowable<List<MyNotification>> {
        return zip(notes.map(this::createNote))
    }

    private fun createNote(note: NotificationEntity): Flowable<MyNotification> {
        return Flowable.zip(
            userProfileProvider.observerUserProfile(note.userId),
            Flowable.just(note),
            MyZipper()
        )
    }

    private fun zip(list: List<Flowable<MyNotification>>): Flowable<List<MyNotification>> {
        return Flowable.zipIterable(list, { it.map { it as MyNotification } }, true, 1)
    }

    private fun toNotification(userProfile: UserProfile, entity: NotificationEntity): MyNotification {
        return when (entity.type) {
            (NotificationType.FRIEND_REQUEST) ->
                FriendRequestNotification(
                id = entity.id,
                imageUrl = userProfile.imageUrl,
                title = "Friend Request",
                subtitle = "${userProfile.firstName} ${userProfile.lastName}",
                userProfile = userProfile,
                entity = entity)
            (NotificationType.NEW_FRIEND) ->
                NewFriendNotification(
                    id = entity.id,
                    imageUrl = userProfile.imageUrl,
                    title = "New Friend",
                    subtitle = "${userProfile.firstName} ${userProfile.lastName}",
                    userProfile = userProfile,
                    entity = entity)

            else -> throw IllegalArgumentException("unknown NotificationEntity type: $entity")
        }
    }

    private inner class MyZipper: BiFunction<UserProfile, NotificationEntity, MyNotification> {

        override fun apply(user: UserProfile, entity: NotificationEntity): MyNotification {
            return toNotification(user, entity)
        }
    }
}