package media.pixi.rx.firebase.remote.config

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import durdinapps.rxfirebase2.RxFirebaseRemote
import io.reactivex.Completable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton class FirebaseConfigDataSource @Inject constructor() : ConfigProvider {

    private val firebaseRemoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

    init {
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setDeveloperModeEnabled(BuildConfig.DEBUG)
            .build()
        firebaseRemoteConfig.setConfigSettings(configSettings)
    }

    override fun sync(): Completable {
        return RxFirebaseRemote.fetch(firebaseRemoteConfig)
            .andThen(Completable.create {
                firebaseRemoteConfig.activateFetched()
            })
    }

    override fun getSearchApiKey(): String {
        return firebaseRemoteConfig.getString(ALGOLIA_SEARCH_KEY)
    }

    override fun getSearchAppId(): String {
        return firebaseRemoteConfig.getString(ALGOLIA_APP_ID)
    }

    companion object {
        private const val ALGOLIA_SEARCH_KEY = "ALGOLIA_SEARCH_KEY"
        private const val ALGOLIA_APP_ID = "ALGOLIA_APP_ID"
    }
}