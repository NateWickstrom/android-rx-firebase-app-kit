package media.pixi.appkit.domain.users

import media.pixi.appkit.data.profile.UserProfile
import java.util.Comparator

class ComparatorUserProfile: Comparator<UserProfile> {
    override fun compare(u1: UserProfile, u2: UserProfile): Int {
        return u1.firstName.compareTo(u2.firstName)
    }
}