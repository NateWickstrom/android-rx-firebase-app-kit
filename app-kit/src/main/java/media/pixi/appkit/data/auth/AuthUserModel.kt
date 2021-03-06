package media.pixi.appkit.data.auth

data class AuthUserModel(
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val username: String,
    val imageUrl: String,
    val phoneNumber: String,
    val verifiedEmail: Boolean)
