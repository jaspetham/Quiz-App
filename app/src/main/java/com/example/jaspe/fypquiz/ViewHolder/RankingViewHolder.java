package com.example.jaspe.fypquiz.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.jaspe.fypquiz.Interface.ItemClickListener;
import com.example.jaspe.fypquiz.R;

public class RankingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView name_text,score_text;
    private ItemClickListener itemClickListener;

    public RankingViewHolder(View itemView) {
        super(itemView);
        name_text = (TextView) itemView.findViewById(R.id.name_text);
        score_text = (TextView) itemView.findViewById(R.id.score_text);

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
