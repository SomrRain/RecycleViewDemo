package com.example.cc.recycleviewdemo.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cc.recycleviewdemo.R;
import com.example.cc.recycleviewdemo.Util.XImageUtil;
import com.example.cc.recycleviewdemo.entity.NetEase;

import java.util.List;

/**
 * Created by CC on 2016/10/25.
 */

public class ViewPagerAdapter extends PagerAdapter {
    List<NetEase.Ad>  list;
    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }
    public ViewPagerAdapter(List<NetEase.Ad>  list){
        this.list=list;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view == object;
    }
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(container.getContext(), R.layout.layout_one_head, null);
        TextView tv = (TextView) view.findViewById(R.id.img_title);
        ImageView iv = (ImageView) view.findViewById(R.id.img_head);
        tv.setText(list.get(position%list.size()).title);
        XImageUtil.display(iv,list.get(position%list.size()).imgsrc);
        container.addView(view);


        return view;
    }
}
