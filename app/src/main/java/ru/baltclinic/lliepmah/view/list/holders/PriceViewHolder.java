package ru.baltclinic.lliepmah.view.list.holders;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.baltclinic.lliepmah.R;
import ru.baltclinic.lliepmah.models.Price;
import ru.baltclinic.lliepmah.util.TextViewUtils;


public class PriceViewHolder extends DefaultViewHolder<Price> {

    private final Context mContext;

    @Nullable
    private Price mPrice = null;

    @BindView(R.id.li_prices_tv_price)
    TextView mTvPrice;
    @BindView(R.id.li_prices_tv_service)
    TextView mTvService;

    private PriceViewHolder(View itemView) {
        super(itemView);
        itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mContext = itemView.getContext();
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(Price model) {
        mPrice = model;
        if (mPrice != null) {
            TextViewUtils.setTextIfNotEmpty(mTvService, model.getTitle());
            TextViewUtils.setTextIfNotEmpty(mTvPrice, model.getValue() + " p.");
        }
    }

    public static class Builder implements DefaultViewHolder.Builder<Price> {

        public Builder() {
        }

        @Override
        public DefaultViewHolder<Price> build(Context context) {
            View view = View.inflate(context, R.layout.li_price, null);
            return new PriceViewHolder(view);
        }
    }

}
