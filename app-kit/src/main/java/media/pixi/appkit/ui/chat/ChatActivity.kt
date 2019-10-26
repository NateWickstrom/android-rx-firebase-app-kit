package media.pixi.appkit.ui.chat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.appkit__appbar.*
import media.pixi.appkit.R
import media.pixi.appkit.utils.ActivityUtils
import javax.inject.Inject

class ChatActivity : DaggerAppCompatActivity() {

    lateinit var fragment: ChatFragment
        @Inject set
    lateinit var presenter: ChatPresenter
        @Inject set
    lateinit var navigator: ChatContract.Navigator
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.appkit__activity_chat)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fragment.presenter = presenter

        ActivityUtils.addFragmentToActivity(
            supportFragmentManager, fragment, R.id.contentFrame
        )
    }

    companion object {
        private const val BUNDLE_CHAT = "chat_id"

        fun launch(activity: Activity, chatId: String) {
            val intent = Intent(activity, ChatActivity::class.java)
            //intent.putExtra(BUNDLE_CHAT, )
            activity.startActivity(intent)
        }
    }
}