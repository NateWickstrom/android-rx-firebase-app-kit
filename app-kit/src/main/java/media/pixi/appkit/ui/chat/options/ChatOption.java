package media.pixi.appkit.ui.chat.options;

import android.app.Activity;

import io.reactivex.Completable;

/**
 * Created by ben on 10/11/17.
 */

public interface ChatOption {

    int getIconResourceId();
    String getTitle();
    Completable execute(Activity activity, Thread thread);

}
