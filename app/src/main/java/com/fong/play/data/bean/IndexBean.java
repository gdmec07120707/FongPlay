package com.fong.play.data.bean;

import java.util.List;

/**
 * Created by FANGDINGJIE
 * 2018/3/28.
 */

public class IndexBean {

    private List<BannerBean> banners;

    private List<AppInfo> recommendApps;

    private List<AppInfo> recommendGames;

    public List<BannerBean> getBanners() {
        return banners;
    }

    public void setBanners(List<BannerBean> banners) {
        this.banners = banners;
    }

    public List<AppInfo> getRecommendApps() {
        return recommendApps;
    }

    public void setRecommendApps(List<AppInfo> recommendApps) {
        this.recommendApps = recommendApps;
    }

    public List<AppInfo> getRecommendGames() {
        return recommendGames;
    }

    public void setRecommendGames(List<AppInfo> recommendGames) {
        this.recommendGames = recommendGames;
    }
}
