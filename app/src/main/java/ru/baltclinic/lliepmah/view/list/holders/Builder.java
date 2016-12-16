package ru.baltclinic.lliepmah.view.list.holders;

import android.content.Context;
import android.view.View;

import ru.baltclinic.lliepmah.R;
import ru.baltclinic.lliepmah.models.Action;

public class Builder implements DefaultViewHolder.Builder<Action> {

        private final ActionViewHolder.OnActionClickListener mOnActionClickListener;

        public Builder(ActionViewHolder.OnActionClickListener onActionClickListener) {
            mOnActionClickListener = onActionClickListener;
        }

        @Override
        public DefaultViewHolder<Action> build(Context context) {
            View view = View.inflate(context, R.layout.li_action, null);
            return new ActionViewHolder(view, mOnActionClickListener);
        }
    }
