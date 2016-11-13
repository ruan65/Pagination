package com.cool.example.pagination;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cool.example.pagination.data.EmulateResponseManager;
import com.cool.example.pagination.data.Item;
import com.cool.example.pagination.utils.PaginationTool;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class PaginationFragment extends Fragment {

    private final static int LIMIT = 50;
    private PagingRecyclerViewAdapter adapter;
    private RecyclerView recyclerView;
    private Subscription subscription;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_pagination, container, false);

        setRetainInstance(true);
        init(root, savedInstanceState);

        return root;
    }

    private void init(View root, Bundle savedInstanceState) {

        recyclerView = (RecyclerView) root.findViewById(R.id.RecyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 1);
        gridLayoutManager.supportsPredictiveItemAnimations();

        if (savedInstanceState == null) {
            adapter = new PagingRecyclerViewAdapter();
            adapter.setHasStableIds(true);
        }

        recyclerView.setSaveEnabled(true);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        if (adapter.areAllItemsLoaded()) {
            return;
        }


        PaginationTool<List<Item>> paginationTool = PaginationTool.buildPagingObservable(recyclerView,

                offset -> EmulateResponseManager.getInstance().getEmulatedResponse(offset, LIMIT)
        )
                .setLimit(LIMIT)
                .build();


        subscription = paginationTool
                .getPagingObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Item>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Item> items) {

                        adapter.addNewItems(items);
                        adapter.notifyItemInserted(adapter.getItemCount() - items.size());
                    }
                });
    }

    @Override
    public void onDestroyView() {

        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }

        // for memory leak prevention (RecycleView is not unsubscibed from adapter DataObserver)
        if (recyclerView != null) {
            recyclerView.setAdapter(null);
        }

        super.onDestroyView();
    }
}





















