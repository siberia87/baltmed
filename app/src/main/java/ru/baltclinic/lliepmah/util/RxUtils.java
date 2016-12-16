package ru.baltclinic.lliepmah.util;

import java.util.List;

import ru.baltclinic.lliepmah.models.Ordered;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RxUtils {

    public static <T> Observable.Transformer<T, T> applySchedulers() {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T extends Ordered> Observable.Transformer<List<T>, List<T>> sortOrdered() {
        return observable -> observable
                .flatMap(Observable::from)
                .sorted((n1, n2) -> n1.getOrder() > n2.getOrder() ? 1 : -1)
                .toList();
    }

}
