package ru.baltclinic.lliepmah.view.list.adapters;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.baltclinic.lliepmah.view.list.holders.DefaultViewHolder;
import ru.baltclinic.lliepmah.view.list.viewmodels.ViewModelWrapper;



public class UniversalAdapter extends RecyclerView.Adapter<DefaultViewHolder> {

    private final List<ViewModelWrapper> mItems = new ArrayList<>();
    private final List<DefaultViewHolder.Builder> mBuilders;

    public UniversalAdapter(DefaultViewHolder.Builder... builders) {
        mBuilders = Arrays.asList(builders);
    }

    public UniversalAdapter(List<DefaultViewHolder.Builder> builders) {
        mBuilders = builders;
    }

    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    public void replaceAll(List<ViewModelWrapper> elements) {
        mItems.clear();
        mItems.addAll(elements);
        notifyDataSetChanged();
    }

    public void add(int pos, ViewModelWrapper item) {
        mItems.add(pos, item);
    }


    public void add(@Nullable ViewModelWrapper item) {
        if (item != null) {
            mItems.add(item);
        }
    }

    public Object getItem(int position) {
        return mItems.get(position).getModel();
    }


    public void addNewItems(List<ViewModelWrapper> items) {
        mItems.addAll(items);
    }

    public  void removeItems(int startIndex, int endIndex) {
        for(int i= startIndex; i<=endIndex; i++){
            mItems.remove(startIndex);
        }
    }

    public <T> void addNewItems(List<T> items, Class<? extends DefaultViewHolder.Builder<T>> builderClass) {
        mItems.addAll(Stream.of(items)
                .map(item -> new ViewModelWrapper<>(item, builderClass))
                .collect(Collectors.toList()));
    }

    @Override
    public DefaultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mBuilders.get(viewType).build(parent.getContext());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBindViewHolder(DefaultViewHolder holder, int position) {
        holder.bind(mItems.get(position).getModel());
    }

    @Override
    public int getItemViewType(int position) {
        Class builderClass = mItems.get(position).getBuilderClass();

        for (DefaultViewHolder.Builder builder : mBuilders) {
            if (builderClass.equals(builder.getClass())) {
                return mBuilders.indexOf(builder);
            }
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

}
