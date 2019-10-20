package media.pixi.appkit.ui.chatcreator

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.appkit__appbar.*
import media.pixi.appkit.R

class ChatCreatorActivity : DaggerAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.appkit__activity)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, ChatCreatorActivity::class.java)
            activity.startActivity(intent)
        }
    }
}