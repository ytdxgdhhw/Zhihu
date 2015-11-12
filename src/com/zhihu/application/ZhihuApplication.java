package com.zhihu.application;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class ZhihuApplication extends Application {
	private RequestQueue requestQueue;
	public static ZhihuApplication application;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		application = this;
		requestQueue = Volley.newRequestQueue(this);

		ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
	}

	public static ZhihuApplication getInstance() {
		return application;
	}

	public RequestQueue getRequestQueue() {
		return this.requestQueue;
	}

}
