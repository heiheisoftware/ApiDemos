
package com.example.android.apis;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TestActivity4 extends Activity {
    private static final int RES_TO_LOGIN = 200;
    private static final int REQ_TO_LOGIN = 10;
    public final static void startSelfForResult(Activity a, Intent intent, int reqCode) {
        intent.setClass(a, TestActivity4.class);
        a.startActivityForResult(intent, reqCode);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_activity2);
        
        Button mBtn = (Button) findViewById(R.id.button1);
        mBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RES_TO_LOGIN);
                finish();
            }
        });
        
        TextView tv = (TextView) findViewById(R.id.text);
        tv.setText("这是4");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test_activity2, menu);
        return true;
    }

}
