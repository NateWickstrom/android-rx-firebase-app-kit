package media.pixi.appkit.ui.chat.permissions;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.CompletableEmitter;
import media.pixi.appkit.R;

import static androidx.core.content.PermissionChecker.PERMISSION_DENIED;

/**
 * Created by ben on 9/28/17.
 */

public class PermissionRequestHandler {

    private static final PermissionRequestHandler instance = new PermissionRequestHandler();

    public static PermissionRequestHandler shared () {
        return instance;
    }

    private Map<Integer, CompletableEmitter> completableMap = new HashMap<>();

    private static int RECORD_AUDIO_REQUEST = 102;

    public Completable requestRecordAudio(Activity activity) {
        return requestPermissions(activity, RECORD_AUDIO_REQUEST, recordAudio(), writeExternalStorage());
    }

    public Permission recordAudio () {
        return new Permission(Manifest.permission.RECORD_AUDIO, R.string.permission_record_audio_title, R.string.permission_record_audio_message);
    }

    public Permission writeExternalStorage () {
        return new Permission(Manifest.permission.WRITE_EXTERNAL_STORAGE, R.string.permission_write_external_storage_title, R.string.permission_write_external_storage_message);
    }

    public Completable requestPermissions(final Activity activity, final int result, Permission... permissions) {

        // If the method is called twice, just return success...
        if (completableMap.containsKey(result)) {
            return Completable.complete();
        }
        return Completable.create(e -> {

            // So we can handle multiple requests at the same time, we store the emitter and wait...
            completableMap.put(result, e);

            ArrayList<AlertDialog.Builder> dialogs = new ArrayList<>();
            ArrayList<String> toRequest = new ArrayList<>();

            boolean allGranted = true;
            for (Permission permission : permissions) {
                int permissionCheck = ContextCompat.checkSelfPermission(activity.getApplicationContext(), permission.name);
                if (permissionCheck == PERMISSION_DENIED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission.name)) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                                .setTitle(permission.title(activity))
                                .setMessage(permission.description(activity))
                                .setPositiveButton(R.string.ok, (dialog, which) -> {
                                    ActivityCompat.requestPermissions(activity, permission.permissions(), result);
                                })
                                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                                    String message = String.format(activity.getString(R.string.permission_not_granted), permission.description(activity));
                                    completableMap.remove(result);
                                    e.onError(new Throwable(message));
                                });
                        dialogs.add(builder);
                    } else {
                        toRequest.add(permission.name);
                    }
                }
                allGranted = allGranted && permissionCheck != PERMISSION_DENIED;
            }

            // If the name is denied, we request it
            if (!allGranted) {
                for (AlertDialog.Builder b : dialogs) {
                    b.show();
                }
                ActivityCompat.requestPermissions(activity, toRequest.toArray(new String[toRequest.size()]),  result);
            } else {
                // If the name isn't denied, we remove the emitter and return success
                completableMap.remove(result);
                e.onComplete();
            }
        });
    }

    public void onRequestPermissionsResult(Context context, int requestCode, String permissions[], int[] grantResults) {
        CompletableEmitter e = completableMap.get(requestCode);
        if (e != null) {
            completableMap.remove(requestCode);

            String errorCodes = "";
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PERMISSION_DENIED) {
                    errorCodes += new Permission(permissions[i]).name + ", ";
                }
            }
            if (!errorCodes.isEmpty()) {
                errorCodes = errorCodes.substring(0, errorCodes.length() - 2);
            }

            if (errorCodes.isEmpty()) {
                e.onComplete();
            }
            else {
                // TODO: this is being called for the contact book (maybe it's better to request contacts
                // from inside the contact book module
                Throwable throwable = new Throwable(context.getString(R.string.error_permission_not_granted, errorCodes));
                e.onError(throwable);
            }
        }
    }


}
