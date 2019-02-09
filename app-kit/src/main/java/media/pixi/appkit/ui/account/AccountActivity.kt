package media.pixi.appkit.ui.account

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.appkit__appbar.*
import media.pixi.appkit.utils.ActivityUtils
import javax.inject.Inject
import android.content.Intent
import media.pixi.appkit.R
import media.pixi.appkit.data.auth.AuthProvider
import media.pixi.appkit.data.storage.CloudStorageRepo
import media.pixi.appkit.utils.BitmapUtils
import timber.log.Timber
import java.io.File


class AccountActivity : DaggerAppCompatActivity() {

    lateinit var fragment: AccountFragment
        @Inject set
    lateinit var navigator: AccountContract.Navigator
        @Inject set
    lateinit var cloudStorageRepo: CloudStorageRepo
        @Inject set
    lateinit var authProvider: AuthProvider
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.appkit__activity)
        setSupportActionBar(toolbar)

        ActivityUtils.addFragmentToActivity(
            supportFragmentManager, fragment, R.id.contentFrame
        )
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val uri = navigator.onShowImageFetcherResult(this, requestCode, resultCode, data)
        if (uri != null) {
            val bitmap = BitmapUtils.getScaledBitmapFromUri(this, uri, 120, 120)
            val file = File(cacheDir, "profile_pic.jpeg")
            BitmapUtils.saveFile(bitmap, file)
            fragment.setImage(file)
            cloudStorageRepo.setUserProfileImage(file)
                .flatMapMaybe {
                    cloudStorageRepo.getUserProfileImageReference() }
                .flatMapCompletable {
                    authProvider.updateProfileImage(it.toString()) }
                .subscribe(
                    {
                        Timber.d( "Upload complete") },
                    {
                        Timber.e(it.message, it) }
                )
        }
    }
}
