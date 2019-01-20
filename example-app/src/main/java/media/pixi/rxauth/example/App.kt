package media.pixi.rxauth.example

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import media.pixi.rx.firebase.auth.kit.data.AuthProvider
import media.pixi.rxauth.example.di.DaggerAppComponent
import timber.log.Timber

import javax.inject.Inject

class App : DaggerApplication() {

    var authProvider: AuthProvider? = null
        @Inject set

    override fun onCreate() {
        super.onCreate()

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
