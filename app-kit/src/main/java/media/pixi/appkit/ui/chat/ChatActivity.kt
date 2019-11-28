package media.pixi.appkit.ui.chat

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.appkit__appbar.*
import media.pixi.appkit.R
import media.pixi.appkit.ui.chatoptions.ChatOptionsContract
import media.pixi.appkit.utils.ActivityUtils
import javax.inject.Inject
import pub.devrel.easypermissions.EasyPermissions

class ChatActivity : DaggerAppCompatActivity() {

    lateinit var fragment: ChatFragment
        @Inject set
    lateinit var presenter: ChatPresenter
        @Inject set
    lateinit var optionsPresenter: ChatOptionsContract.Presenter
        @Inject set
    lateinit var navigator: ChatContract.Navigator
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.appkit__activity_chat)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val chatId = intent.extras?.getString(BUNDLE_CHAT)
        val userIds = intent.extras?.getCharSequenceArrayList(BUNDLE_USERS)

        presenter.chatId = chatId
        presenter.userIds = userIds

        fragment.presenter = presenter

        ActivityUtils.addFragmentToActivity(
            supportFragmentManager, fragment, R.id.contentFrame
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    companion object {
        private const val BUNDLE_CHAT = "chat_id"
        private const val BUNDLE_USERS = "users"

        fun launch(activity: Activity, chatId: String? = null, userIds: ArrayList<CharSequence>? = null) {
            val intent = Intent(activity, ChatActivity::class.java)
            intent.putExtra(BUNDLE_CHAT, chatId)
            intent.putCharSequenceArrayListExtra(BUNDLE_USERS, userIds)
            activity.startActivity(intent)
        }
    }
}