package com.github.piasy.handywidgets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Piasy{github.com/Piasy} on 15/9/1.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.mBtnCenterTitleSideButtonBar)
    public void centerTitleSIdeButtonBar() {
        startActivity(new Intent(this, CenterTitleSideButtonBarActivity.class));
    }

    @OnClick(R.id.mBtnEndlessMovingView)
    public void endlessMovingView() {
        startActivity(new Intent(this, EndlessMovingViewActivity.class));
    }

    @OnClick(R.id.mBtnClearableEditText)
    public void clearableEditText() {
        startActivity(new Intent(this, ClearableEditTextActivity.class));
    }

}
