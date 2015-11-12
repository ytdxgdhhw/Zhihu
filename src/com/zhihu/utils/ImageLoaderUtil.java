package com.zhihu.utils;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.zhihu.R;

public class ImageLoaderUtil {
	private static DisplayImageOptions options =new DisplayImageOptions.Builder()
	 //���ع�������ʾ��ͼƬ
    .showImageOnLoading(R.drawable.cc_default_news_img)
           //����ʧ��ʱ��ʾ��ͼƬ
    .showImageOnFail(R.drawable.cc_default_news_img_fail)
          //uriΪ�յ�ʱ����ʾ��ͼƬ
    .showImageForEmptyUri(R.drawable.cc_default_news_img)
           //�Ƿ�����ڴ滺��
    .cacheInMemory(true)
            //
    .cacheOnDisk(true)
            //
    .bitmapConfig(Bitmap.Config.RGB_565)
    .resetViewBeforeLoading(true)
    .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
    .displayer(new FadeInBitmapDisplayer(200))
            //RoundedBitmapDisplayer
    .build();



public static void display(String url,ImageView imageView){
    ImageLoader
            .getInstance().displayImage(url, imageView, options);
}
}
