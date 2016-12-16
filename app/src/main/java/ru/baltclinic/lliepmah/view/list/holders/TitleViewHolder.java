package ru.baltclinic.lliepmah.view.list.holders;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.baltclinic.lliepmah.R;
import ru.baltclinic.lliepmah.util.TextViewUtils;


public class TitleViewHolder extends DefaultViewHolder<String> {


    @BindView(R.id.li_title_tv_title)
    TextView mTvTitle;


    private TitleViewHolder(View itemView) {
        super(itemView);
        itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(String model) {
        TextViewUtils.setTextIfNotEmpty(mTvTitle, model);
    }

    public static class Builder implements DefaultViewHolder.Builder<String> {

        public Builder() {
        }

        @Override
        public DefaultViewHolder<String> build(Context context) {
            View view = View.inflate(context, R.layout.li_title, null);
            return new TitleViewHolder(view);
        }
    }

}
