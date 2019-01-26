package media.pixi.rx.firebase.profile.kit.ui.profile

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import dagger.android.support.DaggerFragment
import media.pixi.rx.firebase.profile.kit.R
import javax.inject.Inject

class ProfileFragment @Inject constructor(): DaggerFragment(), ProfileContract.View {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater.inflate(R.menu.profile__menu, menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.menu_edit -> {
//                //presenter.onSignUpClicked(activity!!)
//                return true
//            }
//        }
//
//        return false
//    }
}