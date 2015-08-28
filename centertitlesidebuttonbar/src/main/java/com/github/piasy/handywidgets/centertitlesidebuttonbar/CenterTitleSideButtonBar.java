package com.github.piasy.handywidgets.centertitlesidebuttonbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/27.
 */
public class CenterTitleSideButtonBar extends RelativeLayout implements View.OnClickListener {
    public CenterTitleSideButtonBar(@NonNull Context context) {
        this(context, null, 0);
    }

    public CenterTitleSideButtonBar(@NonNull Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CenterTitleSideButtonBar(@NonNull Context context, AttributeSet attrs, int
            defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getLayoutAttrs(context, attrs);
        initAttrs(context, attrs, defStyleAttr);
        initChild(context);
    }

    private int mLayoutHeight = 44;

    private void getLayoutAttrs(@NonNull Context context, AttributeSet attrs) {
        int[] systemAttrs = { android.R.attr.layout_height };
        TypedArray a = context.obtainStyledAttributes(attrs, systemAttrs);
        mLayoutHeight = a.getDimensionPixelSize(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        a.recycle();
    }

    private boolean mHasLeftButton = false;
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

    private boolean mHasTitle = true;
    private String mTitle = "";
    private
    @ColorInt
    int mTitleColor = 0xFF333333;
    private int mTitleSize = 20;
    private int mTitleGravity = 0;

    private boolean mHasDivider = false;
    private int mDividerHeight = 2;
    private
    @ColorInt
    int mDividerColor = 0x19FFFFFF;

    private void initAttrs(@NonNull Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.CenterTitleSideButtonBar, defStyleAttr,
                        0);

        mHasLeftButton = a.getBoolean(R.styleable.CenterTitleSideButtonBar_hasLeftButton, false);
        mLeftButtonAsText =
                a.getBoolean(R.styleable.CenterTitleSideButtonBar_leftButtonAsText, false);
        mLeftButtonText = a.getString(R.styleable.CenterTitleSideButtonBar_leftButtonText);
        mLeftButtonTextColor =
                a.getColorStateList(R.styleable.CenterTitleSideButtonBar_leftButtonTextColor);
        mLeftButtonTextSize =
                a.getDimensionPixelSize(R.styleable.CenterTitleSideButtonBar_leftButtonTextSize,
                        20);
        mLeftButtonSrc = a.getResourceId(R.styleable.CenterTitleSideButtonBar_leftButtonSrc, 0);
        mLeftButtonBg = a.getResourceId(R.styleable.CenterTitleSideButtonBar_leftButtonBg, 0);

        mHasRightButton = a.getBoolean(R.styleable.CenterTitleSideButtonBar_hasRightButton, false);
        mRightButtonAsText =
                a.getBoolean(R.styleable.CenterTitleSideButtonBar_rightButtonAsText, false);
        mRightButtonText = a.getString(R.styleable.CenterTitleSideButtonBar_rightButtonText);
        mRightButtonTextColor =
                a.getColorStateList(R.styleable.CenterTitleSideButtonBar_rightButtonTextColor);
        mRightButtonTextSize =
                a.getDimensionPixelSize(R.styleable.CenterTitleSideButtonBar_rightButtonTextSize,
                        20);
        mRightButtonSrc = a.getResourceId(R.styleable.CenterTitleSideButtonBar_rightButtonSrc, 0);
        mRightButtonBg = a.getResourceId(R.styleable.CenterTitleSideButtonBar_rightButtonBg, 0);

        mHasTitle = a.getBoolean(R.styleable.CenterTitleSideButtonBar_hasTitle, true);
        mTitle = a.getString(R.styleable.CenterTitleSideButtonBar_centerTitle);
        mTitleColor =
                a.getColor(R.styleable.CenterTitleSideButtonBar_centerTitleTextColor, 0xFF333333);
        mTitleSize =
                a.getDimensionPixelSize(R.styleable.CenterTitleSideButtonBar_centerTitleTextSize,
                        20);
        mTitleGravity =
                a.getInteger(R.styleable.CenterTitleSideButtonBar_centerTitleTextGravity, 0);

        mHasDivider = a.getBoolean(R.styleable.CenterTitleSideButtonBar_hasDivider, false);
        mDividerColor = a.getColor(R.styleable.CenterTitleSideButtonBar_dividerColor, 0x19FFFFFF);
        mDividerHeight =
                a.getDimensionPixelSize(R.styleable.CenterTitleSideButtonBar_dividerHeight, 2);

        a.recycle();
    }

    private ImageButton mLeftImageButton = null;
    private Button mLeftButton = null;
    private ImageButton mRightImageButton = null;
    private Button mRightButton = null;

    private TextView mTitleTextView = null;
    private View mDivider = null;

    private OnClickListener mLeftButtonClickListener;
    private OnClickListener mRightButtonClickListener;

    private void initChild(Context context) {
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
                params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                mLeftButton.setLayoutParams(params);
                if (mLeftButtonBg != 0) {
                    mLeftButton.setBackgroundResource(mLeftButtonBg);
                }
                mLeftButton.setText(mLeftButtonText);
                if (mLeftButtonTextColor != null) {
                    mLeftButton.setTextColor(mLeftButtonTextColor);
                }
                mLeftButton.setTextSize(mLeftButtonTextSize);
                mLeftButton.setOnClickListener(this);
                addView(mLeftButton);
            } else if (mLeftButtonBg != 0 || mLeftButtonSrc != 0) {
                mLeftImageButton = new ImageButton(context);
                mLeftImageButton.setLayoutParams(params);
                if (mLeftButtonBg != 0) {
                    mLeftImageButton.setBackgroundResource(mLeftButtonBg);
                }
                if (mLeftButtonSrc != 0) {
                    mLeftImageButton.setImageResource(mLeftButtonSrc);
                }
                mLeftImageButton.setOnClickListener(this);
                addView(mLeftImageButton);
            }
        }

        if (mHasTitle) {
            mTitleTextView = new TextView(context);
            LayoutParams params =
                    new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
            if (mLeftImageButton != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    params.addRule(END_OF, mLeftImageButton.getId());
                } else {
                    params.addRule(RIGHT_OF, mLeftImageButton.getId());
                }
            }
            if (mRightImageButton != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    params.addRule(START_OF, mRightImageButton.getId());
                } else {
                    params.addRule(LEFT_OF, mRightImageButton.getId());
                }
            }
            mTitleTextView.setLayoutParams(params);
            mTitleTextView.setText(mTitle);
            mTitleTextView.setTextSize(mTitleSize);
            mTitleTextView.setTextColor(mTitleColor);
            switch (mTitleGravity) {
                case 1:
                    // left
                    mTitleTextView.setGravity(
                            (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH
                                    ? Gravity.START : Gravity.LEFT) | Gravity.CENTER_VERTICAL);
                    break;
                case 2:
                    // right
                    mTitleTextView.setGravity(
                            (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH
                                    ? Gravity.END : Gravity.RIGHT) | Gravity.CENTER_VERTICAL);
                    break;
                case 0:
                    // center
                default:
                    mTitleTextView.setGravity(Gravity.CENTER);
                    break;
            }
            addView(mTitleTextView);
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
                params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
                mRightButton.setLayoutParams(params);
                if (mRightButtonBg != 0) {
                    mRightButton.setBackgroundResource(mRightButtonBg);
                }
                mRightButton.setText(mRightButtonText);
                if (mRightButtonTextColor != null) {
                    mRightButton.setTextColor(mRightButtonTextColor);
                }
                mRightButton.setTextSize(mRightButtonTextSize);
                mRightButton.setOnClickListener(this);
                mRightButton.setVisibility(GONE);
                addView(mRightButton);
            } else if (mRightButtonBg != 0 || mRightButtonSrc != 0) {
                mRightImageButton = new ImageButton(context);
                mRightImageButton.setLayoutParams(params);
                if (mRightButtonBg != 0) {
                    mRightImageButton.setBackgroundResource(mRightButtonBg);
                }
                if (mRightButtonSrc != 0) {
                    mRightImageButton.setImageResource(mRightButtonSrc);
                }
                mRightImageButton.setOnClickListener(this);
                mRightImageButton.setVisibility(GONE);
                addView(mRightImageButton);
            }
        }

        if (mHasDivider) {
            mDivider = new View(context);
            LayoutParams params;
            params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mDividerHeight);
            params.addRule(ALIGN_PARENT_BOTTOM);
            mDivider.setLayoutParams(params);
            mDivider.setBackgroundColor(mDividerColor);
            addView(mDivider);
        }
    }

    @Override
    public boolean isInEditMode() {
        return true;
    }

    @Override
    public void onClick(@NonNull View v) {
        if (mLeftButton != null && v == mLeftButton && mLeftButtonClickListener != null) {
            mLeftButtonClickListener.onClick(v);
        } else if (mLeftImageButton != null && v == mLeftImageButton &&
                mLeftButtonClickListener != null) {
            mLeftButtonClickListener.onClick(v);
        } else if (mRightButton != null && v == mRightButton && mRightButtonClickListener != null) {
            mRightButtonClickListener.onClick(v);
        } else if (mRightImageButton != null && v == mRightImageButton &&
                mRightButtonClickListener != null) {
            mRightButtonClickListener.onClick(v);
        }
    }

    public void setLeftButtonOnClickListener(OnClickListener listener) {
        mLeftButtonClickListener = listener;
    }

    public void setRightButtonOnClickListener(OnClickListener listener) {
        mRightButtonClickListener = listener;
    }

    public void showLeftButton() {
        if (mLeftButton != null) {
            mLeftButton.setVisibility(VISIBLE);
        }
        if (mLeftImageButton != null) {
            mLeftImageButton.setVisibility(VISIBLE);
        }
    }

    public void hideLeftButton() {
        if (mLeftButton != null) {
            mLeftButton.setVisibility(GONE);
        }
        if (mLeftImageButton != null) {
            mLeftImageButton.setVisibility(GONE);
        }
    }

    public void showRightButton() {
        if (mRightButton != null) {
            mRightButton.setVisibility(VISIBLE);
        }
        if (mRightImageButton != null) {
            mRightImageButton.setVisibility(VISIBLE);
        }
    }

    public void hideRightButton() {
        if (mRightButton != null) {
            mRightButton.setVisibility(GONE);
        }
        if (mRightImageButton != null) {
            mRightImageButton.setVisibility(GONE);
        }
    }

    public void setTitle(String title) {
        if (mTitleTextView != null) {
            mTitleTextView.setText(title);
            mTitle = title;
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
}
