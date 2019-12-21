package media.pixi.appkit.ui.chat.actions;

import android.app.Activity;

import io.reactivex.Completable;
import io.reactivex.disposables.CompositeDisposable;
import media.pixi.appkit.R;
import media.pixi.appkit.domain.chats.models.Message;

public class ForwardMessageAction extends MessageAction {

    protected int messageForwardActivityCode = 998;

    protected CompositeDisposable disposableList = new CompositeDisposable();

    public ForwardMessageAction(Message message) {
        super(message);
        type = Type.Forward;
        titleResourceId = R.string.forward;
        iconResourceId = R.drawable.ic_forward_24px;
        //colorId = R.color.button_success;
        successMessageId = R.string.message_sent;
    }

    @Override
    public Completable execute(Activity activity) {
        return Completable.create(emitter -> {
//            disposableList.add(ActivityResultPushSubjectHolder.shared().subscribe(activityResult -> {
//                if (activityResult.requestCode == messageForwardActivityCode) {
//                    if (activityResult.resultCode == Activity.RESULT_OK) {
//                        emitter.onComplete();
//                    } else {
//                        if (activityResult.data != null) {
//                            String errorMessage = activityResult.data.getStringExtra(Keys.IntentKeyErrorMessage);
//                            emitter.onError(new Throwable(errorMessage));
//                        }
//                    }
//                    disposableList.clear();
//                }
//            }));
//            ChatSDK.ui().startForwardMessageActivityForResult(activity, message.get(), messageForwardActivityCode);
            emitter.onComplete();
        });
    }
}
