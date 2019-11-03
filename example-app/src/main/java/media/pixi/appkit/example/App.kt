package media.pixi.appkit.example

import com.facebook.drawee.backends.pipeline.Fresco
import com.github.piasy.biv.BigImageViewer
import com.github.piasy.biv.loader.glide.GlideImageLoader
import com.google.firebase.FirebaseApp
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import media.pixi.appkit.AppKitInjector
import media.pixi.appkit.data.auth.AuthProvider
import media.pixi.appkit.data.devices.DevicesProvider
import media.pixi.appkit.example.di.AppComponent
import media.pixi.appkit.example.di.DaggerAppComponent
import media.pixi.appkit.service.AppKitNotificationService
import media.pixi.appkit.utils.AppForegroundListener
import timber.log.Timber

import javax.inject.Inject

class App : DaggerApplication(), AppKitInjector {

    lateinit var authProvider: AuthProvider
        @Inject set
    lateinit var devicesProvider: DevicesProvider
        @Inject set

    private lateinit var appForegroundListener: AppForegroundListener

    private var component: AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        Fresco.initialize(this)
        BigImageViewer.initialize(GlideImageLoader.with(this))

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            // TODO Crashlytics.start(this);
            // TODO Timber.plant(new CrashlyticsTree());
        }

        appForegroundListener = AppForegroundListener()
        registerActivityLifecycleCallbacks(appForegroundListener)

        authProvider.observerAuthState()
            .subscribe(
                { onAuthStateChange(it) },
                { Timber.e(it.message, it) }
            )
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return getAppComponent()
    }

    override fun getSystemService(name: String): Any {
        return when (name) {
            AppKitInjector.INJECTOR -> this
            else -> super.getSystemService(name)
        }
    }

    override fun inject(service: AppKitNotificationService) {
        getAppComponent().inject(service)
    }

    private fun getAppComponent(): AppComponent {
        if (component == null)
            component = DaggerAppComponent.builder().application(this).build()
        return component!!
    }

    private fun onAuthStateChange(isLoggedIn: Boolean) {
        //Toast.makeText(this, "Logged in: $isLoggedIn", Toast.LENGTH_SHORT).show()

        if (isLoggedIn) {
            // associate device
            // devicesProvider.registerDevice()
        } else {
            //ProcessPhoenix.triggerRebirth(this)
        }
    }
}
