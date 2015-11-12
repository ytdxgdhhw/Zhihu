package com.zhihu;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.j256.ormlite.dao.Dao;
import com.slidingmenu.lib.SlidingMenu;
import com.zhihu.adapter.HeadAdapter;
import com.zhihu.adapter.NewAdapter;
import com.zhihu.application.ZhihuApplication;
import com.zhihu.entity.Head;
import com.zhihu.entity.HeadDao;
import com.zhihu.entity.ListItem;
import com.zhihu.entity.Stories;
import com.zhihu.utils.ORMLiteOpenHelper;

public class MainActivity extends Activity implements OnClickListener,
		OnRefreshListener2<ListView> {
	private SlidingMenu slidingMenu;
	private ImageView ivLog;
	private ListView lv;
	private NewAdapter nAdapter;
	private List<Stories> data;
	private ActionBar actionBar;
	private List<ListItem> listItem;
	private String strDate;
	private PullToRefreshListView listView;
	private Dao<Stories, Integer> storyDao;
	private long endTime;// 退出时间
	private int date;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		actionBar = super.getActionBar();
		actionBar.hide();
		storyDao=ORMLiteOpenHelper.getInstance(this).getCollectionDao();
		initSlidingMenu();

		init();
		initHead();
	}

	private List<Head> headList;
	private ViewPager vpHead;
	private RadioGroup rgDot;
	private HeadAdapter headAdapter;

	private void initHead() {
		// TODO Auto-generated method stub
		lv = listView.getRefreshableView();
		View view = LayoutInflater.from(this).inflate(R.layout.headview_layout,
				null);
		vpHead = (ViewPager) view.findViewById(R.id.vpImg);
		rgDot = (RadioGroup) view.findViewById(R.id.radioGroup1);
		//radioGroup与viewpager联动
		rgDot.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				vpHead.setCurrentItem(arg1);
			}
		});
		headList = new ArrayList<Head>();
		headAdapter = new HeadAdapter(this, headList);
		vpHead.setAdapter(headAdapter);
	
		loadHeadData();
		for (int i = 0; i < 4; i++) {
			RadioButton v = (RadioButton) LayoutInflater.from(this).inflate(
					R.layout.radiobutton, null);
			v.setId(i);
			rgDot.addView(v);
		}
		rgDot.check(0);

		vpHead.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int idx) {
				// TODO Auto-generated method stub
				rgDot.check(idx);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

		lv.addHeaderView(view);
		
	}

	private void loadHeadData() {
		// TODO Auto-generated method stub
		headList.addAll(HeadDao.getNews());
		headAdapter.notifyDataSetChanged();
	}

	private void init() {
		// TODO Auto-generated method stub
		listItem = new ArrayList<ListItem>();
		ivLog = (ImageView) findViewById(R.id.ivLog);
		ivLog.setOnClickListener(this);
		listView = (PullToRefreshListView) findViewById(R.id.listView);
		listView.setMode(Mode.BOTH);// 选择模式
		listView.setOnRefreshListener(this);// 注册侦听
		listView.setRefreshing(true);// 一开始刷新
		data = new ArrayList<Stories>();
		nAdapter = new NewAdapter(listItem, this);
		listView.setAdapter(nAdapter);
		loadData();

	}

	private void loadData() {
		// 加载网络数据并解析
		StringRequest request = new StringRequest(

		"http://news-at.zhihu.com/api/4/stories/latest",
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						try {
							ListItem item = new ListItem();

							JSONObject jsonObject = new JSONObject(s);
							strDate = jsonObject.getString("date");
							item.setDate(strDate);
							JSONArray jsonArray = jsonObject
									.getJSONArray("stories");
							int length = jsonArray.length();
							List<Stories> l = new ArrayList<Stories>();
							for (int i = 0; i < length; i++) {

								JSONObject object = jsonArray.getJSONObject(i);
								Stories stories = new Stories();
								stories.setGa_prefix(object
										.getString("ga_prefix"));
								stories.setId(object.getInt("id"));

								stories.setImages(object.getJSONArray("images")
										.getString(0));
								stories.setTitle(object.getString("title"));
								stories.setType(object.getInt("type"));
								l.add(stories);
								try {
									storyDao.createIfNotExists(stories);
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							item.setList(l);
							listItem.add(item);
							nAdapter.notifyDataSetChanged();
							date = Integer.parseInt(listItem.get(0).getDate());
							// listView.onRefreshComplete();
							ReloadData(Integer.parseInt(listItem.get(0)
									.getDate()));
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						Toast.makeText(MainActivity.this, "网络加载失败！",
								Toast.LENGTH_SHORT).show();
					}
				});
		ZhihuApplication.getInstance().getRequestQueue().add(request);

	}

	private void ReloadData(int d) {
		// 加载网络数据并解析

		StringRequest request = new StringRequest(

		"http://news-at.zhihu.com/api/4/stories/before/" + d,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String s) {
						try {
							ListItem item = new ListItem();

							JSONObject jsonObject = new JSONObject(s);

							item.setDate(jsonObject.getString("date"));
							JSONArray jsonArray = jsonObject
									.getJSONArray("stories");
							int length = jsonArray.length();
							List<Stories> l = new ArrayList<Stories>();
							for (int i = 0; i < length; i++) {

								JSONObject object = jsonArray.getJSONObject(i);
								Stories stories = new Stories();
								stories.setGa_prefix(object
										.getString("ga_prefix"));
								stories.setId(object.getInt("id"));

								stories.setImages(object.getJSONArray("images")
										.getString(0));
								stories.setTitle(object.getString("title"));
								stories.setType(object.getInt("type"));
								l.add(stories);
								
							}
							item.setList(l);
							listItem.add(item);
							nAdapter.notifyDataSetChanged();
							listView.onRefreshComplete();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {
						Toast.makeText(MainActivity.this, "网络加载失败！",
								Toast.LENGTH_SHORT).show();
					}
				});
		ZhihuApplication.getInstance().getRequestQueue().add(request);

	}

	// 侧边栏
	private void initSlidingMenu() {
		// TODO Auto-generated method stub
		slidingMenu = new SlidingMenu(this);
		slidingMenu.setMode(SlidingMenu.LEFT);
		slidingMenu.setMenu(R.layout.sliding_layout);
		slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_left_offset);
		slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		slidingMenu.setTouchModeAbove(slidingMenu.TOUCHMODE_MARGIN);// 边缘模式

	}

	// 退出设置
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			long currentTime = System.currentTimeMillis();
			if (currentTime - endTime > 2000) {
				Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
				endTime = System.currentTimeMillis();

			} else {
				System.exit(0);
			}
		}

		return false;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.ivLog:
			Intent intent = new Intent(MainActivity.this, LoginActivity.class);
			startActivity(intent);
			break;
		}
	}

	// 下拉刷新数据
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		listItem.clear();
		loadData();

	}

	// 上拉加载更多
	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
		// TODO Auto-generated method stub
		date--;
		ReloadData(date);

	}

	

}
