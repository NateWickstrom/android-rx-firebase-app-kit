package media.pixi.appkit.data.config


import javax.inject.Singleton

import dagger.Binds
import dagger.Module

@Module
abstract class ConfigDataModule {

    @Singleton
    @Binds
    internal abstract fun provideConfigProvider(dataSource: FirebaseConfigDataSource): ConfigProvider

}
