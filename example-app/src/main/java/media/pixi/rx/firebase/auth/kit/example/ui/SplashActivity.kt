package media.pixi.rx.firebase.auth.kit.example.ui

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import media.pixi.rx.firebase.auth.kit.data.AuthProvider
import media.pixi.rx.firebase.auth.kit.example.R
import javax.inject.Inject

class SplashActivity : DaggerAppCompatActivity() {

    var authProvider: AuthProvider? = null
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
