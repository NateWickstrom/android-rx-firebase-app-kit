package media.pixi.rx.firebase.auth.kit.example.ui.splash

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import media.pixi.rx.firebase.auth.kit.ui.AUTH_RESPONSE
import media.pixi.rx.firebase.auth.kit.ui.AuthErrorCodes
import media.pixi.rx.firebase.auth.kit.ui.AuthResponse
import media.pixi.rx.firebase.auth.kit.data.AuthProvider
import media.pixi.rx.firebase.auth.kit.example.ui.home.HomeActivity
import media.pixi.rx.firebase.auth.kit.ui.signin.SignInActivity
import media.pixi.rx.firebase.profile.kit.ui.profile.ProfileActivity
import javax.inject.Inject

class SplashActivity : DaggerAppCompatActivity() {

    lateinit var authProvider: AuthProvider
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (authProvider.isSignedIn())
            onSignInComplete()
        else
            launchSignIn()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                onSignInComplete()
            } else {
                val response =
                    fromResultIntent(data)

                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    finish()
                    return
                }

                if (response.errorCode == AuthErrorCodes.NO_NETWORK) {
                    // show no network frag
                    return
                }

                // show unknown sign in auth__error
            }
        }
    }

    private fun onSignInComplete() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
        finish()
    }

    private fun launchSignIn() {
        val intent = Intent(this, SignInActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivityForResult(intent,
            REQUEST_CODE
        )
    }

    companion object {
        const val REQUEST_CODE = 100

        fun fromResultIntent(resultIntent: Intent?): AuthResponse? {
            return resultIntent?.getParcelableExtra(AUTH_RESPONSE)
        }
    }
}
