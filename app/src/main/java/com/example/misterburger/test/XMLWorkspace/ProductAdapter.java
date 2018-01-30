package com.example.misterburger.test.XMLWorkspace;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.misterburger.test.R;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.UserListViewHolder> {

        List<Product> projects;
        ProductListener listener;


public static class UserListViewHolder extends RecyclerView.ViewHolder {
    LinearLayout linearLayout;
    TextView product_id;
    TextView product_name;
    TextView product_price;


    UserListViewHolder(View itemView) {
        super(itemView);
        linearLayout = itemView.findViewById(R.id.product_list);
        product_id = itemView.findViewById(R.id.product_id);
        product_name = itemView.findViewById(R.id.product_name);
        product_price = itemView.findViewById(R.id.product_price);

    }
}

    public ProductAdapter(List<Product> projects, ProductListener listener) {
        this.projects = projects;
        this.listener = listener;
    }

    public List<Product> getProducts() {
        return projects;
    }

    @Override
    public ProductAdapter.UserListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cellgrid, parent, false);
        UserListViewHolder pvh = new UserListViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(ProductAdapter.UserListViewHolder holder, final int position) {
        holder.product_id.setText(String.valueOf(getProducts().get(position).getId()));
        holder.product_name.setText(getProducts().get(position).getName());
        holder.product_price.setText(String.valueOf(getProducts().get(position).getPrice()));
        holder.linearLayout.setOnClickListener(view -> listener.getProductToListener(getProducts().get(position)));
    }

    @Override
    public int getItemCount() {
        return projects.size();
    }}
