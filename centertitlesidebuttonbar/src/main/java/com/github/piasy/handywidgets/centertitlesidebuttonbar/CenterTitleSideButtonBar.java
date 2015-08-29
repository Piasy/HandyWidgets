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
        initChild(context);
    }

    private void getLayoutAttrs(@NonNull Context context, AttributeSet attrs) {
        int[] systemAttrs = { android.R.attr.layout_height };
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
                a.getDimensionPixelSize(R.styleable.CenterTitleSideButtonBar_leftButtonTextSize,
                        20);
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
                a.getDimensionPixelSize(R.styleable.CenterTitleSideButtonBar_rightButtonTextSize,
                        20);
        mRightButtonSrc = a.getResourceId(R.styleable.CenterTitleSideButtonBar_rightButtonSrc, 0);
        mRightButtonBg = a.getResourceId(R.styleable.CenterTitleSideButtonBar_rightButtonBg, 0);

        mHasTitle = a.getBoolean(R.styleable.CenterTitleSideButtonBar_hasTitle, true);
        mTitleId = a.getResourceId(R.styleable.CenterTitleSideButtonBar_titleId, -1);
        mTitle = a.getString(R.styleable.CenterTitleSideButtonBar_centerTitle);
        mTitleColor =
                a.getColor(R.styleable.CenterTitleSideButtonBar_centerTitleTextColor, 0xFF333333);
        mTitleSize =
                a.getDimensionPixelSize(R.styleable.CenterTitleSideButtonBar_centerTitleTextSize,
                        20);
        mTitleGravity =
                a.getInteger(R.styleable.CenterTitleSideButtonBar_centerTitleTextGravity, 0);

        mHasDivider = a.getBoolean(R.styleable.CenterTitleSideButtonBar_hasDivider, false);
        mDividerId = a.getResourceId(R.styleable.CenterTitleSideButtonBar_dividerId, -1);
        mDividerColor = a.getColor(R.styleable.CenterTitleSideButtonBar_dividerColor, 0x19FFFFFF);
        mDividerHeight =
                a.getDimensionPixelSize(R.styleable.CenterTitleSideButtonBar_dividerHeight, 2);

        a.recycle();
    }

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
                mLeftButton.setTextSize(mLeftButtonTextSize);
                mLeftButton.setOnClickListener(this);
                if (!mLeftButtonShownDefault) {
                    mLeftButton.setVisibility(INVISIBLE);
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
                mRightButton.setTextSize(mRightButtonTextSize);
                mRightButton.setOnClickListener(this);
                if (!mRightButtonShownDefault) {
                    mRightButton.setVisibility(INVISIBLE);
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
            mTitleTextView.setTextSize(mTitleSize);
            mTitleTextView.setTextColor(mTitleColor);
            if (mTitleGravity == 1 && ((mLeftButton == null && mLeftImageButton == null) ||
                    ((mLeftButton != null || mLeftImageButton != null) && mLeftButtonId != -1))) {
                // left
                mTitleTextView.setGravity(
                        (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH
                                ? Gravity.START : Gravity.LEFT) | Gravity.CENTER_VERTICAL);
            } else if (mTitleGravity == 2 && ((mRightButton == null && mRightImageButton == null) ||
                    ((mRightButton != null || mRightImageButton != null) &&
                            mRightButtonId != -1))) {
                // right
                mTitleTextView.setGravity(
                        (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH
                                ? Gravity.END : Gravity.RIGHT) | Gravity.CENTER_VERTICAL);
            } else {
                mTitleTextView.setGravity(Gravity.CENTER);
            }

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

    public boolean leftButtonShown() {
        return (mLeftButton != null && mLeftButton.getVisibility() == VISIBLE) ||
                (mLeftImageButton != null && mLeftImageButton.getVisibility() == VISIBLE);
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
}
