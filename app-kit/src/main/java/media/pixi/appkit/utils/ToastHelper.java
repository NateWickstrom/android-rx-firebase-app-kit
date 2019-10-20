package media.pixi.appkit.utils;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.StringRes;


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
