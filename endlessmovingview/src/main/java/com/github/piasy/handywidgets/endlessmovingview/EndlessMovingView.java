package com.github.piasy.handywidgets.endlessmovingview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/31.
 */
public abstract class EndlessMovingView extends View {
    public EndlessMovingView(Context context) {
        this(context, null);
    }

    public EndlessMovingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Moving direction
     */
    public interface Dir {
        int LEFT = 0;
        int UP = 1;
        int RIGHT = 2;
        int DOWN = 3;
    }

    @IntDef(value = { Dir.LEFT, Dir.UP, Dir.RIGHT, Dir.DOWN })
    @Retention(RetentionPolicy.SOURCE)
    public @interface MovingDir {}

    /**
     * Paint style
     */
    public interface Style {
        int FILL = 0;
        int STROKE = 1;
        int FILL_AND_STROKE = 2;
    }

    @IntDef(value = { Style.FILL, Style.STROKE, Style.FILL_AND_STROKE })
    @Retention(RetentionPolicy.SOURCE)
    public @interface PaintStyle {}

    private final Paint mPaint;
    private final RefreshRunnable mRefreshRunnable;
    private boolean isAnimating = false;

    private final boolean isAutoStart;
    private final boolean isStopWhenLoseFocus;
    private final
    @MovingDir
    int mMovingDir;
    private final int mMovingSpeed;
    protected final
    @DrawableRes
    int mPic2DrawRes;
    private final
    @ColorInt
    int mPaintColor;
    private final
    @PaintStyle
    int mPaintStyle;

    public EndlessMovingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRefreshRunnable = new RefreshRunnable(this);
        mPaint = new Paint();

        TypedArray a = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.EndlessMovingView, defStyleAttr, 0);
        isStopWhenLoseFocus = a.getBoolean(R.styleable.EndlessMovingView_stopWhenLoseFocus, false);
        isAutoStart = a.getBoolean(R.styleable.EndlessMovingView_autoStart, true);
        int dir = a.getInteger(R.styleable.EndlessMovingView_movingDir, 0);
        switch (dir) {
            case 1:
                // up
                mMovingDir = Dir.UP;
                break;
            case 2:
                // right
                mMovingDir = Dir.RIGHT;
                break;
            case 3:
                // down
                mMovingDir = Dir.DOWN;
                break;
            case 0:
                // left
            default:
                mMovingDir = Dir.LEFT;
                break;
        }
        mMovingSpeed = a.getInteger(R.styleable.EndlessMovingView_movingSpeed, 0);

        mPic2DrawRes = a.getResourceId(R.styleable.EndlessMovingView_singlePeriodBitmap, 0);

        mPaintColor = a.getColor(R.styleable.EndlessMovingView_paintColor, Color.YELLOW);
        int style = a.getInteger(R.styleable.EndlessMovingView_paintStyle, 0);
        switch (style) {
            case 1:
                // stroke
                mPaintStyle = Style.STROKE;
                break;
            case 2:
                // fill_and_stroke
                mPaintStyle = Style.FILL_AND_STROKE;
                break;
            case 0:
                // fill
            default:
                mPaintStyle = Style.FILL;
                break;
        }
        a.recycle();

        mPaint.setColor(mPaintColor);
        switch (mPaintStyle) {
            case Style.STROKE:
                mPaint.setStyle(Paint.Style.STROKE);
                break;
            case Style.FILL_AND_STROKE:
                mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                break;
            case Style.FILL:
            default:
                mPaint.setStyle(Paint.Style.FILL);
                break;
        }
        mPaint.setAntiAlias(true);
        setLayerType(LAYER_TYPE_HARDWARE, mPaint);
    }

    private int mCurOffset = 0;
    private int mSinglePeriodGraphWidth = 0;
    private int mSinglePeriodGraphHeight = 0;

    /**
     * get single period graph width
     *
     * @return the width of the single period graph
     * */
    protected abstract int getSinglePeriodGraphWidth();

    /**
     * get single period graph height
     *
     * @return the height of the single period graph
     * */
    protected abstract int getSinglePeriodGraphHeight();

    private int mMaxX = 0;
    private int mMaxY = 0;

    private void init() {
        mMaxX = getWidth();
        mMaxY = getHeight();
        mSinglePeriodGraphHeight = getSinglePeriodGraphHeight();
        mSinglePeriodGraphWidth = getSinglePeriodGraphWidth();
        switch (mMovingDir) {
            case Dir.LEFT:
                mCurOffset = mMaxX;
                break;
            case Dir.UP:
                mCurOffset = mMaxY;
                break;
            case Dir.RIGHT:
                mCurOffset = 0 - mSinglePeriodGraphWidth;
                break;
            case Dir.DOWN:
                mCurOffset = 0 - mSinglePeriodGraphHeight;
                break;
        }
        isInitiated = true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        init();
    }

    @Override
    final public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus) {
            if (VISIBLE == getVisibility()) {
                if (isAutoStart) {
                    startMoving();
                }
            } else {
                removeCallbacks(mRefreshRunnable);
                isAnimating = false;
            }
        } else if (isStopWhenLoseFocus) {
            removeCallbacks(mRefreshRunnable);
            isAnimating = false;
        }
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == VISIBLE) {
            if (isInitiated) {
                startMoving();
            }
        } else {
            stopMoving();
        }
    }

    @Override
    final protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(mRefreshRunnable);
        isAnimating = false;
    }

    private boolean isInitiated = false;

    /**
     * repeat draw one period graph with changing offset, in 60 fps, makes the view move endlessly
     */
    @Override
    final protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        if (isInitiated) {
            int start = mCurOffset;
            while (hasNextPeriod(mMovingDir, start, mSinglePeriodGraphWidth,
                    mSinglePeriodGraphHeight, 0, mMaxX, 0, mMaxY)) {
                draw(canvas, mPaint, mMovingDir, start, 0, mMaxX, 0, mMaxY);
                start = move2NextPeriod(mMovingDir, start, mSinglePeriodGraphWidth,
                        mSinglePeriodGraphHeight, 0, mMaxX, 0, mMaxY);
            }
            mCurOffset = move(mCurOffset, mMovingDir, mMovingSpeed, 0, mMaxX, 0, mMaxY);
        }
    }

    /**
     * change the {@link #mCurOffset}, move all period graph towards the {@link #mMovingDir}
     * with {@link #mMovingSpeed}
     *
     * @param curOffset current offset of the total graph
     * @param movingDir moving direction
     * @param movingSpeed moving speed, pixels per frame
     * @param minX the min x coordinate of this view, always 0
     * @param maxX the max x coordinate of this view, always the width
     * @param minY the min y coordinate of this view, always 0
     * @param maxY the max y coordinate of this view, always the height
     * @return return the new offset according to movingDir, movingSpeed and view bounds
     */
    protected int move(int curOffset, @MovingDir int movingDir, int movingSpeed, int minX, int maxX,
            int minY, int maxY) {
        switch (movingDir) {
            case Dir.LEFT:
                if (curOffset > maxX - mSinglePeriodGraphWidth) {
                    return curOffset - movingSpeed;
                } else {
                    return maxX;
                }
            case Dir.UP:
                if (curOffset > maxY - mSinglePeriodGraphHeight) {
                    return curOffset - movingSpeed;
                } else {
                    return maxY;
                }
            case Dir.RIGHT:
                if (curOffset < minX) {
                    return curOffset + movingSpeed;
                } else {
                    return minX - mSinglePeriodGraphWidth;
                }
            case Dir.DOWN:
                if (curOffset < minY) {
                    return curOffset + movingSpeed;
                } else {
                    return minY - mSinglePeriodGraphHeight;
                }
            default:
                return 0;
        }
    }

    /**
     * draw one single period graph at the start position, according to the moving direction
     *
     * @param canvas the canvas to hold the draw commands, passed from {@link #onDraw(Canvas)}
     * @param paint the paint to draw the single period graph
     * @param movingDir moving direction
     * @param start start position of this period graph, left or top
     * @param minX the min x coordinate of this view, always 0
     * @param maxX the max x coordinate of this view, always the width
     * @param minY the min y coordinate of this view, always 0
     * @param maxY the max y coordinate of this view, always the height
     */
    protected abstract void draw(Canvas canvas, Paint paint, @MovingDir int movingDir, int start,
            int minX, int maxX, int minY, int maxY);

    /**
     * check if still need to draw a next period graph
     *
     * @param movingDir moving direction
     * @param start start position of this period graph, left or top
     * @param singlePeriodGraphWidth the width of the single period graph
     * @param singlePeriodGraphHeight the height of the single period graph
     * @param minX the min x coordinate of this view, always 0
     * @param maxX the max x coordinate of this view, always the width
     * @param minY the min y coordinate of this view, always 0
     * @param maxY the max y coordinate of this view, always the height
     * @return {@code true} if still need to draw a next period graph
     */
    protected boolean hasNextPeriod(@MovingDir int movingDir, int start, int singlePeriodGraphWidth,
            int singlePeriodGraphHeight, int minX, int maxX, int minY, int maxY) {
        switch (movingDir) {
            case Dir.LEFT:
                return start >= -singlePeriodGraphWidth;
            case Dir.RIGHT:
                return start <= maxX;
            case Dir.UP:
                return start >= -singlePeriodGraphHeight;
            case Dir.DOWN:
                return start <= maxY;
            default:
                return false;
        }
    }

    /**
     * move the start position to next period graph
     *
     * @param movingDir moving direction
     * @param start start position of this period graph, left or top
     * @param singlePeriodGraphWidth the width of the single period graph
     * @param singlePeriodGraphHeight the height of the single period graph
     * @param minX the min x coordinate of this view, always 0
     * @param maxX the max x coordinate of this view, always the width
     * @param minY the min y coordinate of this view, always 0
     * @param maxY the max y coordinate of this view, always the height
     * @return return the new start position of the next period graph
     */
    protected int move2NextPeriod(@MovingDir int movingDir, int start, int singlePeriodGraphWidth,
            int singlePeriodGraphHeight, int minX, int maxX, int minY, int maxY) {
        switch (movingDir) {
            case Dir.LEFT:
                return start - singlePeriodGraphWidth;
            case Dir.RIGHT:
                return start + singlePeriodGraphWidth;
            case Dir.UP:
                return start - singlePeriodGraphHeight;
            case Dir.DOWN:
                return start + singlePeriodGraphHeight;
            default:
                return 0;
        }
    }

    private static class RefreshRunnable implements Runnable {

        private final WeakReference<EndlessMovingView> mView2Refresh;

        private RefreshRunnable(EndlessMovingView view2Refresh) {
            mView2Refresh = new WeakReference<>(view2Refresh);
        }

        @Override
        public void run() {
            if (mView2Refresh.get() != null) {
                synchronized (mView2Refresh.get()) {
                    long start = System.currentTimeMillis();

                    mView2Refresh.get().invalidate();

                    long gap = 16 - (System.currentTimeMillis() - start);
                    mView2Refresh.get().postDelayed(this, gap < 0 ? 0 : gap);
                }
            }
        }
    }

    /**
     * start moving the view
     */
    final public void startMoving() {
        if (!isAnimating) {
            removeCallbacks(mRefreshRunnable);
            init();
            post(mRefreshRunnable);
            isAnimating = true;
        }
    }

    /**
     * stop moving the view
     */
    final public void stopMoving() {
        removeCallbacks(mRefreshRunnable);
        isAnimating = false;
    }
}
