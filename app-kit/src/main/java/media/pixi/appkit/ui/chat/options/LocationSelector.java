package media.pixi.appkit.ui.chat.options;

import android.app.Activity;

import com.google.android.gms.maps.model.LatLng;

import org.jetbrains.annotations.NotNull;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.disposables.Disposable;
import media.pixi.appkit.R;
import media.pixi.appkit.utils.GoogleUtils;

/**
 * Created by benjaminsmiley-andrews on 23/05/2017.
 */

public class LocationSelector {

    protected Activity activity;
    protected SingleEmitter<Result> emitter;

    public class Result {
        public LatLng latLng;
        public String snapshotPath;
        public Result (LatLng latLng, String snapshotPath) {
            this.latLng = latLng;
            this.snapshotPath = snapshotPath;
        }
    }

//    public Single<Result> startChooseLocationActivity (Activity activity) {
//        return ChatSDK.locationProvider().getLastLocation(activity).map(location -> {
//            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//
//            int width = activity.getResources().getDimensionPixelSize(R.dimen.message_image_max_width);
//            int height = activity.getResources().getDimensionPixelSize(R.dimen.message_image_max_height);
//
//            return new Result(latLng, GoogleUtils.getMapImageURL(latLng, width, height));
//        });
//    }

    public void clear () {
        emitter = null;
    }

}
