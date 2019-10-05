package media.pixi.appkit.ui.myprofile

import android.os.Bundle
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ProfileFragment @Inject constructor(): DaggerFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

}