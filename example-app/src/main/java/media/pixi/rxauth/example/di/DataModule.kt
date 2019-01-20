package media.pixi.rxauth.example.di


import javax.inject.Singleton

import dagger.Binds
import dagger.Module
import media.pixi.rx.firebase.auth.kit.data.AuthProvider
import media.pixi.rx.firebase.auth.kit.data.FirebaseAuthProvider

@Module
abstract class DataModule {

    @Singleton
    @Binds
    internal abstract fun provideAuthProvider(dataSource: FirebaseAuthProvider): AuthProvider

}
