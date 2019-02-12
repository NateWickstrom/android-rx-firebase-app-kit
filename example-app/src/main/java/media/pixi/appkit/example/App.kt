package media.pixi.appkit.example

import com.facebook.drawee.backends.pipeline.Fresco
import com.google.firebase.FirebaseApp
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import media.pixi.appkit.data.auth.AuthProvider
import media.pixi.appkit.example.di.DaggerAppComponent
import timber.log.Timber

import javax.inject.Inject

class App : DaggerApplication() {

    lateinit var authProvider: AuthProvider
        @Inject set

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        Fresco.initialize(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            // TODO Crashlytics.start(this);
            // TODO Timber.plant(new CrashlyticsTree());
        }

        authProvider.observerAuthState()
            .filter { result -> result }
            .subscribe(
                { onAuthStateChange(it) },
                { Timber.e(it.message, it) }
            )
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }

    private fun onAuthStateChange(isLoggedIn: Boolean) {
        //Toast.makeText(this, "Logged in: $isLoggedIn", Toast.LENGTH_SHORT).show()

        if (!isLoggedIn) {
            //ProcessPhoenix.triggerRebirth(this)
        }
    }
}