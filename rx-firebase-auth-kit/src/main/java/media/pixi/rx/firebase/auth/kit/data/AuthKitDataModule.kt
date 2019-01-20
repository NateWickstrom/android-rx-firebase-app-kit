package media.pixi.rx.firebase.auth.kit.data


import javax.inject.Singleton

import dagger.Binds
import dagger.Module

@Module
abstract class AuthKitDataModule {

    @Singleton
    @Binds
    internal abstract fun provideAuthProvider(dataSource: FirebaseAuthProvider): AuthProvider

}
