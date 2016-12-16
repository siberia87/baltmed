package ru.baltclinic.lliepmah.view.list.viewmodels;


import ru.baltclinic.lliepmah.view.list.holders.DefaultViewHolder;


public class ViewModelWrapper<T> {

    private final T mViewModel;
    private final Class<? extends DefaultViewHolder.Builder<T>> mBuilderClass;

    public static <T> ViewModelWrapper<T> build(T viewModel, Class<? extends DefaultViewHolder.Builder<T>> builderClass) {
        return new ViewModelWrapper<>(viewModel, builderClass);
    }

    public ViewModelWrapper(T viewModel, Class<? extends DefaultViewHolder.Builder<T>> builderClass) {
        mViewModel = viewModel;
        mBuilderClass = builderClass;
    }

    public T getModel() {
        return mViewModel;
    }

    public Class<? extends DefaultViewHolder.Builder<T>> getBuilderClass() {
        return mBuilderClass;
    }

}
