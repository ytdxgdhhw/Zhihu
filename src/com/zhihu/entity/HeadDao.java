package com.zhihu.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.entity.Homework2_entity;
import com.example.slidingmenuviewpager.Homework2_activity;
import com.example.slidingmenuviewpager.R;

public class HeadDao {
	public static List<Head> getNews(){
		List<Head> newslist=new ArrayList<Head>();
		Head news=new Head();
		newslist.add(new Head(R.drawable.wip_bk_na));
		newslist.add(new Head(R.drawable.wip_bk_fog));
		newslist.add(new Head(R.drawable.wip_bk_dust));
		newslist.add(new Head(R.drawable.wip_bk_snow));
		return newslist;
	}
}
