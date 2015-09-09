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

    @Bind(R.id.mEtUsername)
    ClearableEditText mEtUsername;
    @Bind(R.id.mEtPassword)
    ClearableEditText mEtPassword;

    @Bind(R.id.mTvInputUsername)
    TextView mTvInputUsername;
    @Bind(R.id.mTvInputPassword)
    TextView mTvInputPassword;
    @Bind(R.id.mTvAction)
    TextView mTvAction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clearable_edit_text);
        ButterKnife.bind(this);

        mEtUsername.textChanges().subscribe(new Action1<CharSequence>() {
            @Override
            public void call(CharSequence charSequence) {
                mTvInputUsername.setText("Username is: " + charSequence);
            }
        });
        mEtUsername.setImeOptions(EditorInfo.IME_ACTION_DONE);
        mEtUsername.editorActions().subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer code) {
                Log.d("ClearableEditText", "ClearableEditText Action: " + code);
                if (code == EditorInfo.IME_ACTION_DONE) {
                    mEtPassword.requestFocusOnEditText();
                }
            }
        });

        mEtPassword.setOnTextChangedListener(new OnTextChangedListener() {
            @Override
            public void onTextChanged(CharSequence text) {
                mTvInputPassword.setText("Password is: " + text);
            }
        });
        mEtPassword.setOnEditorActionDoneListener(new OnEditorActionDoneListener() {
            @Override
            public void onEditorActionDone() {
                mTvAction.setText("IME_ACTION_DONE invoked");
            }
        });

        mTvAction.setText("Action done not invoked");
    }
}
