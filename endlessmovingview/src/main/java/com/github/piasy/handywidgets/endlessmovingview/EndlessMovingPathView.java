/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Piasy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
     * @param start the start position
     * @param minX the min x coordinate of this view, always 0
     * @param maxX the max x coordinate of this view, always the width
     * @param minY the min y coordinate of this view, always 0
     * @param maxY the max y coordinate of this view, always the height
     */
    protected abstract void drawPath(Path path, @MovingDir int movingDir, int start, int minX,
            int maxX, int minY, int maxY);
}
