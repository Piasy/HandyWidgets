package com.github.piasy.handywidgets;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.github.piasy.handywidgets.centertitlesidebuttonbar.CenterTitleSideButtonBar;

public class CenterTitleSideButtonBarActivity extends Activity {

    @Bind(R.id.mTitleBarFull)
    CenterTitleSideButtonBar mTitleBarFull;
    @Bind(R.id.mTitleBarDefaultHideRightButtonTitleGravityLeft)
    CenterTitleSideButtonBar mTitleBarDefaultHideRightButtonTitleGravityLeft;
    @Bind(R.id.mTitleBarDefaultNoLeftButtonTitleGravityLeft)
    CenterTitleSideButtonBar mTitleBarDefaultNoLeftButtonTitleGravityLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_title_side_button_bar);
        ButterKnife.bind(this);

        mTitleBarFull.disableRightButton();
        mTitleBarFull.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CenterTitleSideButtonBarActivity.this, "Left Button Clicked 1", Toast.LENGTH_SHORT)
                        .show();
            }
        });
        mTitleBarFull.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CenterTitleSideButtonBarActivity.this, "Right Button Clicked 1", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        mTitleBarDefaultHideRightButtonTitleGravityLeft.setLeftButtonOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CenterTitleSideButtonBarActivity.this, "Left Button Clicked 2",
                                Toast.LENGTH_SHORT).show();
                        if (mTitleBarDefaultHideRightButtonTitleGravityLeft.rightButtonShown()) {
                            mTitleBarDefaultHideRightButtonTitleGravityLeft.hideRightButton();
                        } else {
                            mTitleBarDefaultHideRightButtonTitleGravityLeft.showRightButton();
                        }
                    }
                });
        mTitleBarDefaultHideRightButtonTitleGravityLeft.setRightButtonOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CenterTitleSideButtonBarActivity.this, "Right Button Clicked 2",
                                Toast.LENGTH_SHORT).show();
                    }
                });

        mTitleBarDefaultNoLeftButtonTitleGravityLeft.setRightButtonOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CenterTitleSideButtonBarActivity.this, "Right Button Clicked 3", Toast
                                .LENGTH_SHORT).show();
                    }
                });
    }
}
