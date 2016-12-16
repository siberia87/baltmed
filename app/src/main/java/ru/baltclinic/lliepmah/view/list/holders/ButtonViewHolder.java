package ru.baltclinic.lliepmah.view.list.holders;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ru.baltclinic.lliepmah.R;
import ru.baltclinic.lliepmah.util.TextViewUtils;


public class ButtonViewHolder extends DefaultViewHolder<String> {

    private OnButtonClickListener mOnButtonClickListener;

    @BindView(R.id.li_button_btn_action)
    Button mBtnAction;


    private ButtonViewHolder(View itemView, OnButtonClickListener onButtonClickListener) {
        super(itemView);
        mOnButtonClickListener = onButtonClickListener;
        itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void bind(String model) {
        TextViewUtils.setTextIfNotEmpty(mBtnAction, model);
    }

    @OnClick(R.id.li_button_btn_action)
    void onButtonClick() {
        mOnButtonClickListener.onButtonClick();
    }


    public static class Builder implements DefaultViewHolder.Builder<String> {
        private OnButtonClickListener mOnButtonClickListener;

        public Builder(OnButtonClickListener onButtonClickListener) {
            mOnButtonClickListener = onButtonClickListener;
        }

        @Override
        public DefaultViewHolder<String> build(Context context) {
            View view = View.inflate(context, R.layout.li_button, null);
            return new ButtonViewHolder(view, mOnButtonClickListener);
        }
    }

    public interface OnButtonClickListener {
        void onButtonClick();
    }

}
