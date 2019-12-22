package media.pixi.appkit.ui.chat

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.appkit__appbar.*
import media.pixi.appkit.R
import media.pixi.appkit.ui.chatoptions.ChatOptionsContract
import media.pixi.appkit.ui.chatoptionsimage.ChatOptionsImageContract
import media.pixi.appkit.ui.chatoptionsimage.ChatOptionsImageNavigator
import media.pixi.appkit.ui.chatoptionsvideo.ChatOptionsVideoContract
import media.pixi.appkit.ui.chatoptionsvideo.ChatOptionsVideoNavigator
import media.pixi.appkit.utils.ActivityUtils
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import java.io.InputStream
import javax.inject.Inject

class ChatActivity : DaggerAppCompatActivity() {

    lateinit var fragment: ChatFragment
        @Inject set
    lateinit var presenter: ChatPresenter
        @Inject set
    lateinit var optionsPresenter: ChatOptionsContract.Presenter
        @Inject set
    lateinit var optionsImagePresenter: ChatOptionsImageContract.Presenter
        @Inject set
    lateinit var optionsVideoPresenter: ChatOptionsVideoContract.Presenter
        @Inject set
    lateinit var navigator: ChatContract.Navigator
        @Inject set

    var imageFilePath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.appkit__activity_chat)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val chatId = intent.extras?.getString(BUNDLE_CHAT)
        val userIds = intent.extras?.getCharSequenceArrayList(BUNDLE_USERS)
        imageFilePath = savedInstanceState?.getString(IMAGE_FILE_PATH)

        presenter.chatId = chatId
        presenter.userIds = userIds

        fragment.presenter = presenter

        ActivityUtils.addFragmentToActivity(
            supportFragmentManager, fragment, R.id.contentFrame
        )
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putString(IMAGE_FILE_PATH, imageFilePath)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == ChatOptionsVideoNavigator.CAMERA_VIDEO_REQUEST) {
            val uri = data?.data!!
            val file = toFile(uri)
            contentResolver.delete(uri, null, null)
            presenter.onVideoSelected(file.absolutePath)
        }
        if (resultCode == Activity.RESULT_OK && requestCode == ChatOptionsImageNavigator.CAMERA_IMAGE_REQUEST) {
            presenter.onImageSelected(imageFilePath!!)
        }
        if (resultCode == Activity.RESULT_OK && requestCode == ChatOptionsImageNavigator.GALLERY_IMAGE_REQUEST) {
            val uri = data?.data!!
            val file = toFile(uri)
            presenter.onImageSelected(file.absolutePath)
        }
        if (resultCode == Activity.RESULT_OK && requestCode == ChatOptionsVideoNavigator.GALLERY_VIDEO_REQUEST) {
            val uri = data?.data!!
            val file = toFile(uri)
            presenter.onVideoSelected(file.absolutePath)
        }
    }

    private fun toFile(uri: Uri): File {
        val input = contentResolver.openInputStream(uri)
        val file = File(cacheDir, "attachment")
        file.copyInputStreamToFile(input!!)
        return file
    }

    private fun File.copyInputStreamToFile(inputStream: InputStream) {
        this.outputStream().use { fileOut ->
            inputStream.copyTo(fileOut)
        }
    }

    companion object {
        private const val BUNDLE_CHAT = "chat_id"
        private const val BUNDLE_USERS = "users"
        private const val IMAGE_FILE_PATH = "image_file_path"

        fun launch(activity: Activity, chatId: String? = null, userIds: ArrayList<CharSequence>? = null) {
            val intent = Intent(activity, ChatActivity::class.java)
            intent.putExtra(BUNDLE_CHAT, chatId)
            intent.putCharSequenceArrayListExtra(BUNDLE_USERS, userIds)
            activity.startActivity(intent)
        }
    }
}