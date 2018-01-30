package com.example.misterburger.test.RealmModule;

import android.content.Context;

import com.example.misterburger.test.XMLWorkspace.Product;

import java.util.List;

import io.realm.Realm;

public interface RealmInterface {
    Realm init(Context context);

    List<Product> readProducts(Context context);

    void saveProduct(Context context, Product product);

    void removeProducts(Context context, int id);

    void onDestroy(Context context);

  
}
