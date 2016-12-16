package ru.baltclinic.lliepmah.view.list.holders;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.baltclinic.lliepmah.R;


public class MainHeaderViewHolder extends DefaultViewHolder<Void> {


    private final IMainHeaderCallbacks mMainHeaderCallbacks;

    private MainHeaderViewHolder(View itemView, IMainHeaderCallbacks mainHeaderCallbacks) {
        super(itemView);
        mMainHeaderCallbacks = mainHeaderCallbacks;
        itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(Void model) {
    }


    @OnClick({R.id.li_main_header_iv_prices, R.id.li_main_header_tv_prices})
    void onViewPrices() {
        if (mMainHeaderCallbacks != null) {
            mMainHeaderCallbacks.onViewPrices();
        }
    }

    @OnClick(R.id.li_main_header_tv_call)
    void onCall() {
        if (mMainHeaderCallbacks != null) {
            mMainHeaderCallbacks.onCallAction();
        }
    }

    @OnClick({R.id.li_main_header_iv_services, R.id.li_main_header_tv_services})
    void onViewServices() {
        if (mMainHeaderCallbacks != null) {
            mMainHeaderCallbacks.onViewServices();
        }
    }

    @OnClick({R.id.li_main_header_iv_subscribe, R.id.li_main_header_tv_subscribe})
    void onMakeAppointment() {
        if (mMainHeaderCallbacks != null) {
            mMainHeaderCallbacks.onMakeAppointment();
        }
    }

    public static class Builder implements DefaultViewHolder.Builder<Void> {

        IMainHeaderCallbacks mMainHeaderCallbacks;

        public Builder(IMainHeaderCallbacks mainHeaderCallbacks) {
            mMainHeaderCallbacks = mainHeaderCallbacks;
        }

        @Override
        public DefaultViewHolder<Void> build(Context context) {
            View view = View.inflate(context, R.layout.li_main_header, null);
            return new MainHeaderViewHolder(view, mMainHeaderCallbacks);
        }
    }

    public interface IMainHeaderCallbacks {
        void onViewPrices();

        void onViewServices();

        void onMakeAppointment();

        void onCallAction();
    }

}
