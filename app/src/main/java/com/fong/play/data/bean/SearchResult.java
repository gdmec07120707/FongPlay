package com.fong.play.data.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5market.data.entity
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

public class SearchResult implements Serializable {

    private boolean hasMore;

    private List<AppInfo> listApp;

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public List<AppInfo> getListApp() {
        return listApp;
    }

    public void setListApp(List<AppInfo> listApp) {
        this.listApp = listApp;
    }
}
