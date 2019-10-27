package media.pixi.appkit.ui.chat.actions;

import android.app.Activity;

import io.reactivex.Completable;
import media.pixi.appkit.R;
import media.pixi.appkit.domain.chats.Message;

public class DeleteMessageAction extends MessageAction {

    public DeleteMessageAction(Message message) {
        super(message);
        type = Type.Delete;
        titleResourceId = R.string.delete;
        iconResourceId = R.drawable.ic_delete_24px;
        //colorId = R.color.primary;
    }

    @Override
    public Completable execute(Activity activity) {
        return Completable.complete(); //ChatSDK.thread().deleteMessage(message.get());
    }
}
