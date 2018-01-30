package com.example.misterburger.test.Controller.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.misterburger.test.Controller.Controller;
import com.example.misterburger.test.MainActivity;
import com.example.misterburger.test.R;
import com.example.misterburger.test.R2;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FirstActivity extends Fragment {
    @BindView(R2.id.push_btn)
    Button push_btn;
    @BindView(R2.id.show_btn)
    Button show_btn;
    Controller controller;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_activity,container,false);
        ButterKnife.bind(this,view);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = ((MainActivity)getActivity()).getController();
    }

    @Override
    public void onStart() {
        super.onStart();
        push_btn.setOnClickListener(view -> controller.getFirstTask().verifyStoragePermissions(getActivity()));
        show_btn.setOnClickListener(view -> {
            ((MainActivity)getActivity()).getFragment(FlagsOfFragment.SECOND_ACTIVITY);

        });
    }
}

