package media.pixi.appkit.utils;

import android.graphics.drawable.Drawable;

import androidx.annotation.Nullable;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.chip.Chip;

import java.lang.ref.WeakReference;

public class ChipRequestListener implements RequestListener<Drawable> {

    private final WeakReference<Chip> chip;

    public ChipRequestListener(Chip chip) {
        this.chip = new WeakReference<>(chip);
    }

    @Override
    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
            return false;
    }

    @Override
    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
            setChipIcon(resource);
            return false;
    }

    private void setChipIcon(Drawable drawable) {
        Chip chip = this.chip.get();
        if (chip != null) {
            chip.setChipIcon(drawable);
        }
    }

}
