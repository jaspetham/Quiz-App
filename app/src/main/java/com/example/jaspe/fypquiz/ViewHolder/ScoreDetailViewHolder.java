package com.example.jaspe.fypquiz.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.jaspe.fypquiz.R;

import org.w3c.dom.Text;

public class ScoreDetailViewHolder extends RecyclerView.ViewHolder {

    public TextView name_text,score_text;
    public ScoreDetailViewHolder(View itemView) {
        super(itemView);

        name_text = (TextView) itemView.findViewById(R.id.txt_name);
        score_text = (TextView) itemView.findViewById(R.id.txt_score);
    }
}
