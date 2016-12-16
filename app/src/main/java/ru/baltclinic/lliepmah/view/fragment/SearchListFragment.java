package ru.baltclinic.lliepmah.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import ru.baltclinic.lliepmah.R;


public abstract class SearchListFragment<Entity> extends SimpleListFragment<Entity> {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fmt_list_with_search, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @OnTextChanged(R.id.v_search_field_et_search)
    void onTextChanged(CharSequence search) {
        search(search != null ? search.toString() : "");
    }

    protected abstract void search(String s);

}
