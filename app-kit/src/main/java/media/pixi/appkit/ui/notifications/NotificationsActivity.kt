package media.pixi.appkit.ui.notifications

import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class NotificationsActivity: DaggerAppCompatActivity() {

    lateinit var fragment: NotificationsFragment
        @Inject set
}