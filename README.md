# HandyWidgets
Handy Android widgets.

##CenterTitleSideButtonBar
[ ![Download](https://api.bintray.com/packages/piasy/maven/HandyWidgets/images/download.svg) ](https://bintray.com/piasy/maven/HandyWidgets/_latestVersion)  
Title bar with center title TextView, side(left & right) button and bottom divider.
Allow fully customization against the title bar. No more Google/StackOverFlow search for customize ActionBar/Toolbar again!

+  Screenshot  
![Screenshot_centertitlesidebuttonbar.jpg](art/Screenshot_centertitlesidebuttonbar.jpg)
+  Download
```groovy
    repositories {
        jcenter()
    }

    dependencies {
        compile 'com.github.piasy:centertitlesidebuttonbar:${latest version}'
    }
```
+  Usage  
In xml layout file:
```xml
    <com.github.piasy.handywidgets.centertitlesidebuttonbar.CenterTitleSideButtonBar
        xmlns:ctsbb="http://schemas.android.com/apk/res-auto"
        android:id="@+id/mTitleBarFull"
        android:layout_width="match_parent"
        android:layout_height="44dp"
        android:background="#05FFFFFF"
        ctsbb:hasLeftButton="true"
        ctsbb:leftButtonId="@+id/mTitleBarLeftButton"
        ctsbb:leftButtonShownDefault="true"
        ctsbb:leftButtonSrc="@drawable/iv_back"
        ctsbb:leftButtonBg="@drawable/btn_back_bg_selector"
        ctsbb:hasRightButton="true"
        ctsbb:rightButtonId="@+id/mTitleBarRightButton"
        ctsbb:rightButtonShownDefault="true"
        ctsbb:rightButtonAsText="true"
        ctsbb:rightButtonText="Right"
        ctsbb:rightButtonTextColor="@drawable/white_text_selector"
        ctsbb:rightButtonTextSize="9sp"
        ctsbb:rightButtonBg="@drawable/btn_back_bg_selector"
        ctsbb:hasTitle="true"
        ctsbb:titleId="@+id/mTitleBarTitle"
        ctsbb:centerTitleTextColor="@android:color/white"
        ctsbb:centerTitle="Title 1"
        ctsbb:centerTitleTextGravity="center"
        ctsbb:centerTitleTextSize="10sp"
        ctsbb:hasDivider="true"
        ctsbb:dividerId="@+id/mTitleBarDivider"
        ctsbb:dividerHeight="1dp"
        ctsbb:dividerColor="#19FFFFFF"
        />
```
In Java code:
```java
    CenterTitleSideButtonBar mTitleBarFull = (CenterTitleSideButtonBar) findViewById(R.id.mTitleBarFull);
    mTitleBarFull.showRightButton();
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
```
+  Customization
  +  hasTitle/hasLeftButton/hasRightButton/hasDivider
  +  leftButtonShownDefault/rightButtonShownDefault (show the button by defualt, or you can show/hide/enable/disable the button through `CenterTitleSideButtonBar`'s API)
  +  leftButtonAsText/rightButtonAsText
  +  leftButtonText/leftButtonTextSize/leftButtonTextColor (so does right button)
  +  leftButtonSrc (only for image button)
  +  leftButtonBg (both for Button & ImageButton, customize the state background for button)
  +  centerTitle (set title text)
  +  centerTitleTextSize/centerTitleTextColor/centerTitleTextGravity (gravity values are: left, right, center)
  +  dividerHeight/dividerColor
  +  leftButtonId/rightButtonId/titleId/dividerId (to align title to left/right, the button id must be set if the side button exist, and with the view ids, you can retrieve these views and do more customization as you can imagine)
  +  and more...
+  Todo
  +  shadow/elevation
  +  search bar
