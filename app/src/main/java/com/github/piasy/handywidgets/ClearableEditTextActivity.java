package com.github.piasy.handywidgets;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.github.piasy.handywidgets.clearableedittext.ClearableEditText;
import com.github.piasy.handywidgets.clearableedittext.OnEditorActionDoneListener;
import com.github.piasy.handywidgets.clearableedittext.OnTextChangedListener;
import com.jakewharton.rxbinding.widget.RxTextView;
import rx.functions.Action1;

/**
 * Created by Piasy{github.com/Piasy} on 15/9/1.
 */
public class ClearableEditTextActivity extends Activity {

    @Bind(R.id.mTvInput)
    TextView mTvInput;
    @Bind(R.id.mTvAction)
    TextView mTvAction;
    @Bind(R.id.mClearableEditText)
    ClearableEditText mClearableEditText;

    @Bind(R.id.mTvInput2)
    TextView mTvInput2;
    @Bind(R.id.mTvAction2)
    TextView mTvAction2;
    @Bind(R.id.mClearableEditText2)
    ClearableEditText mClearableEditText2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clearable_edit_text);
        ButterKnife.bind(this);

        mClearableEditText.textChanges().subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                mTvInput.setText("Input is: " + charSequence);
            }
        });
        mClearableEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mClearableEditText.editorActions().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer code) {
                Log.d("ClearableEditText", "ClearableEditText Action: " + code);
                if (code == EditorInfo.IME_ACTION_DONE) {
                    mTvAction.setText("IME_ACTION_DONE detected");
                }
            }
        });
        mTvAction.setText("Action is: ?");

        mClearableEditText2.setOnTextChangedListener(new OnTextChangedListener() {
            @Override
            public void onTextChanged(CharSequence text) {
                mTvInput2.setText("Input is: " + text);
            }
        });
        mClearableEditText2.setOnEditorActionDoneListener(new OnEditorActionDoneListener() {
            @Override
            public void onEditorActionDone() {
                mTvAction2.setText("IME_ACTION_DONE detected");
            }
        });
        mTvAction2.setText("Action is: ?");
    }
}
