package com.fong.play.ui.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.fong.play.R;
import com.fong.play.common.imageloader.ImageLoader;
import com.fong.play.data.bean.BannerBean;
import com.fong.play.data.bean.IndexBean;
import com.fong.play.ui.widget.BannerLayout;
import com.fong.play.ui.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by FANGDINGJIE
 * 2018/3/28.
 */

public class IndexMultipleAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    public static final int TYPE_BANNER = 1;
    private static final int TYPE_ICON = 2;
    private static final int TYPE_APPS = 3;
    private static final int TYPE_GAMES = 4;



    private IndexBean mIndexBean;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public IndexMultipleAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setData(IndexBean indexBean) {

        mIndexBean = indexBean;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_BANNER;
        } else if (position == 1) {
            return TYPE_ICON;
        } else if (position == 2) {
            return TYPE_APPS;
        } else if (position == 3) {
            return TYPE_GAMES;
        }

        return 4;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_BANNER:
                return new BannerViewHolder(mLayoutInflater.inflate(R.layout.template_banner, parent, false));
            case TYPE_ICON:
                return new NavIconViewHolder(mLayoutInflater.inflate(R.layout.template_nav_icon, parent, false));
            case TYPE_APPS:
                return new AppViewHolder(mLayoutInflater.inflate(R.layout.template_recyleview_with_title, null, false), TYPE_APPS);
            case TYPE_GAMES:
                return new AppViewHolder(mLayoutInflater.inflate(R.layout.template_recyleview_with_title, null, false), TYPE_GAMES);
            default:
                return null;

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (position) {
            case 0:
                BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
                List<BannerBean> banners = mIndexBean.getBanners();
                List<String> urls = new ArrayList<>(banners.size());
                for (BannerBean banner : banners) {
                    urls.add(banner.getThumbnail());
                }
                bannerViewHolder.banner.setViewUrls(urls);
                bannerViewHolder.banner.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
//                    banners.get(position)
                    }
                });
                break;
            case 1:
                NavIconViewHolder navViewHolder = (NavIconViewHolder) holder;
                navViewHolder.mLayoutHotApp.setOnClickListener(this);
                navViewHolder.mLayoutHotGame.setOnClickListener(this);
                navViewHolder.mLayoutHotSubject.setOnClickListener(this);
                break;
            case 2:
            case 3:
                AppViewHolder viewHolder = (AppViewHolder) holder;

                AppInfoAdapter appInfoAdapter =  AppInfoAdapter.builder().showBrief(true).showCategoryName(false).showPosition(false).build();
                if(viewHolder.type==TYPE_APPS){
                    viewHolder.text.setText("热门应用");
                    appInfoAdapter.addData(mIndexBean.getRecommendApps());
                }
                else{
                    viewHolder.text.setText("热门游戏");
                    appInfoAdapter.addData(mIndexBean.getRecommendGames());
                }

                viewHolder.recyclerView.setAdapter(appInfoAdapter);

                viewHolder.recyclerView.addOnItemTouchListener(new OnItemClickListener() {
                    @Override
                    public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {

                    }
                });
                break;
        }


    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public void onClick(View view) {

    }

    /**
     * banner
     */
    class BannerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.banner)
        BannerLayout banner;

        public BannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            banner.setImageLoader(new ImgLoader());
        }
    }

    /**
     * 导航
     */
    class NavIconViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.layout_hot_app)
        LinearLayout mLayoutHotApp;
        @BindView(R.id.layout_hot_game)
        LinearLayout mLayoutHotGame;
        @BindView(R.id.layout_hot_subject)
        LinearLayout mLayoutHotSubject;

        public NavIconViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 软件游戏
     */
    class AppViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.text)
        TextView text;
        @BindView(R.id.recycler_view)
        RecyclerView recyclerView;

        int type;

        public AppViewHolder(View itemView, int type) {
            super(itemView);
            this.type = type;
            ButterKnife.bind(this, itemView);
            initRecyclerView();

        }

        private void initRecyclerView() {
            recyclerView.setLayoutManager(new LinearLayoutManager(mContext) {

                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            });

            DividerItemDecoration itemDecoration = new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL_LIST);

            recyclerView.addItemDecoration(itemDecoration);
        }
    }

    /**
     * 图片加载
     */
    class ImgLoader implements BannerLayout.ImageLoader {

        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            ImageLoader.load(path, imageView);
        }
    }
}
