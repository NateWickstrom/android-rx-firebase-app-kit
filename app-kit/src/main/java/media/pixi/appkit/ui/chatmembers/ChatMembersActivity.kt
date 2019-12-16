package media.pixi.appkit.ui.chatmembers

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.appkit__appbar.*
import media.pixi.appkit.R
import media.pixi.appkit.utils.ActivityUtils
import javax.inject.Inject


class ChatMembersActivity: DaggerAppCompatActivity() {

    lateinit var presenter: ChatMembersPresenter
        @Inject set

    lateinit var fragment: ChatMembersFragment
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.appkit__activity)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val chatId = intent.extras?.getString(BUNDLE_CHAT)!!

        ActivityUtils.addFragmentToActivity(
            supportFragmentManager, fragment, R.id.contentFrame
        )

        fragment.presenter = presenter
        presenter.chatId = chatId
    }

    companion object {
        private const val BUNDLE_CHAT = "chat_id"

        fun launch(activity: Activity, chatId: String) {
            val intent = Intent(activity, ChatMembersActivity::class.java)
            intent.putExtra(BUNDLE_CHAT, chatId)
            activity.startActivity(intent)
        }
    }
}