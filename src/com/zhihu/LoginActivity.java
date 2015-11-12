package com.zhihu;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class LoginActivity extends Activity implements OnClickListener {
	private ImageView ivBack;
	private ActionBar actionBar;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		actionBar=super.getActionBar();
		actionBar.hide();
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		ivBack=(ImageView)findViewById(R.id.ivBack);
		ivBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.ivBack:
			finish();
			break;

		default:
			break;
		}
	}


}
