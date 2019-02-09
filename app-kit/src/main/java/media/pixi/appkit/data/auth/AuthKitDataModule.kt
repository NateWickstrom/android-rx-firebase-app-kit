package media.pixi.appkit.data.auth

import javax.inject.Singleton

import dagger.Binds
import dagger.Module

@Module
abstract class AuthKitDataModule {

    @Singleton
    @Binds
    internal abstract fun provideAuthProvider(dataSource: FirebaseAuthProvider): AuthProvider

}
