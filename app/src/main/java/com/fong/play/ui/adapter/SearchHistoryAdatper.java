package com.fong.play.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fong.play.R;

import java.util.List;

public class SearchHistoryAdatper extends BaseQuickAdapter<String,BaseViewHolder> {
    public SearchHistoryAdatper() {
        super( R.layout.template_search_history);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.btn,item);

        helper.addOnClickListener(R.id.btn);
    }
}
