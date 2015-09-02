package com.github.piasy.handywidgets.endlessmovingview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

/**
 * Created by Piasy{github.com/Piasy} on 15/9/1.
 */
public abstract class EndlessMovingPathView extends EndlessMovingView {
    public EndlessMovingPathView(Context context) {
        this(context, null);
    }

    public EndlessMovingPathView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private final Path mPath;

    public EndlessMovingPathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPath = new Path();
    }

    @Override
    protected void draw(Canvas canvas, Paint paint, @MovingDir int movingDir, int start, int minX,
            int maxX, int minY, int maxY) {
        mPath.reset();
        drawPath(mPath, movingDir, start, minX, maxX, minY, maxY);
        canvas.drawPath(mPath, paint);
    }

    /**
     * draw a single period graph in the path
     *
     * @param path the {@link Path} object to record the single period graph
     * @param movingDir moving direction
     * @param minX the min x coordinate of this view, always 0
     * @param maxX the max x coordinate of this view, always the width
     * @param minY the min y coordinate of this view, always 0
     * @param maxY the max y coordinate of this view, always the height
     */
    protected abstract void drawPath(Path path, @MovingDir int movingDir, int start, int minX,
            int maxX, int minY, int maxY);
}
