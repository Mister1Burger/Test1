package com.example.misterburger.test.Controller;

import android.content.Context;

import com.example.misterburger.test.Controller.Fragments.FirstActivity;
import com.example.misterburger.test.Controller.Fragments.SecondActivity;
import com.example.misterburger.test.Dialog.PriceDialog;
import com.example.misterburger.test.Dialog.TMPData;
import com.example.misterburger.test.RealmModule.RealmImpl;
import com.example.misterburger.test.XMLWorkspace.FirstTask;

import java.io.Serializable;

public class Controller implements ControllerInterface , Serializable {

    private FirstTask firstTask;
    private TMPData tmpData;
    private FirstActivity firstActivity;
    private SecondActivity secondActivity;
    private PriceDialog dialog;
    RealmImpl realm;

    @Override
    public void init(Context context){
        realm = new RealmImpl();
        firstTask = new FirstTask(context, realm);
        tmpData = new TMPData();
        firstActivity = new FirstActivity();
        secondActivity = new SecondActivity();
        dialog = new PriceDialog();

    }

    @Override
    public RealmImpl getRealm() {
        return realm;
    }

    @Override
    public FirstTask getFirstTask(){
        return firstTask;
    }

    @Override
    public TMPData getTmpData() {
        return tmpData;
    }

    @Override
    public FirstActivity getFirstActivity() {
        return firstActivity;
    }

    @Override
    public SecondActivity getSecondActivity() {
        return secondActivity;
    }

    @Override
    public PriceDialog getDialog() {
        return dialog;
    }
}
