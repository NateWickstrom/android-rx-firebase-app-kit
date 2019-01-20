package media.pixi.rx.firebase.auth.kit.ui.account

import dagger.Module
import dagger.android.ContributesAndroidInjector
import media.pixi.rx.firebase.auth.kit.di.AuthKitFragmentScoped

@Module
abstract class AccountModule {

    @AuthKitFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun accountActivity(): AccountActivity

}
