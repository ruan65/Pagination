package com.cool.example.pagination.utils;

import rx.Observable;

/**
 * Created by a on 12/11/2016.
 */

public interface PagingListener<T> {
    Observable<T> onNextPage(int offset);
}
