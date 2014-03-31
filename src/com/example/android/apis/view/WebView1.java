/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.apis.view;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

import com.example.android.apis.R;

import java.io.IOException;
import java.io.InputStream;


/**
 * Sample creating 1 webviews.
 */
public class WebView1 extends Activity {
    
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        
        setContentView(R.layout.webview_1);
        
        final String mimeType = "text/html";
        
        WebView localString = (WebView) findViewById(R.id.local_string);
        localString.loadData("<a href='x'>Hello World! - 1</a>", mimeType, null);
        
        WebView localHtml = (WebView) findViewById(R.id.local_html);
        loadLocalHtml(localHtml);
    }

    private void loadLocalHtml(WebView localHtml) {
        String htmlContent = null;
        InputStream is = null;
        try {
            is = getAssets().open("retry.html");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            
            // Convert the buffer into a string.
            htmlContent = new String(buffer);
        } catch (IOException e) {
            // Should never happen!
            throw new RuntimeException(e);
        } finally {
            if (is != null) try { is.close(); } catch (IOException e) { e.printStackTrace(); } 
        }
        
        // 使用 webView.loadData(htmlContent, "text/html", "UTF-8")中文会出现乱码
        localHtml.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);
    }
}
