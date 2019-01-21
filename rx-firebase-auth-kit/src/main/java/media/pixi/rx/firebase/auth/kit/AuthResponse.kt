package media.pixi.rx.firebase.auth.kit

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

const val AUTH_RESPONSE = "auth_response"

@Parcelize
data class AuthResponse(val errorCode: Int): Parcelable