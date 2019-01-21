package media.pixi.rx.firebase.auth.kit.example.ui

import android.content.Intent
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import media.pixi.rx.firebase.auth.kit.data.AuthProvider
import media.pixi.rx.firebase.auth.kit.ui.account.AccountActivity
import media.pixi.rx.firebase.auth.kit.ui.signin.SigninActivity
import javax.inject.Inject

class SplashActivity : DaggerAppCompatActivity() {

    lateinit var authProvider: AuthProvider
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //if (authProvider.isSignedIn())
            launch(AccountActivity::class.java)
        //else
        //    launch(SigninActivity::class.java)
    }

    private fun launch(clazz: Class<*>) {
        val intent = Intent(this, clazz)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
        finish()
    }
}
