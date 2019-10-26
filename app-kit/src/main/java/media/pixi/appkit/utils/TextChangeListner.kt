package media.pixi.appkit.utils

import android.text.Editable
import android.text.TextWatcher

interface TextChangeListner: TextWatcher {

     override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
     }

     override fun afterTextChanged(s: Editable?) {
     }
 }