package com.github.piasy.handywidgets.endlessmovingview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * Created by Piasy{github.com/Piasy} on 15/9/1.
 */
final public class EndlessMovingBitmapView extends EndlessMovingView {
    public EndlessMovingBitmapView(Context context) {
        this(context, null);
    }

    public EndlessMovingBitmapView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private final Bitmap mPic2Draw;

    public EndlessMovingBitmapView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPic2Draw = BitmapFactory.decodeResource(getResources(), mPic2DrawRes);
        if (mPic2Draw == null) {
            throw new IllegalArgumentException("Invalid pic resource");
        }
    }

    @Override
    protected void initMoveState(@MovingDir int movingDir, int minX, int maxX, int minY, int maxY) {
        mSinglePeriodGraphWidth = mPic2Draw.getWidth();
        mSinglePeriodGraphHeight = mPic2Draw.getHeight();
        switch (mMovingDir) {
            case Dir.LEFT:
                mCurOffset = maxX;
                break;
            case Dir.UP:
                mCurOffset = maxY;
                break;
            case Dir.RIGHT:
                mCurOffset = minX - mSinglePeriodGraphWidth;
                break;
            case Dir.DOWN:
                mCurOffset = minY - mSinglePeriodGraphHeight;
                break;
        }
    }

    @Override
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

    @Override
    protected void draw(Canvas canvas, Paint paint, @MovingDir int movingDir, int start) {
        switch (movingDir) {
            case Dir.LEFT:
            case Dir.RIGHT:
                canvas.drawBitmap(mPic2Draw, start, 0, paint);
                break;
            case Dir.UP:
            case Dir.DOWN:
                canvas.drawBitmap(mPic2Draw, 0, start, paint);
                break;
        }
    }

    @Override
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

    @Override
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
}
