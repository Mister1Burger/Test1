package com.example.misterburger.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.misterburger.test.Controller.Controller;
import com.example.misterburger.test.Controller.Fragments.FirstActivity;
import com.example.misterburger.test.Controller.Fragments.FlagsOfFragment;
import com.example.misterburger.test.Controller.Fragments.SecondActivity;
import com.example.misterburger.test.Dialog.PriceDialog;

public class MainActivity extends AppCompatActivity {
    Controller controller;
    FirstActivity firstActivity;
    SecondActivity secondActivity;
    PriceDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controller = new Controller();
        controller.init(this);
        firstActivity = controller.getFirstActivity();
        secondActivity = controller.getSecondActivity();
        dialog = controller.getDialog();
        getFragment(FlagsOfFragment.MAIN_ACTIVITY);
    }

    public Controller getController() {
        return controller;
    }

    public void getFragment(FlagsOfFragment flag) {
        switch (flag) {
            case MAIN_ACTIVITY:
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment, firstActivity)
                        .commit();
                break;
            case SECOND_ACTIVITY:
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment, secondActivity)
                        .commit();
            case DIALOG_FRAGMENT:
                dialog.show(getFragmentManager(), "dialog");

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}