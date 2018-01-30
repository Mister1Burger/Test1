package com.example.misterburger.test.Controller;

import android.app.DialogFragment;
import android.content.Context;

import com.example.misterburger.test.Controller.Fragments.FirstActivity;
import com.example.misterburger.test.Controller.Fragments.SecondActivity;
import com.example.misterburger.test.Dialog.TMPData;
import com.example.misterburger.test.RealmModule.RealmImpl;
import com.example.misterburger.test.XMLWorkspace.FirstTask;

public interface ControllerInterface {
    void init(Context context);

    RealmImpl getRealm();

    FirstTask getFirstTask();

    TMPData getTmpData();

    FirstActivity getFirstActivity();

    SecondActivity getSecondActivity();

    DialogFragment getDialog();
}
