package media.pixi.appkit.ui.develop

import io.reactivex.disposables.CompositeDisposable
import media.pixi.appkit.data.auth.AuthProvider
import media.pixi.appkit.data.devices.DevicesProvider
import javax.inject.Inject

class DevelopPresenter @Inject constructor(
    private var authProvider: AuthProvider,
    private var devicesProvider: DevicesProvider,
    private var navigator: DevelopContract.Navigator): DevelopContract.Presenter {

    private var disposables = CompositeDisposable()
    private var view: DevelopContract.View? = null


    override fun takeView(view: DevelopContract.View) {
        this.view = view

        devicesProvider.deviceId?.let { view.deviceId = it }
        authProvider.getUserId()?.let { view.userId = it }
    }

    override fun dropView() {
        disposables.clear()
    }

}