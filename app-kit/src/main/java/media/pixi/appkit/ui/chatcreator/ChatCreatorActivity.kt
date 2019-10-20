package media.pixi.appkit.ui.chatcreator

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import media.pixi.appkit.R
import media.pixi.appkit.utils.ActivityUtils
import javax.inject.Inject

class ChatCreatorActivity : DaggerAppCompatActivity() {

    lateinit var presenter: ChatCreatorPresenter
        @Inject set

    lateinit var fragment: ChatCreatorFragment
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.appkit__activity_chat_creator)

        fragment.presenter = presenter

        ActivityUtils.addFragmentToActivity(
            supportFragmentManager, fragment, R.id.contentFrame
        )
    }

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, ChatCreatorActivity::class.java)
            activity.startActivity(intent)
        }
    }
}