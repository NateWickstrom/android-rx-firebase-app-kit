package media.pixi.appkit.ui.imageviewer

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.piasy.biv.view.BigImageView
import media.pixi.appkit.R

class ImageViewerFragment: Fragment(), ImageViewerContract.View {

    override var error: String
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    lateinit var url: String
    lateinit var presenter: ImageViewerContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        url = arguments?.getString(IMAGE_URL)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(
            R.layout.appkit__fragment_image_viewer, container,
            false
        ) as BigImageView
        view.showImage(Uri.parse(url))

        return view

    }

    companion object {

        const val IMAGE_URL = "image_url"

        fun newInstance(url: String): ImageViewerFragment {
            val bundle = Bundle()
            bundle.putString(IMAGE_URL, url)
            val fragment = ImageViewerFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}