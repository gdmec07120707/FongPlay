package com.fong.play.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fong.play.R;
import com.fong.play.common.Constant;
import com.fong.play.data.bean.Subject;

import java.util.List;

public class SubjectAdapter extends BaseQuickAdapter<Subject,BaseViewHolder>{
    public SubjectAdapter() {
        super( R.layout.template_subject_imageview );
    }

    @Override
    protected void convert(BaseViewHolder helper, Subject item) {
        ImageView imageView =  helper.getView(R.id.imageview);
        String url = Constant.BASE_IMG_URL+item.getMticon();
        Glide.with(mContext).load(url).into(imageView);
    }
}
