package com.zhihu.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhihu.R;
import com.zhihu.TextActivity;
import com.zhihu.entity.ListItem;
import com.zhihu.entity.Stories;
import com.zhihu.utils.ImageLoaderUtil;

public class NewAdapter extends BaseAdapter {
	private List<ListItem> list;
	private Context context;
	
	public NewAdapter(List<ListItem> list, Context context) {
		super();
		this.list = list;
		this.context = context;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ListItem item=list.get(arg0);
		ViewHolder holder=null;
		if(arg1==null){
			holder=new ViewHolder();
		
			arg1=LayoutInflater.from(context).inflate(R.layout.layout_item, null);	
			holder.ll=(LinearLayout)arg1;
			arg1.setTag(holder);
		}else{
			holder=(ViewHolder)arg1.getTag();
		}
		List<Stories> stories=item.getList();
		TextView date=(TextView)holder.ll.findViewById(R.id.textView);
		LinearLayout l=(LinearLayout)holder.ll.findViewById(R.id.container);
		date.setText(item.getDate());
		l.removeAllViews();

		for(int i=0;i<stories.size();i++){
			final Stories s=stories.get(i);
			View v=LayoutInflater.from(context).inflate(R.layout.news_layout, null);
			v.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(context,TextActivity.class);
					intent.putExtra("ID", s.getId());
					context.startActivity(intent);
				}
			});
			TextView tv=(TextView)v.findViewById(R.id.tvTitle);
			tv.setText(s.getTitle());
			ImageView iv=(ImageView)v.findViewById(R.id.ivTitle);
			String url = s.getImages().replace("\\", "");
			
			ImageLoaderUtil.display(url, iv);
			l.addView(v);
		}
		
		
		
		return arg1;
	}
	
	
	
	private static class ViewHolder{
		public LinearLayout ll;

	}
}
