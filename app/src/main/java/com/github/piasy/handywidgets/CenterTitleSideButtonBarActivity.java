package com.github.piasy.handywidgets;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.github.piasy.handywidgets.centertitlesidebuttonbar.CenterTitleSideButtonBar;
import com.github.piasy.handywidgets.clearableedittext.OnEditorActionDoneListener;
import com.trello.rxlifecycle.components.RxActivity;
import rx.functions.Action1;

public class CenterTitleSideButtonBarActivity extends RxActivity {

    @Bind(R.id.mTitleBarFull)
    CenterTitleSideButtonBar mTitleBarFull;
    @Bind(R.id.mTitleBarDefaultHideRightButtonTitleGravityLeft)
    CenterTitleSideButtonBar mTitleBarDefaultHideRightButtonTitleGravityLeft;
    @Bind(R.id.mTitleBarSearch)
    CenterTitleSideButtonBar mTitleBarSearch;
    @Bind(R.id.mTvSearchQuery)
    TextView mTvSearchQuery;
    @Bind(R.id.mTvEditorAction)
    TextView mTvEditorAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center_title_side_button_bar);
        ButterKnife.bind(this);

        mTitleBarFull.disableRightButton();
        mTitleBarFull.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CenterTitleSideButtonBarActivity.this, "Left Button Clicked 1",
                        Toast.LENGTH_SHORT).show();
            }
        });
        mTitleBarFull.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CenterTitleSideButtonBarActivity.this, "Right Button Clicked 1",
                        Toast.LENGTH_SHORT).show();
            }
        });

        mTitleBarDefaultHideRightButtonTitleGravityLeft.setLeftButtonOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(CenterTitleSideButtonBarActivity.this,
                                "Left Button Clicked 2", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(CenterTitleSideButtonBarActivity.this,
                                "Right Button Clicked 2", Toast.LENGTH_SHORT).show();
                    }
                });

        mTitleBarSearch.searchQueryChanges()
                .compose(this.<CharSequence>bindToLifecycle())
                .subscribe(new Action1<CharSequence>() {
                    @Override
                    public void call(CharSequence query) {
                        mTvSearchQuery.setText("Search Query: " + query);
                    }
                });
        mTitleBarSearch.setOnEditorActionDoneListener(new OnEditorActionDoneListener() {
            @Override
            public void onEditorActionDone() {
                mTvEditorAction.setText("ACTION_DONE detected!");
            }
        });
    }
}
