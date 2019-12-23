package media.pixi.appkit.ui.chat.options

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import media.pixi.appkit.R

class ChatOptionsVideosBottomSheetFragment : BottomSheetDialogFragment() {

    interface ChatOptionsOnVideoClickedListener {
        fun onGalleryVideoClicked(activity: Activity)
        fun onCameraVideoClicked(activity: Activity)
    }

    var listener: ChatOptionsOnVideoClickedListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(
            R.layout.appkit__fragment_chat_options_videos, container,
            false
        )

        view.findViewById<View>(R.id.gallery).setOnClickListener {
            dismiss()
            listener?.onGalleryVideoClicked(activity!!)
        }
        view.findViewById<View>(R.id.camera).setOnClickListener {
            dismiss()
            listener?.onCameraVideoClicked(activity!!)
        }

        return view
    }

    companion object {
        fun newInstance(): ChatOptionsVideosBottomSheetFragment {
            return ChatOptionsVideosBottomSheetFragment()
        }
    }
}