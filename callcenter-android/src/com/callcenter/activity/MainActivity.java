package com.callcenter.activity;

import android.os.Bundle;

import com.phonegap.DroidGap;

public class MainActivity extends DroidGap {
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadUrl("file:///android_asset/index.html");
    }
}