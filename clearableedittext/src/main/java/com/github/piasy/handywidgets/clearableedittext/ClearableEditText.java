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

package com.github.piasy.handywidgets.clearableedittext;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.jakewharton.rxbinding.widget.RxTextView;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Piasy{github.com/Piasy} on 15/9/7.
 *
 * A widget that has functions of {@link EditText} and have a clear button. With full ability for
 * customization.
 */
public class ClearableEditText extends LinearLayout {

    private final boolean mIsEditTextAutoFocus;
    private final EditText mEditText;
    private final ImageButton mClearButton;

    private OnTextChangedListener mOnTextChangedListener;
    private Subscription mOnTextChangedSubscription;
    private boolean mIsEditorActionsMonitored = false;
    private OnEditorActionDoneListener mOnEditorActionDoneListener;
    private Subscription mEditorActionsSubscription;

    public ClearableEditText(Context context) {
        this(context, null);
    }

    public ClearableEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClearableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        TypedArray a = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.ClearableEditText, 0, 0);
        boolean hasSearchIcon = a.getBoolean(R.styleable.ClearableEditText_hasSearchIcon, false);
        int searchIconRes = a.getResourceId(R.styleable.ClearableEditText_searchIconRes, 0);
        int searchIconMarginLeft =
                a.getDimensionPixelSize(R.styleable.ClearableEditText_searchIconMarginLeft,
                        dip2px(10, context));
        int searchIconMarginRight =
                a.getDimensionPixelSize(R.styleable.ClearableEditText_searchIconMarginRight,
                        dip2px(10, context));

        int editTextColor =
                a.getColor(R.styleable.ClearableEditText_clearableEditTextColor, 0xFF000000);
        String editTextContent = a.getString(R.styleable.ClearableEditText_editTextContent);
        int editTextHintColor =
                a.getColor(R.styleable.ClearableEditText_clearableEditTextHintColor, 0xFF333333);
        String editTextHintContent = a.getString(R.styleable.ClearableEditText_editTextHintContent);
        float editTextSize = a.getDimension(R.styleable.ClearableEditText_editTextSize, 20);
        mIsEditTextAutoFocus = a.getBoolean(R.styleable.ClearableEditText_editTextAutoFocus, true);

        int clearIconRes = a.getResourceId(R.styleable.ClearableEditText_clearIconRes, 0);
        int clearIconMarginLeft =
                a.getDimensionPixelSize(R.styleable.ClearableEditText_searchIconMarginLeft,
                        dip2px(10, context));
        int clearIconMarginRight =
                a.getDimensionPixelSize(R.styleable.ClearableEditText_searchIconMarginRight,
                        dip2px(10, context));

        a.recycle();

        if (clearIconRes == 0) {
            throw new IllegalArgumentException("Clear icon resource must be set!");
        }

        if (hasSearchIcon) {
            LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            ImageView searchIcon = new ImageView(context);
            searchIcon.setImageResource(searchIconRes);
            params.leftMargin = searchIconMarginLeft;
            params.rightMargin = searchIconMarginRight;
            searchIcon.setLayoutParams(params);
            addView(searchIcon);
        }

        mEditText = new EditText(context);
        mEditText.setTextColor(editTextColor);
        mEditText.setHintTextColor(editTextHintColor);
        mEditText.setTextSize(TypedValue.COMPLEX_UNIT_PX, editTextSize);
        mEditText.setText(editTextContent);
        mEditText.setHint(editTextHintContent);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.width = 0;
        params.weight = 1;
        mEditText.setLayoutParams(params);
        mEditText.setBackgroundResource(0);
        addView(mEditText);

        mClearButton = new ImageButton(context);
        mClearButton.setBackgroundResource(0);
        LayoutParams params2 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params2.leftMargin = clearIconMarginLeft;
        params2.rightMargin = clearIconMarginRight;
        mClearButton.setLayoutParams(params2);
        mClearButton.setImageResource(clearIconRes);
        addView(mClearButton);
        mClearButton.setVisibility(INVISIBLE);

        initListener();
    }

    private void initListener() {
        mEditText.setSingleLine();
        mEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (v == mEditText && hasFocus && !TextUtils.isEmpty(mEditText.getText())) {
                    mClearButton.setVisibility(VISIBLE);
                } else {
                    mClearButton.setVisibility(INVISIBLE);
                }
            }
        });
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    mClearButton.setVisibility(VISIBLE);
                } else {
                    mClearButton.setVisibility(INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mClearButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == mClearButton) {
                    mEditText.setText("");
                }
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (mIsEditTextAutoFocus) {
            mEditText.requestFocus();
        } else {
            mEditText.clearFocus();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mOnTextChangedSubscription != null && !mOnTextChangedSubscription.isUnsubscribed()) {
            mOnTextChangedSubscription.unsubscribe();
        }
        mOnTextChangedSubscription = null;
        mOnTextChangedListener = null;

        if (mEditorActionsSubscription != null && !mEditorActionsSubscription.isUnsubscribed()) {
            mEditorActionsSubscription.unsubscribe();
        }
        mEditorActionsSubscription = null;
        mOnEditorActionDoneListener = null;
        mIsEditorActionsMonitored = false;
    }

    private int dip2px(int dipValue, @NonNull Context context) {
        final float reSize = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * reSize + 0.5);
    }

    /**
     * Create an observable of character sequences for text changes on {@code view}.
     * <p>
     * <em>Warning:</em> The created observable keeps a strong reference to {@code view}.
     * Unsubscribe
     * to free this reference.
     * <p>
     * <em>Note:</em> A value will be emitted immediately on subscribe.
     */
    @CheckResult
    @NonNull
    public Observable<CharSequence> textChanges() {
        return RxTextView.textChanges(mEditText);
    }

    /**
     * Set the Ime option for the {@link EditText} within it.
     *
     * @param option the option to set to {@link #mEditText}
     */
    public void setImeOptions(int option) {
        mEditText.setImeOptions(option);
    }

    /**
     * Get the Observable which emit the Editor Action on the EditText. To make this method works
     * properly, you should call {@link #setImeOptions(int)} before call this method.
     *
     * <p>
     * <em>Warning:</em> The created observable keeps a strong reference to {@code view}.
     * Unsubscribe to free this reference.
     * <p>
     * <p>
     * <em>Warning:</em> The editor action could be only notified by one listener, and if
     * a {@link OnEditorActionDoneListener} was set by {@link #setOnEditorActionDoneListener
     * (OnEditorActionDoneListener)} or an Observable is already get through {@link
     * #editorActions()}, an {@link IllegalStateException} will be thrown, to notify
     * this error as soon as possible.
     * <p>
     *
     * <em>Note:</em> the listener will no longer receive any event when the {@link
     * #onDetachedFromWindow()} is called. And it's free to set this listener/get Observable again.
     */
    @CheckResult
    @NonNull
    public Observable<Integer> editorActions() {
        if (mIsEditorActionsMonitored) {
            throw new IllegalStateException("The editor action has already been monitored!");
        } else {
            mIsEditorActionsMonitored = true;
        }
        return RxTextView.editorActions(mEditText);
    }

    /**
     * Set a listener to get notified when the text content change.
     *
     * <em>Note:</em> the listener will no longer receive any event when the {@link
     * #onDetachedFromWindow()} is called.
     *
     * @param onTextChangedListener the listener to get notified when the text content change.
     */
    public void setOnTextChangedListener(final OnTextChangedListener onTextChangedListener) {
        mOnTextChangedListener = onTextChangedListener;
        mOnTextChangedSubscription = textChanges().subscribe(new Subscriber<CharSequence>() {
            @Override
            public void onCompleted() { }

            @Override
            public void onError(Throwable e) { }

            @Override
            public void onNext(CharSequence charSequence) {
                if (mOnEditorActionDoneListener != null) {
                    mOnTextChangedListener.onTextChanged(charSequence);
                }
            }
        });
    }

    /**
     * Set a listener to get notified when the {@link EditorInfo#IME_ACTION_DONE} happen.
     *
     * <p>
     * <em>Warning:</em> The editor action could be only notified by one listener, and if
     * a {@link OnEditorActionDoneListener} was set by {@link #setOnEditorActionDoneListener
     * (OnEditorActionDoneListener)} or an Observable is already get through {@link
     * #editorActions()}, an {@link IllegalStateException} will be thrown, to notify
     * this error as soon as possible.
     * <p>
     *
     * <em>Note:</em> the listener will no longer receive any event when the {@link
     * #onDetachedFromWindow()} is called. And it's free to set this listener/get Observable again.
     *
     * @param onEditorActionDoneListener the listener to get notified when the {@link
     * EditorInfo#IME_ACTION_DONE} happen.
     */
    public void setOnEditorActionDoneListener(
            final OnEditorActionDoneListener onEditorActionDoneListener) {
        if (mIsEditorActionsMonitored) {
            throw new IllegalStateException("The editor action has already been monitored!");
        } else {
            mOnEditorActionDoneListener = onEditorActionDoneListener;
            mEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
            mEditorActionsSubscription = editorActions().subscribe(new Subscriber<Integer>() {
                @Override
                public void onCompleted() { }

                @Override
                public void onError(Throwable e) { }

                @Override
                public void onNext(Integer code) {
                    if (code == EditorInfo.IME_ACTION_DONE && mOnEditorActionDoneListener != null) {
                        mOnEditorActionDoneListener.onEditorActionDone();
                    }
                }
            });
        }
    }
}
