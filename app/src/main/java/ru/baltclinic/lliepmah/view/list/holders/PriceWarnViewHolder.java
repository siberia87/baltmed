package ru.baltclinic.lliepmah.view.list.holders;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import ru.baltclinic.lliepmah.R;


public class PriceWarnViewHolder extends DefaultViewHolder<Void> {


    private PriceWarnViewHolder(View itemView) {
        super(itemView);
        itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(Void model) {
    }

    public static class Builder implements DefaultViewHolder.Builder<Void> {

        public Builder() {
        }

        @Override
        public DefaultViewHolder<Void> build(Context context) {
            View view = View.inflate(context, R.layout.li_prices_warn, null);
            return new PriceWarnViewHolder(view);
        }
    }


}
