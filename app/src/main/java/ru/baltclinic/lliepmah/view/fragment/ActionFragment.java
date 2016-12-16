package ru.baltclinic.lliepmah.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.baltclinic.lliepmah.R;
import ru.baltclinic.lliepmah.models.Action;
import ru.baltclinic.lliepmah.util.ImageUtils;
import ru.baltclinic.lliepmah.util.TextViewUtils;
import ru.baltclinic.lliepmah.view.navigator.Navigator;


public class ActionFragment extends BaseFragment {

    private static final String KEY_DESCR = "key_full_descr";
    private static final String KEY_IMAGE_URL = "key_image_url";
    private static final String KEY_TITLE = "key_title";
    private static final String KEY_PREVIEW_URL = "key_preview_url";

    @BindView(R.id.fmt_action_iv_picture)
    ImageView mIvPicture;
    @BindView(R.id.fmt_action_tv_title)
    TextView mTvTitle;
    @BindView(R.id.fmt_action_tv_desc)
    TextView mTvDesc;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static Fragment newInstance(Context context, Action action) {
        ActionFragment actionFragment = new ActionFragment();
        Bundle bundle = new Bundle();

        bundle.putString(KEY_PREVIEW_URL, action.getPreviewUrl());
        bundle.putString(KEY_DESCR, action.getDescr());
        bundle.putString(KEY_IMAGE_URL, action.getImageUrl());

        bundle.putCharSequence(KEY_TITLE, TextViewUtils.makeActionTitle(context, action));

        actionFragment.setArguments(bundle);
        return actionFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fmt_action, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle args = getArguments();
        TextViewUtils.setTextIfNotEmpty(mTvDesc, args.getString(KEY_DESCR));
        TextViewUtils.setTextIfNotEmpty(mTvTitle, args.getCharSequence(KEY_TITLE));

        ImageUtils.loadImage(getContext(), args.getString(KEY_IMAGE_URL), mIvPicture);

    }

    @Override
    protected long getMenuIdentifier() {
        return R.id.menu_actions;
    }


    @Override
    public String getTitle() {
        return getString(R.string.ttl_actions);
    }


    @OnClick(R.id.fmt_action_appointment)
    void onactionAppointmentClick() {
        Navigator navigator = getNavigator();
        if (navigator != null) {
            navigator.openMakeAppointment();
        }
    }

    @OnClick(R.id.fmt_action_call)
    void onactionCallClick() {
        Navigator navigator = getNavigator();
        if (navigator != null) {
            navigator.openOrderCall();
        }
    }

    @Override
    protected Toolbar getToolbar() {
        return mToolbar;
    }

}
