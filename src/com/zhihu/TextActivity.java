package com.zhihu;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.zhihu.application.ZhihuApplication;
import com.zhihu.entity.ListItem;
import com.zhihu.entity.Stories;
import com.zhihu.entity.Text;
import com.zhihu.utils.ImageLoaderUtil;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class TextActivity extends Activity implements OnClickListener {
	private ActionBar actionBar;
	private ImageView ivBack;
	private ImageView ivTop;
	private TextView tvText,tvCome;
	private int id;
	private WebView wbText;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_text);
		actionBar = super.getActionBar();
		actionBar.hide();
		//获得传递过来的id
		id = getIntent().getIntExtra("ID", -1);
		init();
		initdata();
	}

	private void initdata() {
		//解析具体的新闻
		// TODO Auto-generated method stub
		StringRequest request = new StringRequest(

		"http://news-at.zhihu.com/api/4/story/" + id,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						try {
							JSONObject jsonObject = new JSONObject(s);
								Text text = new Text();
								text.setTitle(jsonObject.getString("title"));
								text.setImage_source(jsonObject.getString("image_source"));
								text.setImage(jsonObject.getString("image"));
								text.setBody(jsonObject.getString("body"));
								ImageLoaderUtil.display(text.getImage(), ivTop);
								tvText.setText(text.getTitle());
								tvCome.setText(text.getImage_source());
								wbText.loadDataWithBaseURL(null, text.getBody(), "text/html", "utf-8",null);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						Toast.makeText(TextActivity.this, "网络加载失败！",
								Toast.LENGTH_SHORT).show();
					}
				});
		ZhihuApplication.getInstance().getRequestQueue().add(request);
	}

	private void init() {
		// TODO Auto-generated method stub
		ivBack = (ImageView) findViewById(R.id.ivBack);
		ivBack.setOnClickListener(this);
		ivTop=(ImageView)findViewById(R.id.ivTop);
		tvText=(TextView)findViewById(R.id.tvText);
		tvCome=(TextView)findViewById(R.id.tvCome);
		wbText=(WebView)findViewById(R.id.wvText);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.text, menu);
		return true;
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
