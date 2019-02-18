package media.pixi.appkit.data.profile

data class UserProfile(val id: String,
                       val friendCount: Int,
                       val isFriend: Boolean,
                       val isBlocked: Boolean,
                       val username: String,
                       val firstName: String,
                       val lastName: String,
                       val imageUrl: String)