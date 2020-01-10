package com.example.jaspe.fypquiz.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jaspe.fypquiz.Interface.ItemClickListener;
import com.example.jaspe.fypquiz.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView category_name;
    public ImageView category_img;

    private ItemClickListener itemClickListener;

    public  CategoryViewHolder (View itemView){
        super(itemView);
        category_img = (ImageView) itemView.findViewById(R.id.category_img);
        category_name = (TextView) itemView.findViewById(R.id.category_title);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
