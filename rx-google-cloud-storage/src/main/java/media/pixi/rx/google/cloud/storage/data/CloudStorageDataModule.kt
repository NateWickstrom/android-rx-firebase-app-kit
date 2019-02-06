package media.pixi.rx.google.cloud.storage.data

import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class CloudStorageDataModule {

    @Singleton
    @Binds
    internal abstract fun provideCloudStorageRepo(dataSource: GoogleCloudStorageDataSource): CloudStorageRepo

}