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

package com.github.piasy.handywidgets.centertitlesidebuttonbar;

import com.github.piasy.handywidgets.clearableedittext.ClearableEditText;
import com.github.piasy.handywidgets.clearableedittext.OnEditorActionDoneListener;
import com.github.piasy.handywidgets.clearableedittext.OnTextChangedListener;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Slide;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Looper;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/27.
 */
public final class CenterTitleSideButtonBar extends RelativeLayout implements View.OnClickListener {

    private int mLayoutHeight = 44;

    private boolean mHasLeftButton = false;

    private int mLeftButtonId = -1;

    private boolean mLeftButtonShownDefault = true;

    private boolean mLeftButtonAsText = false;

    private String mLeftButtonText = "Left";

    private ColorStateList mLeftButtonTextColor = null;

    private int mLeftButtonTextSize = 20;

    private
    @DrawableRes
    int mLeftButtonSrc = 0;

    private
    @DrawableRes
    int mLeftButtonBg = 0;

    private boolean mHasRightButton = false;

    private int mRightButtonId = -1;

    private boolean mRightButtonShownDefault = false;

    private boolean mRightButtonAsText = false;

    private String mRightButtonText = "Right";

    private ColorStateList mRightButtonTextColor = null;

    private int mRightButtonTextSize = 20;

    private
    @DrawableRes
    int mRightButtonSrc = 0;

    private
    @DrawableRes
    int mRightButtonBg = 0;

    private int mCloseSearchViewId = -1;

    private boolean mRightButtonAsSearchView = false;

    private boolean mRightButtonClickToSearch = true;

    private boolean mSearchViewDefaultShown = false;

    private
    @DrawableRes
    int mSearchViewBg = 0;

    private int mSearchViewHeight = ViewGroup.LayoutParams.MATCH_PARENT;

    private int mSearchViewMarginLeft = 0;

    private int mSearchViewMarginRight = 0;

    private String mCloseSearchViewText = "";

    private ColorStateList mCloseSearchViewTextColor;

    private int mCloseSearchViewTextSize = 20;

    private boolean mHasTitle = true;

    private int mTitleId = -1;

    private String mTitle = "";

    private
    @ColorInt
    int mTitleColor = 0xFF333333;

    private int mTitleSize = 20;

    private int mTitleGravity = 0;

    private boolean mHasDivider = false;

    private int mDividerId = -1;

    private int mDividerHeight = 2;

    private
    @ColorInt
    int mDividerColor = 0x19FFFFFF;

    private ImageButton mLeftImageButton = null;

    private Button mLeftButton = null;

    private ImageButton mRightImageButton = null;

    private Button mRightButton = null;

    private TextView mTitleTextView = null;

    private ClearableEditText mClearableEditText;

    private Button mCloseSearchButton;

    private View mDivider = null;

    private OnClickListener mLeftButtonClickListener;

    private OnClickListener mRightButtonClickListener;

    public CenterTitleSideButtonBar(@NonNull Context context) {
        this(context, null, 0);
    }

    public CenterTitleSideButtonBar(@NonNull Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CenterTitleSideButtonBar(@NonNull Context context, AttributeSet attrs,
            int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getLayoutAttrs(context, attrs);
        initAttrs(context, attrs, defStyleAttr);
        initChild(context, attrs, defStyleAttr);
    }

    private void getLayoutAttrs(@NonNull Context context, AttributeSet attrs) {
        int[] systemAttrs = {android.R.attr.layout_height};
        TypedArray a = context.obtainStyledAttributes(attrs, systemAttrs);
        mLayoutHeight = a.getDimensionPixelSize(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        a.recycle();
    }

    private void initAttrs(@NonNull Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.CenterTitleSideButtonBar, defStyleAttr,
                        0);

        mHasLeftButton = a.getBoolean(R.styleable.CenterTitleSideButtonBar_hasLeftButton, false);
        mLeftButtonId = a.getResourceId(R.styleable.CenterTitleSideButtonBar_leftButtonId, -1);
        mLeftButtonShownDefault =
                a.getBoolean(R.styleable.CenterTitleSideButtonBar_leftButtonShownDefault, true);
        mLeftButtonAsText =
                a.getBoolean(R.styleable.CenterTitleSideButtonBar_leftButtonAsText, false);
        mLeftButtonText = a.getString(R.styleable.CenterTitleSideButtonBar_leftButtonText);
        mLeftButtonTextColor =
                a.getColorStateList(R.styleable.CenterTitleSideButtonBar_leftButtonTextColor);
        mLeftButtonTextSize =
                (int) a.getDimension(R.styleable.CenterTitleSideButtonBar_leftButtonTextSize, 20);
        mLeftButtonSrc = a.getResourceId(R.styleable.CenterTitleSideButtonBar_leftButtonSrc, 0);
        mLeftButtonBg = a.getResourceId(R.styleable.CenterTitleSideButtonBar_leftButtonBg, 0);

        mHasRightButton = a.getBoolean(R.styleable.CenterTitleSideButtonBar_hasRightButton, false);
        mRightButtonId = a.getResourceId(R.styleable.CenterTitleSideButtonBar_rightButtonId, -1);
        mRightButtonShownDefault =
                a.getBoolean(R.styleable.CenterTitleSideButtonBar_rightButtonShownDefault, false);
        mRightButtonAsText =
                a.getBoolean(R.styleable.CenterTitleSideButtonBar_rightButtonAsText, false);
        mRightButtonText = a.getString(R.styleable.CenterTitleSideButtonBar_rightButtonText);
        mRightButtonTextColor =
                a.getColorStateList(R.styleable.CenterTitleSideButtonBar_rightButtonTextColor);
        mRightButtonTextSize =
                (int) a.getDimension(R.styleable.CenterTitleSideButtonBar_rightButtonTextSize, 20);
        mRightButtonSrc = a.getResourceId(R.styleable.CenterTitleSideButtonBar_rightButtonSrc, 0);
        mRightButtonBg = a.getResourceId(R.styleable.CenterTitleSideButtonBar_rightButtonBg, 0);
        mRightButtonAsSearchView =
                a.getBoolean(R.styleable.CenterTitleSideButtonBar_rightButtonAsSearchView, false);
        mRightButtonClickToSearch = a
                .getBoolean(R.styleable.CenterTitleSideButtonBar_rightButtonClickToSearch, true);
        if (mRightButtonAsSearchView) {
            mCloseSearchViewId =
                    a.getResourceId(R.styleable.CenterTitleSideButtonBar_closeSearchViewId, -1);
            mSearchViewDefaultShown =
                    a.getBoolean(R.styleable.CenterTitleSideButtonBar_searchViewDefaultShown,
                            false);
            mSearchViewBg = a.getResourceId(R.styleable.CenterTitleSideButtonBar_searchViewBg, 0);
            mSearchViewHeight =
                    a.getDimensionPixelSize(R.styleable.CenterTitleSideButtonBar_searchViewHeight,
                            ViewGroup.LayoutParams.MATCH_PARENT);
            mSearchViewMarginLeft = a.getDimensionPixelSize(
                    R.styleable.CenterTitleSideButtonBar_searchViewMarginLeft, 0);
            mSearchViewMarginRight = a.getDimensionPixelSize(
                    R.styleable.CenterTitleSideButtonBar_searchViewMarginRight, 0);
            mCloseSearchViewText =
                    a.getString(R.styleable.CenterTitleSideButtonBar_closeSearchViewText);
            mCloseSearchViewTextColor = a.getColorStateList(
                    R.styleable.CenterTitleSideButtonBar_closeSearchViewTextColor);
            mCloseSearchViewTextSize = (int) a.getDimension(
                    R.styleable.CenterTitleSideButtonBar_closeSearchViewTextSize, 20);
        }

        mHasTitle = a.getBoolean(R.styleable.CenterTitleSideButtonBar_hasTitle, true);
        mTitleId = a.getResourceId(R.styleable.CenterTitleSideButtonBar_titleId, -1);
        mTitle = a.getString(R.styleable.CenterTitleSideButtonBar_centerTitle);
        mTitleColor =
                a.getColor(R.styleable.CenterTitleSideButtonBar_centerTitleTextColor, 0xFF333333);
        mTitleSize =
                (int) a.getDimension(R.styleable.CenterTitleSideButtonBar_centerTitleTextSize, 20);
        mTitleGravity =
                a.getInteger(R.styleable.CenterTitleSideButtonBar_centerTitleTextGravity, 0);

        mHasDivider = a.getBoolean(R.styleable.CenterTitleSideButtonBar_hasDivider, false);
        mDividerId = a.getResourceId(R.styleable.CenterTitleSideButtonBar_dividerId, -1);
        mDividerColor = a.getColor(R.styleable.CenterTitleSideButtonBar_dividerColor, 0x19FFFFFF);
        mDividerHeight =
                a.getDimensionPixelSize(R.styleable.CenterTitleSideButtonBar_dividerHeight, 2);

        a.recycle();
    }

    private void initChild(Context context, AttributeSet attrs, int defStyleAttr) {
        if (mHasLeftButton) {
            LayoutParams params;
            if (mLayoutHeight != ViewGroup.LayoutParams.WRAP_CONTENT) {
                params = new LayoutParams(mLayoutHeight, mLayoutHeight);
            } else {
                params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            params.addRule(CENTER_VERTICAL);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                params.addRule(ALIGN_PARENT_START);
            } else {
                params.addRule(ALIGN_PARENT_LEFT);
            }
            if (mLeftButtonAsText) {
                mLeftButton = new Button(context);
                mLeftButton.setId(mLeftButtonId);
                params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                mLeftButton.setLayoutParams(params);
                if (mLeftButtonBg != 0) {
                    mLeftButton.setBackgroundResource(mLeftButtonBg);
                }
                mLeftButton.setText(mLeftButtonText);
                if (mLeftButtonTextColor != null) {
                    mLeftButton.setTextColor(mLeftButtonTextColor);
                }
                mLeftButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, mLeftButtonTextSize);
                mLeftButton.setOnClickListener(this);
                if (!mLeftButtonShownDefault) {
                    mLeftButton.setVisibility(INVISIBLE);
                    mIsLeftButtonShown = false;
                }
                addView(mLeftButton);
            } else if (mLeftButtonBg != 0 || mLeftButtonSrc != 0) {
                mLeftImageButton = new ImageButton(context);
                mLeftImageButton.setId(mLeftButtonId);
                mLeftImageButton.setLayoutParams(params);
                if (mLeftButtonBg != 0) {
                    mLeftImageButton.setBackgroundResource(mLeftButtonBg);
                }
                if (mLeftButtonSrc != 0) {
                    mLeftImageButton.setImageResource(mLeftButtonSrc);
                }
                mLeftImageButton.setOnClickListener(this);
                if (!mLeftButtonShownDefault) {
                    mLeftImageButton.setVisibility(INVISIBLE);
                    mIsLeftButtonShown = false;
                }
                addView(mLeftImageButton);
            }
        }

        if (mHasRightButton) {
            LayoutParams params;
            if (mLayoutHeight != ViewGroup.LayoutParams.WRAP_CONTENT) {
                params = new LayoutParams(mLayoutHeight, mLayoutHeight);
            } else {
                params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            params.addRule(CENTER_VERTICAL);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                params.addRule(ALIGN_PARENT_END);
            } else {
                params.addRule(ALIGN_PARENT_RIGHT);
            }
            if (mRightButtonAsText) {
                mRightButton = new Button(context);
                mRightButton.setId(mRightButtonId);
                params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                mRightButton.setLayoutParams(params);
                if (mRightButtonBg != 0) {
                    mRightButton.setBackgroundResource(mRightButtonBg);
                }
                mRightButton.setText(mRightButtonText);
                if (mRightButtonTextColor != null) {
                    mRightButton.setTextColor(mRightButtonTextColor);
                }
                mRightButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, mRightButtonTextSize);
                mRightButton.setOnClickListener(this);
                if (!mRightButtonShownDefault) {
                    mRightButton.setVisibility(INVISIBLE);
                    mIsRightButtonShown = false;
                }
                addView(mRightButton);
            } else if (mRightButtonBg != 0 || mRightButtonSrc != 0) {
                mRightImageButton = new ImageButton(context);
                mRightImageButton.setId(mRightButtonId);
                mRightImageButton.setLayoutParams(params);
                if (mRightButtonBg != 0) {
                    mRightImageButton.setBackgroundResource(mRightButtonBg);
                }
                if (mRightButtonSrc != 0) {
                    mRightImageButton.setImageResource(mRightButtonSrc);
                }
                mRightImageButton.setOnClickListener(this);
                if (!mRightButtonShownDefault) {
                    mRightImageButton.setVisibility(INVISIBLE);
                    mIsRightButtonShown = false;
                }
                addView(mRightImageButton);
            }
        }

        if (mHasTitle) {
            mTitleTextView = new TextView(context);
            mTitleTextView.setId(mTitleId);
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            if (mLeftImageButton != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    params.addRule(END_OF, mLeftImageButton.getId());
                } else {
                    params.addRule(RIGHT_OF, mLeftImageButton.getId());
                }
            } else if (mLeftButton != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    params.addRule(END_OF, mLeftButton.getId());
                } else {
                    params.addRule(RIGHT_OF, mLeftButton.getId());
                }
            }
            if (mRightImageButton != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    params.addRule(START_OF, mRightImageButton.getId());
                } else {
                    params.addRule(LEFT_OF, mRightImageButton.getId());
                }
            } else if (mRightButton != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    params.addRule(START_OF, mRightButton.getId());
                } else {
                    params.addRule(LEFT_OF, mRightButton.getId());
                }
            }
            mTitleTextView.setLayoutParams(params);
            mTitleTextView.setText(mTitle);
            mTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTitleSize);
            mTitleTextView.setTextColor(mTitleColor);
            if (mTitleGravity == 1 && ((mLeftButton == null && mLeftImageButton == null) ||
                    ((mLeftButton != null || mLeftImageButton != null) && mLeftButtonId != -1))) {
                // left
                mTitleTextView.setGravity(
                        (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH ?
                                Gravity.START : Gravity.LEFT) | Gravity.CENTER_VERTICAL);
            } else if (mTitleGravity == 2 && ((mRightButton == null && mRightImageButton == null) ||
                    ((mRightButton != null || mRightImageButton != null) &&
                            mRightButtonId != -1))) {
                // right
                mTitleTextView.setGravity(
                        (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH ?
                                Gravity.END : Gravity.RIGHT) | Gravity.CENTER_VERTICAL);
            } else {
                mTitleTextView.setGravity(Gravity.CENTER);
            }

            mTitleTextView.setMaxLines(1);
            mTitleTextView.setEllipsize(TextUtils.TruncateAt.END);

            addView(mTitleTextView);
        }

        if (mHasDivider) {
            mDivider = new View(context);
            mDivider.setId(mDividerId);
            LayoutParams params;
            params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mDividerHeight);
            params.addRule(ALIGN_PARENT_BOTTOM);
            mDivider.setLayoutParams(params);
            mDivider.setBackgroundColor(mDividerColor);
            addView(mDivider);
        }

        if (mRightButtonAsSearchView) {
            mClearableEditText = new ClearableEditText(context, attrs, defStyleAttr);
            mClearableEditText.setBackgroundResource(mSearchViewBg);
            mCloseSearchButton = new Button(context);
            mCloseSearchButton.setId(mCloseSearchViewId);
            LayoutParams params1 =
                    new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mSearchViewHeight);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                params1.addRule(START_OF, mCloseSearchButton.getId());
            } else {
                params1.addRule(LEFT_OF, mCloseSearchButton.getId());
            }
            params1.leftMargin = mSearchViewMarginLeft;
            params1.rightMargin = mSearchViewMarginRight;
            params1.addRule(CENTER_VERTICAL);
            mClearableEditText.setLayoutParams(params1);
            mCloseSearchButton.setText(mCloseSearchViewText);
            mCloseSearchButton.setTextColor(mCloseSearchViewTextColor);
            mCloseSearchButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, mCloseSearchViewTextSize);
            LayoutParams params;
            if (mLayoutHeight != ViewGroup.LayoutParams.WRAP_CONTENT) {
                params = new LayoutParams(mLayoutHeight, mLayoutHeight);
            } else {
                params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            params.addRule(CENTER_VERTICAL);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                params.addRule(ALIGN_PARENT_END);
            } else {
                params.addRule(ALIGN_PARENT_RIGHT);
            }
            params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            mCloseSearchButton.setLayoutParams(params);
            mCloseSearchButton.setBackgroundResource(0);

            if (mSearchViewDefaultShown) {
                showSearchView();
            } else {
                mClearableEditText.setVisibility(GONE);
                mCloseSearchButton.setVisibility(GONE);
            }
            mCloseSearchButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    hideSearchView();
                }
            });
            addView(mClearableEditText);
            addView(mCloseSearchButton);
        }
    }

    public void showSearchView() {
        setEnterSearchAnimation();
        if (mLeftButton != null) {
            mLeftButton.setVisibility(GONE);
        }
        if (mLeftImageButton != null) {
            mLeftImageButton.setVisibility(GONE);
        }
        if (mRightButton != null) {
            mRightButton.setVisibility(GONE);
        }
        if (mRightImageButton != null) {
            mRightImageButton.setVisibility(GONE);
        }
        if (mTitleTextView != null) {
            mTitleTextView.setVisibility(GONE);
        }

        mClearableEditText.showKeyboard();
        mClearableEditText.setVisibility(VISIBLE);
        mCloseSearchButton.setVisibility(VISIBLE);
        if (mSearchViewVisibilityChangedListener != null) {
            mSearchViewVisibilityChangedListener.onSearchViewShown();
        }
    }

    private void setEnterSearchAnimation() {
        TransitionSet transitionSet = new TransitionSet();
        if (mClearableEditText != null) {
            transitionSet.addTransition(
                    new Slide(Gravity.TOP).addTarget(mClearableEditText).setDuration(150));
        }
        if (mCloseSearchButton != null) {
            transitionSet.addTransition(new Fade(Fade.IN).addTarget(mCloseSearchButton));
        }
        if (mLeftButton != null) {
            transitionSet.addTransition(new Fade(Fade.OUT).addTarget(mLeftButton));
        }
        if (mLeftImageButton != null) {
            transitionSet.addTransition(new Fade(Fade.OUT).addTarget(mLeftImageButton));
        }
        if (mTitleTextView != null) {
            transitionSet.addTransition(new Fade(Fade.OUT).addTarget(mTitleTextView).setDuration(
                    150));
        }
        if (mRightButton != null) {
            transitionSet.addTransition(new Fade(Fade.OUT).addTarget(mRightButton));
        }
        if (mRightImageButton != null) {
            transitionSet.addTransition(new Fade(Fade.OUT).addTarget(mRightImageButton));
        }

        TransitionManager.beginDelayedTransition(this, transitionSet);
    }

    public void hideSearchView() {
        setLeaveSearchAnimation();
        if (mIsLeftButtonShown) {
            if (mLeftButton != null) {
                mLeftButton.setVisibility(VISIBLE);
            } else if (mLeftImageButton != null) {
                mLeftImageButton.setVisibility(VISIBLE);
            }
        }

        if (mIsRightButtonShown) {
            if (mRightButton != null) {
                mRightButton.setVisibility(VISIBLE);
            } else if (mRightImageButton != null) {
                mRightImageButton.setVisibility(VISIBLE);
            }
        }

        if (mTitleTextView != null) {
            mTitleTextView.setVisibility(VISIBLE);
        }

        mClearableEditText.hideKeyboard();
        mClearableEditText.setText("");
        mClearableEditText.setVisibility(GONE);
        mCloseSearchButton.setVisibility(GONE);
        if (mSearchViewVisibilityChangedListener != null) {
            mSearchViewVisibilityChangedListener.onSearchViewHidden();
        }
    }

    private void setLeaveSearchAnimation() {
        TransitionManager.beginDelayedTransition(this);
    }

    /**
     * Listener to get notified when search view's visibility changed.
     * */
    public interface SearchViewVisibilityChangedListener {

        /**
         * search view is shown.
         * */
        void onSearchViewShown();

        /**
         * search view is hidden.
         * */
        void onSearchViewHidden();
    }

    private SearchViewVisibilityChangedListener mSearchViewVisibilityChangedListener;

    /**
     * set listener to get notified when search view's visibility changed.
     *
     * @param listener SearchViewVisibilityChangedListener
     * */
    public void setSearchViewVisibilityChangedListener(
            SearchViewVisibilityChangedListener listener) {
        mSearchViewVisibilityChangedListener = listener;
    }

    /**
     * get notified with the popular rx way.
     *
     * @return the rx Observable that notify search view visibility change, emit {@code true}
     * when search view is shown, emit {@code false} when search view is hidden.
     * */
    public Observable<Boolean> searchViewVisibilityChanged() {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(final Subscriber<? super Boolean> subscriber) {
                mSearchViewVisibilityChangedListener = new SearchViewVisibilityChangedListener() {
                    @Override
                    public void onSearchViewShown() {
                        subscriber.onNext(true);
                    }

                    @Override
                    public void onSearchViewHidden() {
                        subscriber.onNext(false);
                    }
                };

                subscriber.add(unsubscribeInUiThread(new Action0() {
                    @Override
                    public void call() {
                        mSearchViewVisibilityChangedListener = null;
                    }
                }));
            }
        });
    }

    private Subscription unsubscribeInUiThread(final Action0 unsubscribe) {
        return Subscriptions.create(new Action0() {

            @Override
            public void call() {
                if (Looper.getMainLooper() == Looper.myLooper()) {
                    unsubscribe.call();
                } else {
                    final Scheduler.Worker inner = AndroidSchedulers.mainThread().createWorker();
                    inner.schedule(new Action0() {
                        @Override
                        public void call() {
                            unsubscribe.call();
                            inner.unsubscribe();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onClick(@NonNull View v) {
        if (mLeftButton != null && v == mLeftButton && mLeftButtonClickListener != null) {
            mLeftButtonClickListener.onClick(v);
        } else if (mLeftImageButton != null && v == mLeftImageButton &&
                mLeftButtonClickListener != null) {
            mLeftButtonClickListener.onClick(v);
        } else if (mRightButton != null && v == mRightButton) {
            if (mRightButtonClickListener != null) {
                mRightButtonClickListener.onClick(v);
            }
            if (mRightButtonAsSearchView && mRightButtonClickToSearch) {
                if (mClearableEditText.getVisibility() == VISIBLE) {
                    hideSearchView();
                } else {
                    showSearchView();
                }
            }
        } else if (mRightImageButton != null && v == mRightImageButton) {
            if (mRightButtonClickListener != null) {
                mRightButtonClickListener.onClick(v);
            }
            if (mRightButtonAsSearchView && mRightButtonClickToSearch) {
                if (mClearableEditText.getVisibility() == VISIBLE) {
                    hideSearchView();
                } else {
                    showSearchView();
                }
            }
        }
    }

    public void setLeftButtonOnClickListener(OnClickListener listener) {
        mLeftButtonClickListener = listener;
    }

    public void setRightButtonOnClickListener(OnClickListener listener) {
        mRightButtonClickListener = listener;
    }

    public void showLeftButton() {
        mIsLeftButtonShown = true;
        if (mLeftButton != null) {
            mLeftButton.setVisibility(VISIBLE);
        }
        if (mLeftImageButton != null) {
            mLeftImageButton.setVisibility(VISIBLE);
        }
    }

    private boolean mIsLeftButtonShown = true;

    public void hideLeftButton() {
        mIsLeftButtonShown = false;
        if (mLeftButton != null) {
            mLeftButton.setVisibility(GONE);
        }
        if (mLeftImageButton != null) {
            mLeftImageButton.setVisibility(GONE);
        }
    }

    public boolean leftButtonShown() {
        return (mLeftButton != null && mLeftButton.getVisibility() == VISIBLE) ||
                (mLeftImageButton != null && mLeftImageButton.getVisibility() == VISIBLE);
    }

    private boolean mIsRightButtonShown = true;

    public void showRightButton() {
        mIsRightButtonShown = true;
        if (mRightButton != null) {
            mRightButton.setVisibility(VISIBLE);
        }
        if (mRightImageButton != null) {
            mRightImageButton.setVisibility(VISIBLE);
        }
    }

    public void hideRightButton() {
        mIsRightButtonShown = false;
        if (mRightButton != null) {
            mRightButton.setVisibility(GONE);
        }
        if (mRightImageButton != null) {
            mRightImageButton.setVisibility(GONE);
        }
    }

    public boolean rightButtonShown() {
        return (mRightButton != null && mRightButton.getVisibility() == VISIBLE) ||
                (mRightImageButton != null && mRightImageButton.getVisibility() == VISIBLE);
    }

    public void setTitle(String title) {
        if (mTitleTextView != null) {
            mTitleTextView.setText(title);
            mTitle = title;
        }
    }

    public void enableLeftButton() {
        if (mLeftButton != null) {
            mLeftButton.setEnabled(true);
        }
        if (mLeftImageButton != null) {
            mLeftImageButton.setEnabled(true);
        }
    }

    public void disableLeftButton() {
        if (mLeftButton != null) {
            mLeftButton.setEnabled(false);
        }
        if (mLeftImageButton != null) {
            mLeftImageButton.setEnabled(false);
        }
    }

    public void enableRightButton() {
        if (mRightButton != null) {
            mRightButton.setEnabled(true);
        }
        if (mRightImageButton != null) {
            mRightImageButton.setEnabled(true);
        }
    }

    public void disableRightButton() {
        if (mRightButton != null) {
            mRightButton.setEnabled(false);
        }
        if (mRightImageButton != null) {
            mRightImageButton.setEnabled(false);
        }
    }

    public Observable<CharSequence> searchQueryChanges() {
        if (mClearableEditText == null) {
            throw new IllegalStateException("No search view configured!");
        }
        return mClearableEditText.textChanges();
    }

    public Observable<Integer> editorActions() {
        if (mClearableEditText == null) {
            throw new IllegalStateException("No search view configured!");
        }
        return mClearableEditText.editorActions();
    }

    public void setOnQueryChangedListener(OnTextChangedListener onQueryChangedListener) {
        if (mClearableEditText == null) {
            throw new IllegalStateException("No search view configured!");
        }
        mClearableEditText.setOnTextChangedListener(onQueryChangedListener);
    }

    public void setOnEditorActionDoneListener(
            OnEditorActionDoneListener onEditorActionDoneListener) {
        if (mClearableEditText == null) {
            throw new IllegalStateException("No search view configured!");
        }
        mClearableEditText.setOnEditorActionDoneListener(onEditorActionDoneListener);
    }
}
