package media.pixi.appkit.ui.chat.actions;

import android.app.Activity;

import io.reactivex.Completable;
import media.pixi.appkit.R;
import media.pixi.appkit.domain.chats.Message;

public class CopyMessageAction extends MessageAction {

    public CopyMessageAction(Message message) {
        super(message);
        type = Type.Copy;
        titleResourceId = R.string.copy;
        iconResourceId = R.drawable.ic_file_copy_24px;
        //colorId = R.color.button_success;
        successMessageId = R.string.copied_to_clipboard;
    }

    @Override
    public Completable execute(Activity activity) {
        return Completable.create(emitter -> {
//            String text = message.get().getTextRepresentation();
//            if (text != null) {
//                ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
//                ClipData clip = ClipData.newPlainText(activity.getString(R.string.message), text);
//                clipboard.setPrimaryClip(clip);
//                emitter.onComplete();
//            } else {
//                emitter.onError(new Throwable(activity.getString(R.string.copy_failed)));
//            }

            emitter.onComplete();
        });
    }
}
