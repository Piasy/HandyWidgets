package com.github.piasy.handywidgets;

import android.content.Context;
import android.graphics.Path;
import android.util.AttributeSet;
import com.github.piasy.handywidgets.endlessmovingview.EndlessMovingPathView;
import com.github.piasy.handywidgets.endlessmovingview.EndlessMovingView;

/**
 * Created by Piasy{github.com/Piasy} on 15/9/1.
 */
public class SimpleEndlessMovingPathView extends EndlessMovingPathView {
    public SimpleEndlessMovingPathView(Context context) {
        this(context, null);
    }

    public SimpleEndlessMovingPathView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleEndlessMovingPathView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getSinglePeriodGraphWidth() {
        return 60;
    }

    @Override
    protected int getSinglePeriodGraphHeight() {
        return getHeight();
    }

    @Override
    protected void drawPath(Path path, @EndlessMovingView.MovingDir int movingDir, int start,
            int minX, int maxX, int minY, int maxY) {
        switch (movingDir) {
            case Dir.LEFT:
                path.moveTo(start + minX, minY);
                path.lineTo(start + minY + 15, minY);
                path.lineTo(start + minX, minY + 15);
                path.lineTo(start + minX, minY);

                path.moveTo(start + minX + 30, minY);
                path.lineTo(start + minY + 45, minY);
                path.lineTo(start + minX, minY + 45);
                path.lineTo(start + minX, minY + 30);
                path.lineTo(start + minX + 30, minY);

                path.moveTo(start + minX + 60, minY);
                path.lineTo(start + minY + 60, minY + 15);
                path.lineTo(start + minX, minY + 75);
                path.lineTo(start + minX, minY + 60);
                path.lineTo(start + minX + 60, minY);

                path.moveTo(start + minX + 60, minY + 30);
                path.lineTo(start + minY + 60, minY + 45);
                path.lineTo(start + minX, minY + 105);
                path.lineTo(start + minX, minY + 90);
                path.lineTo(start + minX + 60, minY + 30);

                path.moveTo(start + minX + 60, minY + 60);
                path.lineTo(start + minY + 60, minY + 75);
                path.lineTo(start + minX + 15, minY + 120);
                path.lineTo(start + minX, minY + 120);
                path.lineTo(start + minX + 60, minY + 60);

                path.moveTo(start + minX + 60, minY + 90);
                path.lineTo(start + minY + 60, minY + 105);
                path.lineTo(start + minX + 45, minY + 120);
                path.lineTo(start + minX + 30, minY + 120);
                path.lineTo(start + minX + 60, minY + 90);
                break;
            case Dir.UP:
                break;
            case Dir.RIGHT:
                break;
            case Dir.DOWN:
                break;
        }
    }

}
