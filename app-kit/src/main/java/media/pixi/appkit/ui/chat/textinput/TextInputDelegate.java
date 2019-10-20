package media.pixi.appkit.ui.chat.textinput;

import media.pixi.appkit.data.audio.Recording;

public interface TextInputDelegate {

    void showOptions();
    void hideOptions();
    void onSendPressed(String text);
    void startTyping();
    void sendAudio(Recording recording);
    void stopTyping();
    void onKeyboardShow();
    void onKeyboardHide();

}
