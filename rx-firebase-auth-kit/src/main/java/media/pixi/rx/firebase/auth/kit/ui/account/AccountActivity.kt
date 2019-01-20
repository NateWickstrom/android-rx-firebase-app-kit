package media.pixi.rx.firebase.auth.kit.ui.account

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import media.pixi.rx.firebase.auth.kit.R
import media.pixi.rx.firebase.auth.kit.data.AuthProvider
import javax.inject.Inject

class AccountActivity : DaggerAppCompatActivity() {

    var authProvider: AuthProvider? = null
        @Inject set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
    }
}
