package com.example.android.apis.view;

import android.app.Activity;
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
//                  TODO: 自定义Toast位置
                  Rect r = new Rect();
                  mBtnToast.requestRectangleOnScreen(r, true);
                  Toast t = Toast.makeText(this, "自定义Toast位置", Toast.LENGTH_LONG);
                  t.setGravity(Gravity. CENTER_HORIZONTAL | Gravity.TOP, 0, r.top );
                  t.getView().setBackgroundColor(Color.GRAY);
                  t.show();
                break;
            default:
                break;
        }
    }
}
