package com.example.android.apis.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.apis.R;

public class ToastDemoActivity extends Activity implements View.OnClickListener {

    private Button mBtnToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.toast_demo);

        mBtnToast = (Button) findViewById(R.id.toast);
        mBtnToast.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.toast:
            custToastAlignView(mBtnToast);
            break;
        default:
            break;
        }
    }

    public static int getStatusBarHeight(Context c) {
        int result = 0;
        int resourceId = c.getResources().getIdentifier("status_bar_height",
                "dimen", "android");
        if (resourceId > 0) {
            result = c.getResources().getDimensionPixelSize(resourceId);
        } else {
            result = c.getResources().getDimensionPixelSize(R.dimen.test_dimen_25);
        }
        return result;
    }

    /**
     * 演示同View高度齐平的Toast
     * @param v
     */
    private void custToastAlignView(View v) {
        Rect r = new Rect();
        v.requestRectangleOnScreen(r, true);
        Toast t = Toast.makeText(this, "自定义Toast位置", Toast.LENGTH_LONG);
        t.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP, 0, 
                r.top - getStatusBarHeight(this)/* 这里面的Top是要剔除状态栏的高度 */);
        t.getView().setBackgroundColor(Color.GRAY);
        t.show();
    }
}
