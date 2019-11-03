package media.pixi.appkit.example.di.feature

import dagger.Binds
import dagger.Module
import media.pixi.appkit.example.di.ActivityScoped
import media.pixi.appkit.ui.imageviewer.ImageViewerContract
import media.pixi.appkit.ui.imageviewer.ImageViewerNavigator
import media.pixi.appkit.ui.imageviewer.ImageViewerPresenter

@Module
abstract class ImageViewerModule {

    @ActivityScoped
    @Binds
    internal abstract fun imageViewerPresenter(presenter: ImageViewerPresenter): ImageViewerContract.Presenter

    @ActivityScoped
    @Binds
    internal abstract fun imageViewerNavigator(navigator: ImageViewerNavigator): ImageViewerContract.Navigator

}
