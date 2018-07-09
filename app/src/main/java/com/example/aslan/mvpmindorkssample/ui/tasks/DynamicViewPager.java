package com.example.aslan.mvpmindorkssample.ui.tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Debug;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class DynamicViewPager extends ViewPager {

    private Bitmap backgroundId = null;
    private int backgroundSaveId = -1;
    private int savedWidth = -1;
    private int savedHeight = -1;
    private int savedMaxNumPages = -1;
    private Bitmap savedBitmap;
    private boolean insufficientMemory = false;

    private int maxNumPages = 0;
    private int imageHeight;
    private float zoomLevel;
    private float overlapLevel;
    private int currentPosition = -1;
    private float currentOffset = 0.0f;
    private Rect src = new Rect();
    private Rect dst = new Rect();

    private boolean pagingEnabled = true;
    private boolean parallaxEnabled = true;

    private final static String TAG = DynamicViewPager.class.getSimpleName();

    public DynamicViewPager(Context context) {
        super(context);
    }

    public DynamicViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private int sizeOf(Bitmap data) {
        return data.getByteCount();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //Log.d(TAG, "onLayout: ");
        if (!insufficientMemory && parallaxEnabled)
            setNewBackground();
    }

    private void setNewBackground() {
        if (backgroundId == null) {
            return;
        }

        if (maxNumPages == 0) {
            return;
        }

        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        if ((savedHeight == getHeight()) && (savedWidth == getWidth()) &&
                (savedMaxNumPages == maxNumPages)) {
            return;
        }

        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;

            imageHeight = backgroundId.getHeight();
            int imageWidth = backgroundId.getWidth();
            Log.v(TAG, "imageHeight=" + imageHeight + ", imageWidth=" + imageWidth);

            zoomLevel = ((float) imageHeight) / getHeight();  // we are always in 'fitY' mode

            options.inJustDecodeBounds = false;
            options.inSampleSize = Math.round(zoomLevel);

            if (options.inSampleSize > 1) {
                imageHeight = imageHeight / options.inSampleSize;
                imageWidth = imageWidth / options.inSampleSize;
            }
            Log.v(TAG, "imageHeight=" + imageHeight + ", imageWidth=" + imageWidth);

            double max = Runtime.getRuntime().maxMemory(); //the maximum memory the app can use
            double heapSize = Runtime.getRuntime().totalMemory(); //current heap size
            double heapRemaining = Runtime.getRuntime().freeMemory(); //amount available in heap
            double nativeUsage = Debug.getNativeHeapAllocatedSize();
            double remaining = max - (heapSize - heapRemaining) - nativeUsage;

            int freeMemory = (int) (remaining / 1024);
            int bitmapSize = imageHeight * imageWidth * 4 / 1024;
            Log.v(TAG, "freeMemory = " + freeMemory);
            Log.v(TAG, "calculated bitmap size = " + bitmapSize);
            if (bitmapSize > freeMemory / 5) {
                Log.d(TAG, "setNewBackground: lalalal");
                insufficientMemory = true;
                return; // we aren't going to use more than one fifth of free memory
            }

            zoomLevel = ((float) imageHeight) / getHeight();  // we are always in 'fitY' mode
            // how many pixels to shift for each panel
            overlapLevel = zoomLevel * Math.min(Math.max(imageWidth / zoomLevel - getWidth(), 0) / (maxNumPages - 1), getWidth() / 2);


            //savedBitmap = BitmapFactory.decodeStream(bs, null, options);
            savedBitmap = backgroundId;
            Log.i(TAG, "real bitmap size = " + sizeOf(savedBitmap) / 1024);
            Log.v(TAG, "saved_bitmap.getHeight()=" + savedBitmap.getHeight() + ", saved_bitmap.getWidth()=" + savedBitmap.getWidth());

        } catch (Exception e) {
            Log.e(TAG, "Cannot decode: " + e.getMessage());
            backgroundId = null;
            return;
        }

        savedHeight = getHeight();
        savedWidth = getWidth();
        //savedBitmap = backgroundId;
        savedMaxNumPages = maxNumPages;
    }

    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        super.onPageScrolled(position, offset, offsetPixels);
        currentPosition = position;
        currentOffset = offset;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!insufficientMemory && parallaxEnabled) {
            if (currentPosition == -1)
                currentPosition = getCurrentItem();
            // maybe we could get the current position from the getScrollX instead?
            src.set((int) (overlapLevel * (currentPosition + currentOffset)), 0,
                    (int) (overlapLevel * (currentPosition + currentOffset) + (getWidth() * zoomLevel)), imageHeight);

            dst.set((getScrollX()), 0, (getScrollX() + canvas.getWidth()), canvas.getHeight());
            if (savedBitmap!=null) {
                canvas.drawBitmap(savedBitmap, src, dst, null);
                canvas.drawARGB(142, 40, 36, 36);
            }
            else setNewBackground();
        }
    }

    public void setMaxPages(int numMaxPages) {
        maxNumPages = numMaxPages;
        setNewBackground();
    }

    public void setBackgroundAsset(Bitmap resId) {
        backgroundId = resId;
        setNewBackground();
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
        currentPosition = item;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return this.pagingEnabled && super.onTouchEvent(event);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (isFakeDragging()) {
            return false;
        }
        return this.pagingEnabled && super.onInterceptTouchEvent(event);
    }

    public boolean isPagingEnabled() {
        return pagingEnabled;
    }

    /**
     * Enables or disables paging for this ViewPagerParallax.
     */
    public void setPagingEnabled(boolean pagingEnabled) {
        this.pagingEnabled = pagingEnabled;
    }

    public boolean isParallaxEnabled() {
        return parallaxEnabled;
    }

    /**
     * Enables or disables parallax effect for this ViewPagerParallax.
     */
    public void setParallaxEnabled(boolean parallaxEnabled) {
        this.parallaxEnabled = parallaxEnabled;
    }

    protected void onDetachedFromWindow() {
        if (savedBitmap!=null) {
            savedBitmap=null;
        }
        super.onDetachedFromWindow();
    }
}
