package com.zhihu.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zhihu.R;
import com.zhihu.entity.Head;



public class HeadAdapter extends PagerAdapter {
	private Context context;
	private List<Head> data;
	public HeadAdapter(Context context, List<Head> data) {
		super();
		this.context = context;
		this.data = data;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0==arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView((View)object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		Head list=data.get(position);
		View convertView=new View(context);
		convertView=LayoutInflater.from(context).inflate(R.layout.head_layout, null);
		ImageView ivTu=(ImageView)convertView.findViewById(R.id.ivTu);
		ivTu.setImageResource(list.getImgRes());
		container.addView(convertView);
		return convertView;
	}

}
