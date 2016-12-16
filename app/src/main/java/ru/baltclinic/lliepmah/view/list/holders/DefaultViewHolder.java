package ru.baltclinic.lliepmah.view.list.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public abstract class DefaultViewHolder<T> extends RecyclerView.ViewHolder {

    public DefaultViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(T model);

    public View getView() {
        return itemView;
    }

    public interface Builder<M> {
        DefaultViewHolder<M> build(Context context);
    }
}
