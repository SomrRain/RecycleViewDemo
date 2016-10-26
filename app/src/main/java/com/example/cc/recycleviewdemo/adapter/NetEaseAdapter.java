package com.example.cc.recycleviewdemo.adapter;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cc.recycleviewdemo.R;
import com.example.cc.recycleviewdemo.Util.XImageUtil;
import com.example.cc.recycleviewdemo.entity.NetEase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by CC on 2016/10/24.
 */
public class NetEaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<NetEase> dataList;
    private static final int VIEW_VIEWPAGER = 364;
    private static final int VIEW_LONG_IMAGE = 123;
    private static final int VIEW_ONE_IMAGE = 36424;
    private static final int VIEW_THREE_IMAGE = 351264;
    private static final int VIEW_FOOTER = 36644;
    boolean go = true;
    List<Integer>  add=new ArrayList<Integer>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case VIEW_ONE_IMAGE:
                view = View.inflate(parent.getContext(), R.layout.layout_item_one_img, null);
                holder = new OneImageHolder(view);
                break;
            case VIEW_VIEWPAGER:
                view = View.inflate(parent.getContext(), R.layout.layout_viewpager, null);
                holder = new ViewPagerHolder(view);
                break;
            case VIEW_LONG_IMAGE:
                view = View.inflate(parent.getContext(), R.layout.layout_long_image, null);
                holder = new LongImageHolder(view);
                break;
            case VIEW_THREE_IMAGE:
                view = View.inflate(parent.getContext(), R.layout.layout_three_image, null);
                holder = new ThreeImageHolder(view);
                break;
            case VIEW_FOOTER:
                view = View.inflate(parent.getContext(), R.layout.layout_footer, null);
                holder = new FooterHolder(view);
                break;
//            default:
//                break;
        }
        return holder;
    }

    public int getItemViewType(int position) {
        if (position == 0) {
            return dataList.get(position).ads == null ? VIEW_ONE_IMAGE : VIEW_VIEWPAGER;
        } else if (position < dataList.size()) {
            return dataList.get(position).imgextra == null ? VIEW_ONE_IMAGE : VIEW_THREE_IMAGE;

        } else {
            return VIEW_FOOTER;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OneImageHolder) {
            OneImageHolder h = (OneImageHolder) holder;
            NetEase netEase = dataList.get(position);
            XImageUtil.display(h.layoutImgIv, netEase.imgsrc);
            h.layoutImgTittle.setText(netEase.title);
            h.layoutImgFollow.setText(netEase.replyCount + "");
        } else if (holder instanceof ThreeImageHolder) {
           final ThreeImageHolder h = (ThreeImageHolder) holder;
            NetEase netEase = dataList.get(position);
            XImageUtil.display(h.layoutThreeIv1, netEase.imgsrc);
            XImageUtil.display(h.layoutThreeIv2, netEase.imgextra.get(0).imgsrc);
            XImageUtil.display(h.layoutThreeIv3, netEase.imgextra.get(1).imgsrc);
            h.layoutThreeTitle.setText(netEase.title);
            h.layoutThreeTv2.setText(netEase.replyCount + "");
                h.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(h.itemView.getContext(), "1111", Toast.LENGTH_SHORT).show();
                    }
                });

        } else if (holder instanceof LongImageHolder) {
            LongImageHolder h = (LongImageHolder) holder;
            NetEase netEase = dataList.get(position);
            h.layoutLongTitle.setText(netEase.title);
            XImageUtil.display(h.layoutLongIv, netEase.imgsrc);
            h.layoutLongTv2.setText(netEase.replyCount + "");
        } else if (holder instanceof ViewPagerHolder) {
            int number = dataList.get(position).ads.size() * 1000;
            if(add!=null){
                for (int i=0;i<add.size();i++){
                    number+=add.get(i);
                }
            }

            initViewPagerView((ViewPagerHolder) holder, dataList.get(position), number);
        }
    }

    private void initViewPagerView(final ViewPagerHolder holder, NetEase netEase, final int number) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(netEase.ads);
        holder.layoutViewpagerVp.setAdapter(adapter);
        holder.layoutViewpagerVp.setCurrentItem(number);
        if (go) {
            for (int i = 0; i < netEase.ads.size(); i++) {
                ImageView img = new ImageView(holder.layoutViewpagerLl.getContext());
                img.setImageResource(R.drawable.adware_style_default);
                holder.layoutViewpagerLl.addView(img);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) img.getLayoutParams();
                layoutParams.leftMargin = 5;
                layoutParams.rightMargin = 5;
            }
            go = false;
            ((ImageView) (holder.layoutViewpagerLl.getChildAt(number % netEase.ads.size()))).setImageResource(R.drawable.adware_style_selected);
        }

//      ((ImageView) (holder.layoutViewpagerLl.getChildAt(number % netEase.ads.size()))).setImageResource(R.drawable.adware_style_selected);
        holder.layoutViewpagerVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < holder.layoutViewpagerLl.getChildCount(); i++) {
                    ImageView img = (ImageView) holder.layoutViewpagerLl.getChildAt(i);
                    img.setImageResource(R.drawable.adware_style_default);
                }
                ImageView img = (ImageView) holder.layoutViewpagerLl.getChildAt(position % holder.layoutViewpagerLl.getChildCount());
                img.setImageResource(R.drawable.adware_style_selected);

            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        int position=holder.layoutViewpagerVp.getChildCount();
        add.add(position-number);

    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public NetEaseAdapter(List<NetEase> dataList) {
        this.dataList = dataList;
    }

    public List<NetEase> getDataList() {
        return dataList;
    }

    public void setDataList(List<NetEase> dataList) {
        this.dataList = dataList;
    }

    public class OneImageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.layout_img_iv)
        ImageView layoutImgIv;
        @BindView(R.id.layout_img_tittle)
        TextView layoutImgTittle;
        @BindView(R.id.layout_img_follow)
        TextView layoutImgFollow;

        public OneImageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public class ViewPagerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.layout_viewpager_vp)
        ViewPager layoutViewpagerVp;
        @BindView(R.id.layout_viewpager_ll)
        LinearLayout layoutViewpagerLl;

        public ViewPagerHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class LongImageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.layout_long_title)
        TextView layoutLongTitle;
        @BindView(R.id.layout_long_iv)
        ImageView layoutLongIv;
        @BindView(R.id.layout_long_tv2)
        TextView layoutLongTv2;

        public LongImageHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class ThreeImageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.layout_three_title)
        TextView layoutThreeTitle;
        @BindView(R.id.layout_three_iv1)
        ImageView layoutThreeIv1;
        @BindView(R.id.layout_three_iv2)
        ImageView layoutThreeIv2;
        @BindView(R.id.layout_three_iv3)
        ImageView layoutThreeIv3;
        @BindView(R.id.layout_three_tv2)
        TextView layoutThreeTv2;

        public ThreeImageHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public class FooterHolder extends RecyclerView.ViewHolder {
        public FooterHolder(View view) {
            super(view);
        }
    }


}


