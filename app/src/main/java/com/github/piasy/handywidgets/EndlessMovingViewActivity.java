package com.github.piasy.handywidgets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.piasy.handywidgets.endlessmovingview.EndlessMovingView;

/**
 * Created by Piasy{github.com/Piasy} on 15/9/1.
 */
public class EndlessMovingViewActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endless_moving_view);
        ButterKnife.bind(this);
    }

    @BindView(R.id.mEndlessMovingViewLeft)
    EndlessMovingView mEndlessMovingViewLeft;
    @BindView(R.id.mEndlessMovingViewRight)
    EndlessMovingView mEndlessMovingViewRight;
    @BindView(R.id.mEndlessMovingViewUp)
    EndlessMovingView mEndlessMovingViewUp;
    @BindView(R.id.mEndlessMovingViewDown)
    EndlessMovingView mEndlessMovingViewDown;

    @OnClick({
            R.id.mBtnStartEndlessMovingViewLeft, R.id.mBtnStopEndlessMovingViewLeft,
            R.id.mBtnStartEndlessMovingViewRight, R.id.mBtnStopEndlessMovingViewRight,
            R.id.mBtnStartEndlessMovingViewUp, R.id.mBtnStopEndlessMovingViewUp,
            R.id.mBtnStartEndlessMovingViewDown, R.id.mBtnStopEndlessMovingViewDown
    })
    public void onOpClicked(View v) {
        switch (v.getId()) {
            case R.id.mBtnStartEndlessMovingViewLeft:
                mEndlessMovingViewLeft.startMoving();
                break;
            case R.id.mBtnStopEndlessMovingViewLeft:
                mEndlessMovingViewLeft.stopMoving();
                break;
            case R.id.mBtnStartEndlessMovingViewRight:
                mEndlessMovingViewRight.startMoving();
                break;
            case R.id.mBtnStopEndlessMovingViewRight:
                mEndlessMovingViewRight.stopMoving();
                break;
            case R.id.mBtnStartEndlessMovingViewUp:
                mEndlessMovingViewUp.startMoving();
                break;
            case R.id.mBtnStopEndlessMovingViewUp:
                mEndlessMovingViewUp.stopMoving();
                break;
            case R.id.mBtnStartEndlessMovingViewDown:
                mEndlessMovingViewDown.startMoving();
                break;
            case R.id.mBtnStopEndlessMovingViewDown:
                mEndlessMovingViewDown.stopMoving();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, AnimateCompareActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
