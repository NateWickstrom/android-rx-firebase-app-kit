package media.pixi.rx.firebase.auth.kit.example.ui.splash

import android.content.Intent
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import media.pixi.rx.firebase.remote.config.ConfigProvider
import timber.log.Timber
import javax.inject.Inject

class SplashActivity : DaggerAppCompatActivity(), SplashContract.View {

    lateinit var presenter: SplashContract.Presenter
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onLaunch(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.onActivityResult(this, requestCode, resultCode, data)
    }

}
