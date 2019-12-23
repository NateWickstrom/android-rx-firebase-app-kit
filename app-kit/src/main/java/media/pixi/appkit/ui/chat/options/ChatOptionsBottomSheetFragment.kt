package media.pixi.appkit.ui.chat.options

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import media.pixi.appkit.R

class ChatOptionsBottomSheetFragment : BottomSheetDialogFragment() {

    interface PrimaryOptionOnClickListener {
        fun showImageChooser(activity: Activity)
        fun showVideoChooser(activity: Activity)
    }

    var listener: PrimaryOptionOnClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(
            R.layout.appkit__fragment_chat_options, container,
            false
        )

        view.findViewById<View>(R.id.location).setOnClickListener {
            dismiss()
        }
        view.findViewById<View>(R.id.image).setOnClickListener {
            dismiss()
            listener?.showImageChooser(activity!!)
        }
        view.findViewById<View>(R.id.video).setOnClickListener {
            dismiss()
            listener?.showVideoChooser(activity!!)
        }

        return view

    }

    companion object {
        fun newInstance(): ChatOptionsBottomSheetFragment {
            return ChatOptionsBottomSheetFragment()
        }
    }
}