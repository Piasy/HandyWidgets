package com.github.piasy.handywidgets;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.github.piasy.handywidgets.centertitlesidebuttonbar.CenterTitleSideButtonBar;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.mTitleBarFull)
    CenterTitleSideButtonBar mTitleBarFull;
    @Bind(R.id.mTitleBarDefaultHideRightButtonTitleGravityLeft)
    CenterTitleSideButtonBar mTitleBarDefaultHideRightButtonTitleGravityLeft;
    @Bind(R.id.mTitleBarDefaultNoLeftButtonTitleGravityLeft)
    CenterTitleSideButtonBar mTitleBarDefaultNoLeftButtonTitleGravityLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mTitleBarFull.disableRightButton();
        mTitleBarFull.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Left Button Clicked 1", Toast.LENGTH_SHORT)
                        .show();
            }
        });
        mTitleBarFull.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Right Button Clicked 1", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        mTitleBarDefaultHideRightButtonTitleGravityLeft.setLeftButtonOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "Left Button Clicked 2",
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
                        Toast.makeText(MainActivity.this, "Right Button Clicked 2",
                                Toast.LENGTH_SHORT).show();
                    }
                });

        mTitleBarDefaultNoLeftButtonTitleGravityLeft.setRightButtonOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "Right Button Clicked 3", Toast
                                .LENGTH_SHORT).show();
                    }
                });
    }
}
