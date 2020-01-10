package com.example.jaspe.fypquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.jaspe.fypquiz.Model.Question_Score;
import com.example.jaspe.fypquiz.ViewHolder.ScoreDetailViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Score_Detail extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference question_score;

    RecyclerView scoreList;
    RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Question_Score,ScoreDetailViewHolder>adapter;
    String viewUser = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score__detail);

        database = FirebaseDatabase.getInstance();
        question_score = database.getReference("Question_Score");

        scoreList = (RecyclerView) findViewById(R.id.score_list);
        scoreList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        scoreList.setLayoutManager(layoutManager);

        if (getIntent() != null){
            viewUser = getIntent().getStringExtra("viewUser");
        }

        if (!viewUser.isEmpty()){
            loadScore(viewUser);
        }

    }

    private void loadScore(String viewUser) {
        adapter =  new FirebaseRecyclerAdapter<Question_Score, ScoreDetailViewHolder>(
                Question_Score.class,
                R.layout.score_detail_layout,
                ScoreDetailViewHolder.class,
                question_score.orderByChild("user").equalTo(viewUser)
        ) {
            @Override
            protected void populateViewHolder(ScoreDetailViewHolder viewHolder, Question_Score model, int position) {
                viewHolder.name_text.setText(model.getCategoryName());
                viewHolder.score_text.setText(model.getScore());
            }
        };
        adapter.notifyDataSetChanged();
        scoreList.setAdapter(adapter);
    }
}
