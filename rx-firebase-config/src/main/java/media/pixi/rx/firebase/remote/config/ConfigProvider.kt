package media.pixi.rx.firebase.remote.config

import io.reactivex.Completable

interface ConfigProvider {
    fun sync(): Completable

    fun getSearchApiKey(): String

    fun getSearchAppId(): String
}
