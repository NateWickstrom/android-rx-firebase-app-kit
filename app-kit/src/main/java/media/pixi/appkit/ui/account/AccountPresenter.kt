package media.pixi.appkit.ui.account

import android.app.Activity
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import media.pixi.appkit.data.auth.AuthProvider
import media.pixi.appkit.data.auth.AuthUserModel
import media.pixi.appkit.domain.auth.SignOut
import timber.log.Timber
import java.lang.ref.WeakReference
import javax.inject.Inject

class AccountPresenter @Inject constructor(
    private var authProvider: AuthProvider,
    private var signOut: SignOut,
    private var navigator: AccountContract.Navigator): AccountContract.Presenter {

    private var disposables = CompositeDisposable()
    private var view: AccountContract.View? = null

    private var originalUser: AuthUserModel? = null

    override fun takeView(view: AccountContract.View) {
        this.view = view

        view.loading = true
        disposables.add(authProvider.observerLoggedInUser()
            .subscribe(
                { onResult(it) },
                { onError(it) }
            ))
    }

    override fun dropView() {
        disposables.clear()
    }

    override fun onUserImageClicked(activity: Activity) {
        navigator.showImageFetcher(activity)
    }

    override fun onSignOutClicked(activity: Activity) {
        view?.loading = true

        //disposables.set(
            signOut.signOut()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { onSignOutResult(WeakReference(activity)) },
                    { onError(it) }
                )
        //)
    }

    override fun onSaveClicked(activity: Activity) {
        view?.loading = true

        val completables = mutableListOf<Completable>()

        val firstName = view?.firstName ?: ""
        val lastName = view?.lastName ?: ""
        val username = view?.username ?: ""
        val email = view?.email ?: ""

        if (originalUser?.firstName != firstName && firstName.isNotBlank()) {
            completables.add(authProvider.updateFirstName(firstName))
        }
        if (originalUser?.lastName != lastName && lastName.isNotBlank()) {
            completables.add(authProvider.updateLastName(lastName))
        }
        if (originalUser?.username != username && username.isNotBlank()) {
            completables.add(authProvider.updateUsername(username))
        }
        if (originalUser?.email != email && email.isNotBlank()) {
            // requires password
            //complatables.set(authProvider.updateEmail(firstName))
        }

        if (completables.isNotEmpty()) {
            view?.loading = true
            Completable.concat(completables).subscribe(
                { onUpdateComplete() },
                { onUpdateError(it) }
            )
        } else {
            // nothing to update
        }
    }

    override fun onResetClicked(activity: Activity) {
        originalUser?.let { onResult(it) }
    }

    override fun onUpdatePasswordClicked(activity: Activity) {
        navigator.showUpdatePasswordScreen(activity)
    }

    override fun onVerifyEmailClicked(activity: Activity) {

    }

    private fun onUpdateComplete() {
        view?.loading = false
    }

    private fun onUpdateError(error: Throwable) {
        view?.loading = false
        Timber.e(error.message, error)
    }

    private fun onSignOutResult(activity: WeakReference<Activity>) {
        view?.loading = false
        activity.get()?.let {
            navigator.showSignInScreen(it)
        }
    }

    private fun onResult(user: AuthUserModel) {
        view?.loading = false
        originalUser = user
        view?.email = user.email
        view?.username = user.username
        view?.firstName = user.firstName
        view?.lastName = user.lastName
        view?.userImageUrl = user.imageUrl
    }

    private fun onError(error: Throwable) {
        view?.loading = false
        Timber.e(error.message, error)
    }
}