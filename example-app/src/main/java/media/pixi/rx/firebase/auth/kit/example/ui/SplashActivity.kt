package media.pixi.rx.firebase.auth.kit.example.ui

import android.content.Intent
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import media.pixi.rx.firebase.auth.kit.data.AuthProvider
import media.pixi.rx.firebase.auth.kit.ui.account.PasswordForgotActivity
import media.pixi.rx.firebase.auth.kit.ui.signin.SigninActivity
import javax.inject.Inject

class SplashActivity : DaggerAppCompatActivity() {

    var authProvider: AuthProvider? = null
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (authProvider!!.isSignedIn()) {
            onSignedIn()
        } else {
            onSignedOut()
        }
    }

    private fun onSignedOut() {
        val intent = Intent(this, SigninActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
        finish()
    }

    private fun onSignedIn() {
        val intent = Intent(this, PasswordForgotActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
        finish()
    }
}
