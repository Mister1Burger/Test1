package com.example.misterburger.test.Dialog;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;

import com.example.misterburger.test.Controller.Controller;
import com.example.misterburger.test.MainActivity;
import com.example.misterburger.test.R;
import com.example.misterburger.test.R2;
import com.example.misterburger.test.RealmModule.RealmImpl;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PriceDialog extends DialogFragment {
    @BindView(R2.id.price_of_name)
    TextView price_of_name;
    @BindView(R2.id.price_et)
    EditText price_et;
    Controller controller;
    TMPData tmpData;
    RealmImpl realm;



    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        ButterKnife.bind(this,inflater.inflate(R.layout.dialog, null));
        controller = ((MainActivity)getActivity()).getController();
        tmpData = controller.getTmpData();
        realm = controller.getRealm();
        builder.setView(inflater.inflate(R.layout.dialog, null))
                .setPositiveButton(R.string.save_btn, (dialog, id) -> dialogFilling())
                .setNegativeButton(R.string.cancel_btn, (dialog, id) -> PriceDialog.this.getDialog().cancel());

        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
//        price_of_name.setText("Price of " + tmpData.getProduct().getName());
//        price_et.setText(String.valueOf(tmpData.getProduct().getPrice()));
    }

    private void dialogFilling(){
        Log.d("TAG", String.valueOf(price_et.getText()) + "  WQW");
        if (TextUtils.isEmpty(price_et.getText())) {
            PriceDialog.this.getDialog().cancel();
        }else {
            realm.getProductFromRealm(tmpData, (MainActivity)getActivity(),String.valueOf(price_et.getText()));

        }
            PriceDialog.this.getDialog().cancel();
    }

}


