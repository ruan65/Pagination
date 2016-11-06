package com.cool.example.pagination;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by a on 07/11/2016.
 */

public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        setRetainInstance(true);
        init(rootView);
        return rootView;
    }

    private void init(View view) {
        Button autoLoadingButton = (Button)view.findViewById(R.id.btn_auto_loading);
        autoLoadingButton.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.container, new AutoLoadingFragment());
            transaction.commit();
        });

        Button paginationToolButton = (Button)view.findViewById(R.id.btn_pagination_tool);
        paginationToolButton.setOnClickListener(v -> {
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.addToBackStack(null);
            transaction.replace(R.id.container, new PaginationFragment());
            transaction.commit();
        });
    }


}
