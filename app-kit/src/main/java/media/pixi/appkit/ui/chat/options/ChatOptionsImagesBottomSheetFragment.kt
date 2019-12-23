package media.pixi.appkit.ui.chat.options

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import media.pixi.appkit.R

class ChatOptionsImagesBottomSheetFragment : BottomSheetDialogFragment() {

    interface ChatOptionsForImagesOnClickListener {
        fun onGalleryImageClicked(activity: Activity)
        fun onCameraImageClicked(activity: Activity)
    }

    var listener: ChatOptionsForImagesOnClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(
            R.layout.appkit__fragment_chat_options_images, container,
            false
        )

        view.findViewById<View>(R.id.gallery).setOnClickListener {
            dismiss()
            listener?.onGalleryImageClicked(activity!!)
        }
        view.findViewById<View>(R.id.camera).setOnClickListener {
            dismiss()
            listener?.onCameraImageClicked(activity!!)
        }

        return view
    }

    companion object {
        fun newInstance(): ChatOptionsImagesBottomSheetFragment {
            return ChatOptionsImagesBottomSheetFragment()
        }
    }
}