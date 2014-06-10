
package com.example.android.apis;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.TextUtils.TruncateAt;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TestActivity extends Activity {

//    06-09 23:44:45.108 D/TestActivity(21272): beforeTextChanged:  start: 0 count: 0 after: 2
//    06-09 23:44:45.108 D/TestActivity(21272): onTextChanged: 策略 start: 0 before: 0 count: 2
//    06-09 23:44:45.148 D/TestActivity(21272): afterTextChanged: 策略
//    06-09 23:44:50.618 D/TestActivity(21272): beforeTextChanged: 策略  start: 0 count: 0 after: 2
//    06-09 23:44:50.618 D/TestActivity(21272): onTextChanged: 我去策略  start: 0 before: 0 count: 2
//    06-09 23:44:50.658 D/TestActivity(21272): afterTextChanged: 我去策略 
//    06-09 23:45:00.038 D/TestActivity(21272): beforeTextChanged: 策略 我去策略   start: 0 count: 0 after: 1
//    06-09 23:45:00.048 D/TestActivity(21272): onTextChanged: 胡策略 我去策略   start: 0 before: 0 count: 1
//    06-09 23:45:00.088 D/TestActivity(21272): afterTextChanged: 胡策略 我去策略  
    
    private static final String TAG = "TestActivity";

    private static final int REQ_TO_LOGIN = 10;
    private Button mBtn;

    public final static void startSelfForResult(Activity a, Intent intent, int reqCode) {
        intent.setClass(a, TestActivity.class);
        a.startActivityForResult(intent, reqCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);

        mBtn = (Button) findViewById(R.id.button1);
        mBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

        final EditText et = (EditText) findViewById(R.id.editText1);
//        String contactName = "联系人";
//        et.setText(contactName);
//        
//        SpannableStringBuilder sb = new SpannableStringBuilder();
//        addTag(contactName, sb);
//        et.getEditableText().replace(0, contactName.length(), sb);
        
        
//        addTag(contactName, sb);
//        addTag(contactName, sb);
        
        
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                    int count) {
                Log.d(TAG, "onTextChanged: " + s + " start: " + start + " before: " + before + " count: " + count);
                
                String str = s.toString();
                if (str.trim().length() > 0 && str.charAt(str.length() - 1) == SPE_CHA) {
                    str = str.subSequence(0, str.length() - 1).toString();
                    int iStart = str.lastIndexOf(SPE_CHA);
                    if (iStart == -1) iStart = 0;
                    CharSequence append = s.subSequence(iStart, str.length());
                    
                    Log.d(TAG, "onTextChanged 2: append: " + append);

                    et.removeTextChangedListener(this);
                    final SpannableStringBuilder sb = new SpannableStringBuilder();
                    addTag(append.toString(), sb);
                    et.getEditableText().replace(iStart, str.length(), sb);
                    et.getEditableText().append('\t');
                    et.addTextChangedListener(this);
                }
            }
            
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d(TAG, "beforeTextChanged: " + s + " start: " + start + " count: " + count + " after: " + after);
            }
            
            @Override
            public void afterTextChanged(Editable s) {
                Log.d(TAG, "afterTextChanged: " + s);
            }
        });
    }

    public static final char SPE_CHA = ' ';
    
    private void addTag(String contactName, final SpannableStringBuilder sb) {
        TextView tv = createContactTextView(contactName);
        BitmapDrawable bd = convertViewToDrawable(getResources(), tv);
        bd.setBounds(0, 0, bd.getIntrinsicWidth(), bd.getIntrinsicHeight());
        sb.append(contactName);
        sb.setSpan(new ImageSpan(bd), 0, sb.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public TextView createContactTextView(String text) {
        //creating textview dynamically
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setTextSize(13);
        tv.setSingleLine(true);
        tv.setEllipsize(TruncateAt.MIDDLE);
        tv.setMaxEms(6);
        tv.setBackgroundResource(R.drawable.oval);
        tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.close, 0);
        return tv;
    }

    public static BitmapDrawable convertViewToDrawable(Resources res, View view) {
        int spec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        view.measure(spec, spec);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        Bitmap b = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        c.translate(-view.getScrollX(), -view.getScrollY());
        view.draw(c);
        view.setDrawingCacheEnabled(true);
        Bitmap cacheBmp = view.getDrawingCache();
        Bitmap viewBmp = cacheBmp.copy(Bitmap.Config.ARGB_8888, true);
        view.destroyDrawingCache();
        return new BitmapDrawable(res, viewBmp);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: " + requestCode + " " + resultCode);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test, menu);
        return true;
    }

}
