package media.pixi.rx.firebase.auth.kit.example

import com.google.firebase.FirebaseApp
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import media.pixi.rx.firebase.auth.kit.data.AuthProvider
import media.pixi.rx.firebase.auth.kit.example.di.DaggerAppComponent
import timber.log.Timber

import javax.inject.Inject

class App : DaggerApplication() {

    var authProvider: AuthProvider? = null
        @Inject set

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            // TODO Crashlytics.start(this);
            // TODO Timber.plant(new CrashlyticsTree());
        }

        authProvider!!.observerAuthState()
            .filter { result -> result }
            .subscribe(
                { t -> onLogout() },
                { Timber.e(it) }
            )
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

    private fun onLogout() {

    }
}