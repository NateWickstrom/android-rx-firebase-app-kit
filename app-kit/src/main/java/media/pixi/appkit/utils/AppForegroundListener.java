package media.pixi.appkit.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

public class AppForegroundListener implements Application.ActivityLifecycleCallbacks {

    private boolean isForegranded;
    private int activityReferences = 0;
    private boolean isActivityChangingConfigurations = false;

    public boolean isForegranded() {
        return isForegranded;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (++activityReferences == 1 && !isActivityChangingConfigurations) {
            // App enters foreground
            isForegranded = true;
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        isActivityChangingConfigurations = activity.isChangingConfigurations();
        if (--activityReferences == 0 && !isActivityChangingConfigurations) {
            // App enters background
            isForegranded = false;
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
