package media.pixi.rx.firebase.auth.kit.ui.account

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.auth__appbar.*
import media.pixi.common.ActivityUtils
import media.pixi.rx.firebase.auth.kit.R
import javax.inject.Inject
import android.content.Intent
import media.pixi.common.BitmapUtils
import java.io.File


class AccountActivity : DaggerAppCompatActivity() {

    lateinit var fragment: AccountFragment
        @Inject set
    lateinit var navigator: AccountContract.Navigator
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth__activity)
        setSupportActionBar(toolbar)

        ActivityUtils.addFragmentToActivity(
            supportFragmentManager, fragment, R.id.contentFrame
        )
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val uri = navigator.onShowImageFetcherResult(this, requestCode, resultCode, data)
        if (uri != null) {
            val bitmap = BitmapUtils.getScaledBitmapFromUri(this, uri, 120, 120)
            val file = File(cacheDir, "profile_pic.png")
            BitmapUtils.saveFile(bitmap, file)
            fragment.setImage(file)
        }
    }
}
