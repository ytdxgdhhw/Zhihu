package com.zhihu.utils;

import java.sql.SQLException;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.zhihu.entity.Stories;

public class ORMLiteOpenHelper extends OrmLiteSqliteOpenHelper {
	private static String DB_NAME="zhihu.db";//数据库名
	private static int DB_VERSION=1;//版本号
	public static ORMLiteOpenHelper helper;
	private  ORMLiteOpenHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		// TODO Auto-generated constructor stub
	}
	public static ORMLiteOpenHelper getInstance(Context context){
		synchronized(ORMLiteOpenHelper.class){
			if(helper==null){
				synchronized (ORMLiteOpenHelper.class) {
					if(helper==null){
						helper=new ORMLiteOpenHelper(context);
					}
				}
			}
		}
		return helper;
	}
	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
		// TODO Auto-generated method stub
		try {
			TableUtils.createTable(arg1, Stories.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub
	
	}
	private Dao<Stories, Integer> collectionDao=null;
	
	public Dao<Stories, Integer> getCollectionDao(){
		if(collectionDao==null){
			try {
				collectionDao=getDao(Stories.class);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return collectionDao;
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		super.close();
		collectionDao=null;
	}
	

}
