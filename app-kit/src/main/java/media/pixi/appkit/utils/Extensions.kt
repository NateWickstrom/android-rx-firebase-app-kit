package media.pixi.appkit.utils

import com.google.firebase.firestore.DocumentSnapshot


fun DocumentSnapshot.getString(key: String, defaultValue: String): String {
    return getString(key)?.let { it } ?: defaultValue
}