package com.cool.example.pagination.data;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func0;

/**
 * Created by a on 08/11/2016.
 */

public class EmulateResponseManager {

    private final static int MAX_LIMIT = 1000;
    private static final long FAKE_RESPONSE_TIME_IN_MS = 200;
    private final static int MAX_FAKE_ERROR_COUNT = 2;
    private final static int OFFSET_WHEN_FAKE_ERROR = 200;

    private static volatile EmulateResponseManager client;

    private int fakeErrorCount = 0;

    public static EmulateResponseManager getInstance() {

        if (client == null) {

            synchronized (EmulateResponseManager.class) {
                if (client == null) {
                    client = new EmulateResponseManager();
                }
            }
        }
        return client;
    }

    public Observable<List<Item>> getEmulatedResponse(int offset, int limit) {

        if (offset == OFFSET_WHEN_FAKE_ERROR && fakeErrorCount < MAX_FAKE_ERROR_COUNT) {

            // I am emulating an error in the response
            fakeErrorCount++;

            return Observable.error(new RuntimeException("fake error"));
        } else {
            return Observable.defer(() -> Observable.just(getFakeItemList(offset, limit))
                    .delaySubscription(FAKE_RESPONSE_TIME_IN_MS, TimeUnit.MILLISECONDS));
        }
    }

    private List<Item> getFakeItemList(int offset, int limit) {

        List<Item> list = new ArrayList<>();

        if (offset > MAX_LIMIT) {
            return list;
        }

        int concreteLimit = offset + limit;

        if (concreteLimit > MAX_LIMIT) {
            concreteLimit = MAX_LIMIT;
        }

        for (int i = offset; i < concreteLimit; i++) {

            list.add(new Item(i, String.valueOf(i)));
        }
        return list;
    }
}
