package media.pixi.appkit.ui.chatoptions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import media.pixi.appkit.R

class ChatOptionFragment : BottomSheetDialogFragment(), ChatOptionsContract.View {

    override var error: String
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    lateinit var presenter: ChatOptionsContract.Presenter

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
            presenter.onLocationClicked(activity!!)
        }
        view.findViewById<View>(R.id.image).setOnClickListener {
            dismiss()
            presenter.onImageClicked(activity!!)
        }
        view.findViewById<View>(R.id.video).setOnClickListener {
            dismiss()
            presenter.onVideoClicked(activity!!)
        }

        return view

    }

    companion object {
        fun newInstance(): ChatOptionFragment {
            return ChatOptionFragment()
        }
    }
}