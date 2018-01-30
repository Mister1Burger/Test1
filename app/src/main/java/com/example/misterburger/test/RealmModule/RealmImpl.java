package com.example.misterburger.test.RealmModule;

import android.content.Context;
import android.util.Log;

import com.example.misterburger.test.Dialog.TMPData;
import com.example.misterburger.test.XMLWorkspace.Product;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;

public class RealmImpl implements RealmInterface {

    public RealmImpl() {
    }

    @Override
    public Realm init(Context context) {
        Realm.init(context);
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .name( "reminder.realm")
                .modules(new MyLibraryModule())
                .build();

        try {
            return Realm.getInstance(realmConfiguration);
        } catch (RealmMigrationNeededException e) {
            try {
                Log.e( "TAG", String.valueOf(e));
                Realm.deleteRealm(realmConfiguration);
                //Realm file has been deleted.
                return Realm.getInstance(realmConfiguration);
            } catch (Exception ex) {
                //No Realm file to remove.
                Log.e( "TAG", String.valueOf(ex));
            }
        }

        return null;
    }

    @Override
    public List<Product> readProducts(Context context) {
        Realm realm = init(context);
        File realmFile = new File(context.getFilesDir(), "reminder.realm");
        try {
            assert realm != null;
            RealmResults<Product> list = realm.where(Product.class)
                    .findAll();
            Log.d( "TAG", String.valueOf(realmFile.length()));
            if (list == null)
                return new ArrayList<>();
            return list;
        } catch (NullPointerException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void saveProduct(Context context, Product product) {

        Realm realm = init(context);
        File realmFile = new File(context.getFilesDir(), "reminder.realm");
        assert realm != null;
        try {
            realm.beginTransaction();
            realm.insertOrUpdate(product);
            Log.d( "TAG", String.valueOf(realmFile.length()));
            realm.commitTransaction();
        } catch (NullPointerException ignore) {
        }
    }

    @Override
    public void removeProducts(Context context, int id) {
        Realm realm = init(context);
        File realmFile = new File(context.getFilesDir(), "reminder.realm");
        assert realm != null;
        try {
           Product product = realm.where(Product.class)
                    .equalTo("id" , id).findFirst();
            if(product!=null) {
                realm.beginTransaction();
                product.deleteFromRealm();
                realm.commitTransaction();
            }
            Log.d( "TAG", String.valueOf(realmFile.length()));
        } catch (NullPointerException ignore) {
        }
    }

    @Override
    public void onDestroy(Context context) {
        Realm realm = init(context);
        assert realm != null;
        realm.close();
    }

    public void getProductFromRealm(TMPData tmpData, Context context,String value){
        Realm realm = init(context);
        File realmFile = new File(context.getFilesDir(), "reminder.realm");
        assert realm != null;
        if(value != null){
            Log.d("TAG",value);
        try {
            Product product = realm.where(Product.class)
                    .equalTo("id" , tmpData.getProduct().getId()).findFirst();
            if(product!=null) {
                realm.beginTransaction();
                product.setPrice(Double.parseDouble(value));

                realm.commitTransaction();
            }

            Log.d("TAG", String.valueOf(product.getId()));


        }catch (NullPointerException ignore) {

        }
    }}

    public void changeValue(String value){

    }

}
