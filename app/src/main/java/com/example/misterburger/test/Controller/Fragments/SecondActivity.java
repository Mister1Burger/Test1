package com.example.misterburger.test.Controller.Fragments;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.misterburger.test.Controller.Controller;
import com.example.misterburger.test.Dialog.TMPData;
import com.example.misterburger.test.MainActivity;
import com.example.misterburger.test.R;
import com.example.misterburger.test.R2;
import com.example.misterburger.test.RealmModule.RealmImpl;
import com.example.misterburger.test.XMLWorkspace.Product;
import com.example.misterburger.test.XMLWorkspace.ProductAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SecondActivity extends Fragment {

    @BindView(R2.id.rv_list)
    RecyclerView recyclerView;
    List<Product> products;
    ProductAdapter adapter;
    TMPData tmpData;
    Controller controller;
    RealmImpl realm;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_activity, container, false);
        ButterKnife.bind(this, view);
        controller = ((MainActivity)getActivity()).getController();
        tmpData = controller.getTmpData();

        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onStart() {
        super.onStart();
        realm = controller.getRealm();
        products = realm.readProducts(getContext());
        adapter = new ProductAdapter(products,product ->{ tmpData.setProduct(product);((MainActivity)getActivity()).getFragment(FlagsOfFragment.DIALOG_FRAGMENT);});
        recyclerView.setAdapter(adapter);
    }
}
