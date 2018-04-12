package com.fong.play.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fong.play.R;
import com.fong.play.common.Constant;
import com.fong.play.common.imageloader.ImageLoader;
import com.fong.play.data.bean.AppInfo;
import com.fong.play.ui.widget.DownloadButtonConntroller;
import com.fong.play.ui.widget.DownloadProgressButton;

import java.util.List;

import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadRecord;

/**
 * 菜鸟窝http://www.cniao5.com 一个高端的互联网技能学习平台
 *
 * @author Ivan
 * @version V1.0
 * @Package com.cniao5.cniao5play.ui.adapter
 * @Description: ${TODO}(用一句话描述该文件做什么)
 * @date
 */

public class AppManagerAdapter extends BaseQuickAdapter<DownloadRecord, BaseViewHolder> {

    private DownloadButtonConntroller mDownloadController;

    public AppManagerAdapter(RxDownload mRxDownload) {
        super(R.layout.template_app_downloading);
        mDownloadController = new DownloadButtonConntroller(mRxDownload);
    }

    @Override
    protected void convert(BaseViewHolder helper, DownloadRecord item) {

        AppInfo appInfo = mDownloadController.downloadRecord2AppInfo(item);

        ImageLoader.load(Constant.BASE_IMG_URL+appInfo.getIcon(), (ImageView) helper.getView(R.id.img_app_icon));
        helper.setText(R.id.txt_app_name,appInfo.getDisplayName());


        helper.addOnClickListener(R.id.btn_download);
        View viewBtn  = helper.getView(R.id.btn_download);

        if (viewBtn instanceof  DownloadProgressButton){

            DownloadProgressButton btn = (DownloadProgressButton) viewBtn;
            mDownloadController.handClick(btn,item);
        }


    }
}
