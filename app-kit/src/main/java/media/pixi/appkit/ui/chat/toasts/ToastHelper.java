package media.pixi.appkit.ui.chat.toasts;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.StringRes;


/**
 * Created by ben on 9/8/17.
 */

public class ToastHelper {

    public static void show(Context context, String text) {
        if(!isEmpty(text)) {
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        }
    }

    public static void show(Context context, @StringRes int resourceId){
        show(context, context.getString(resourceId));
    }

    private static boolean isEmpty(String text) {
        return text == null || text.length() == 0;
    }

}
