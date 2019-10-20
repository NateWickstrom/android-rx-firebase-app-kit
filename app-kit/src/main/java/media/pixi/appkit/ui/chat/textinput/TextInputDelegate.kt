package media.pixi.appkit.ui.chat.textinput

import media.pixi.appkit.data.audio.Recording

interface TextInputDelegate {

    fun showOptions()
    fun hideOptions()
    fun onSendPressed(text: String)
    fun startTyping()
    fun sendAudio(recording: Recording)
    fun stopTyping()
    fun onKeyboardShow()
    fun onKeyboardHide()

}
