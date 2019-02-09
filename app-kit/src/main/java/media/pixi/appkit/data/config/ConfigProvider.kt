package media.pixi.appkit.data.config

import io.reactivex.Completable

interface ConfigProvider {
    fun sync(): Completable

    fun getSearchApiKey(): String

    fun getSearchAppId(): String
}
