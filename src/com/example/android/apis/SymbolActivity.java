package com.example.android.apis;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.heihei.util.Util;

public class SymbolActivity extends Activity {

    private TextView mTvSymbolDynamicPercent;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.symbol_activity);
        
        mTvSymbolDynamicPercent = (TextView) findViewById(R.id.symbol_dynamic_percent);
        mTvSymbolDynamicPercent.setText(getString(R.string.symbol_dynamic_percent, 10));
    }

    @Override
    public void onBackPressed() {
        Util.finishActivityAnim(this);
    }
    
}
