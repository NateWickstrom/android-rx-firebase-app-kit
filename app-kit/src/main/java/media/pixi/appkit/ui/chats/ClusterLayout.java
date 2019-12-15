package media.pixi.appkit.ui.chats;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import media.pixi.appkit.R;

public class ClusterLayout extends ViewGroup {

    private static final double CONSTANT_FOR_TWO_VIEWS = 2 + ( 2 / Math.sqrt(2) );
    private static final double CONSTANT_FOR_THREE_VIEWS = 1 - Math.sin(Math.toRadians(60));

    interface ClusteredView {
        String getImageUrl();
        String getName();
        int getBackgroundColor();
    }

    public ClusterLayout(Context context) {
        this(context, null, 0);
    }

    public ClusterLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClusterLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        final int count = getChildCount();

        final int childLeft = this.getPaddingLeft();
        final int childTop = this.getPaddingTop();
        final int childRight = this.getMeasuredWidth() - this.getPaddingRight();
        final int childBottom = this.getMeasuredHeight() - this.getPaddingBottom();
        final int width = childRight - childLeft;
        final int height = childBottom - childTop;

        if (count == 1) {
            View child = getChildAt(0);
            final int childWidth = width;
            final int childHeight = height;

            //do the layout
            child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
        }

        if (count == 2) {
            final double r = width / CONSTANT_FOR_TWO_VIEWS;

            final int childWidth = (int)(r * 2);
            final int childHeight = childWidth;

            setupChildren(childWidth, childHeight);

            View child = getChildAt(0);
            View child2 = getChildAt(1);

            int top = childTop;
            int bottom = childBottom;

            child.measure(childWidth, childHeight);
            child.layout(
                    childLeft,
                    top,
                    childLeft + childWidth,
                    top + childHeight);

            child2.layout(
                    childRight - childWidth,
                    bottom - childHeight,
                    childRight,
                    bottom);
        }

        if (count == 3) {
            final int r = width / 4;
            final int x = (int)(r * CONSTANT_FOR_THREE_VIEWS);

            final int childWidth = r * 2;
            final int childHeight = childWidth;

            setupChildren(childWidth, childHeight);

            View child1 = getChildAt(0);
            View child2 = getChildAt(1);
            View child3 = getChildAt(2);

            child1.layout(
                    childLeft + r,
                    childTop + x,
                    childLeft + r + childWidth,
                    childTop + x + childHeight);

            int bottomTop = childBottom - x - childHeight;
            child2.layout(
                    childLeft,
                    bottomTop,
                    childLeft + childWidth,
                    childBottom - x);

            child3.layout(
                    childLeft + childWidth,
                    bottomTop,
                    childRight,
                    childBottom - x);
        }

        if (count >= 4) {
            final int childWidth = width / 2;
            final int childHeight = height / 2;

            setupChildren(childWidth, childHeight);

            View child1 = getChildAt(0);
            View child2 = getChildAt(1);
            View child3 = getChildAt(2);
            View child4 = getChildAt(3);

            int top = childTop;
            int bottom = childBottom;
            int centerWidth = childLeft + childWidth;
            int centerHeight = childTop + childWidth;

            child1.layout(
                    childLeft,
                    top,
                    centerWidth,
                    centerHeight);

            child2.layout(
                    centerWidth,
                    top,
                    childRight,
                    centerHeight);

            child3.layout(
                    childLeft,
                    centerHeight,
                    centerWidth,
                    bottom);

            child4.layout(
                    centerWidth,
                    centerHeight,
                    childRight,
                    bottom);
        }
    }

    public void setClusteredViews(List<ClusteredView> heads) {
        removeAllViews();
        if (heads.size() == 1) {
            ClusteredView head = heads.get(0);
            addView(createChildView(head.getImageUrl(), null, head.getBackgroundColor()));
        } else {
            for (ClusteredView head : heads) {
                addView(createChildView(head.getImageUrl(), head.getName(), head.getBackgroundColor()));
            }
        }
        requestLayout();
    }

    private void setupChildren(int width, int height) {
        final LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width, height);
        final int mWidth = MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST);
        final int mHeight = MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST);

        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            child.setLayoutParams(layoutParams);
            child.measure(mWidth, mHeight);
        }
    }

    private View createChildView(String imageUrl,
                                 String name,
                                 int backgroundColor) {
        ImageView image = new ImageView(getContext());
        Drawable placeholder = getDrawable(name, backgroundColor);

        Glide.with(this)
                .load(imageUrl)
                .placeholder(placeholder)
                .error(placeholder)
                .apply(RequestOptions.circleCropTransform())
                .into(image);

        return image;
    }

    private Drawable getDrawable(String name, int backgroundColor) {
        return name == null || name.isEmpty()
                ? getDefaultDrawable(backgroundColor)
                : TextDrawable.builder()
                    .beginConfig()
                    .bold()
                    .endConfig()
                    .round()
                    .build(name, backgroundColor);
    }

    private Drawable getDefaultDrawable(int color) {
        Drawable userIcon = getContext().getDrawable(R.drawable.ic_person_24px);
        userIcon.setTint(Color.WHITE);

        int px = userIcon.getIntrinsicHeight() / 8;
        InsetDrawable insetDrawable = new InsetDrawable(userIcon, px);

        ShapeDrawable ovalDrawable = new ShapeDrawable(new OvalShape());
        ovalDrawable.getPaint().setColor(color);

        Drawable[] layers = new Drawable[]{
                ovalDrawable,
                insetDrawable
        };

        return new LayerDrawable(layers);
    }

}
