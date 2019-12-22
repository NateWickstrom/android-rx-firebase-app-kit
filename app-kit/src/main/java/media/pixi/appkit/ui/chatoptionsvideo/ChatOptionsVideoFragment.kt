package media.pixi.appkit.ui.chatoptionsvideo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import media.pixi.appkit.R

class ChatOptionsVideoFragment : BottomSheetDialogFragment(), ChatOptionsVideoContract.View {

    override var error: String
        get() = ""
        set(value) {}

    lateinit var presenter: ChatOptionsVideoContract.Presenter

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
            presenter.onGalleryClicked(activity!!)
        }
        view.findViewById<View>(R.id.camera).setOnClickListener {
            dismiss()
            presenter.onCameraClicked(activity!!)
        }

        return view
    }

    companion object {
        fun newInstance(): ChatOptionsVideoFragment {
            return ChatOptionsVideoFragment()
        }
    }
}