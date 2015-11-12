package com.zhihu;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {
	private ActionBar actionBar;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		actionBar=super.getActionBar();
		actionBar.hide();
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		  new Handler().postDelayed(new Runnable() {
	            @Override
	            public void run() {
	                Intent intent =new Intent(SplashActivity.this,MainActivity.class);
	                startActivity(intent);
	                finish();
	            }
	        },5000);
	}


}
