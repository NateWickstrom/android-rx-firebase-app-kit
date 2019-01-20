package media.pixi.rx.firebase.auth.kit.data

data class AuthUserModel(
    val id: String,
    val email: String,
    val username: String,
    val imageUrl: String,
    val phoneNumber: String,
    val verifiedEmail: Boolean)
