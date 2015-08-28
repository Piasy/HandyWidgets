package com.github.piasy.handywidgets;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.github.piasy.handywidgets.centertitlesidebuttonbar.CenterTitleSideButtonBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CenterTitleSideButtonBar titleBar1 = (CenterTitleSideButtonBar) findViewById(R.id.mTitleBar1);
        titleBar1.showRightButton();
        titleBar1.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Left Button Clicked 1", Toast.LENGTH_SHORT)
                        .show();
            }
        });
        titleBar1.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Right Button Clicked 1", Toast.LENGTH_SHORT)
                        .show();
            }
        });

        CenterTitleSideButtonBar titleBar2 = (CenterTitleSideButtonBar) findViewById(R.id.mTitleBar2);
        titleBar2.showRightButton();
        titleBar2.disableRightButton();
        titleBar2.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Left Button Clicked 2", Toast.LENGTH_SHORT).show();
            }
        });
        titleBar2.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Right Button Clicked 2", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}
