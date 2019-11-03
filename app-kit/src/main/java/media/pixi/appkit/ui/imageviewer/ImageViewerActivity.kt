package media.pixi.appkit.ui.imageviewer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.appkit__appbar.*
import media.pixi.appkit.R
import media.pixi.appkit.utils.ActivityUtils

class ImageViewerActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.appkit__activity)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val imageUrl = intent.extras?.getString(IMAGE_URL)!!

        val fragment = ImageViewerFragment.newInstance(imageUrl)

        ActivityUtils.addFragmentToActivity(
            supportFragmentManager, fragment, R.id.contentFrame
        )
    }

    companion object {
        private const val IMAGE_URL = "image_url"

        fun launch(activity: Activity, imageUrl: String) {
            val intent = Intent(activity, ImageViewerActivity::class.java)
            intent.putExtra(IMAGE_URL, imageUrl)
            activity.startActivity(intent)
        }
    }
}