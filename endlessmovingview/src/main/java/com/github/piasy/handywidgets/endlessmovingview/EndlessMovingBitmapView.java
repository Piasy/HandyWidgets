package com.github.piasy.handywidgets.endlessmovingview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

/**
 * Created by Piasy{github.com/Piasy} on 15/9/1.
 * Set a bitmap(drawable resource) to create an endless moving view. The provided bitmap is the
 * single period graph of the endless view.
 * The direction & speed could be configured.
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
    protected int getSinglePeriodGraphWidth() {
        return mPic2Draw.getWidth();
    }

    @Override
    protected int getSinglePeriodGraphHeight() {
        return mPic2Draw.getHeight();
    }

    @Override
    protected void draw(Canvas canvas, Paint paint, @MovingDir int movingDir, int start, int minX,
            int maxX, int minY, int maxY) {
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
}
