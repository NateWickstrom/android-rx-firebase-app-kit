package media.pixi.appkit.example.ui.home

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_home.*
import media.pixi.appkit.example.R
import javax.inject.Inject

class HomeActivity : DaggerAppCompatActivity(), HomeContract.View {

    lateinit var presenter: HomeContract.Presenter
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        search.setOnClickListener { presenter.onSearchClicked(this) }
        profile.setOnClickListener { presenter.onProfileClicked(this) }
        settings.setOnClickListener { presenter.onSettingsClicked(this) }
        notifications.setOnClickListener { presenter.onNotificationsClicked(this) }
        devices.setOnClickListener { presenter.onDevicesClicked(this) }
        myFriends.setOnClickListener { presenter.onFriendsClicked(this) }

    }
}