
package com.example.android.apis;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TestActivity3 extends Activity {
    
    private static final String TAG = "TestActivity3";
    
    private static final int REQ_TO_LOGIN = 10;
    public final static void startSelfForResult(Activity a, Intent intent, int reqCode) {
        intent.setClass(a, TestActivity3.class);
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
                Intent intent = new Intent();
                TestActivity4.startSelfForResult(TestActivity3.this, intent, REQ_TO_LOGIN);
            }
        });
        
        TextView tv = (TextView) findViewById(R.id.text);
        tv.setText("这是3");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: " + requestCode + " " + resultCode);
        setResult(resultCode);
        finish();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test_activity2, menu);
        return true;
    }

}
