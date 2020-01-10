package com.example.jaspe.fypquiz;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jaspe.fypquiz.Gameplay.Common;
import com.example.jaspe.fypquiz.Interface.ItemClickListener;
import com.example.jaspe.fypquiz.Interface.RankingCallBack;
import com.example.jaspe.fypquiz.Model.Question_Score;
import com.example.jaspe.fypquiz.Model.Ranking;
import com.example.jaspe.fypquiz.ViewHolder.RankingViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class RankingFragment extends Fragment {

    View myFragment;
    FirebaseDatabase database;
    RecyclerView rankingList;
    LinearLayoutManager layoutManager;
    FirebaseRecyclerAdapter<Ranking,RankingViewHolder> adapter;
    DatabaseReference questionScore,rankingTable;
    int sum = 0; //score is default by zero

    public static RankingFragment newInstance(){
        RankingFragment rankingFragment = new RankingFragment();
        return rankingFragment ;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        questionScore = database.getReference("Question_Score");
        rankingTable = database.getReference("Ranking");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_ranking,container,false);

        rankingList = (RecyclerView) myFragment.findViewById(R.id.ranking_list);
        layoutManager = new LinearLayoutManager(getActivity());
        rankingList.setHasFixedSize(true);
        //Using orderByChild method , this will sort the ranking in ascending order
        //reverse the data by using layout manager
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        rankingList.setLayoutManager(layoutManager);



        updateScore(Common.currentUser.getUserName(), new RankingCallBack<Ranking>() {
            @Override
            public void callBack(Ranking ranking) {
                //Ranking Score update
                rankingTable.child(ranking.getUserName())
                        .setValue(ranking);
                // showRanking();
            }
        });

        adapter = new FirebaseRecyclerAdapter<Ranking, RankingViewHolder>(
                Ranking.class,
                R.layout.ranking_layout,
                RankingViewHolder.class,
                rankingTable.orderByChild("score")
        ) {
            @Override
            protected void populateViewHolder(RankingViewHolder viewHolder, final Ranking model, int position) {


                viewHolder.name_text.setText(model.getUserName());
                viewHolder.score_text.setText(String.valueOf(model.getScore()));

                //prevent crash when user click
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent scoreDetail = new Intent(getActivity(),Score_Detail.class);
                        scoreDetail.putExtra("viewUser",model.getUserName());
                        startActivity(scoreDetail);
                    }
                });
            }
        };

        adapter.notifyDataSetChanged();
        rankingList.setAdapter(adapter);
        return myFragment;
    }



    private void updateScore(final String userName, final RankingCallBack<Ranking> callBack) {
        questionScore.orderByChild("user").equalTo(userName)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot data:dataSnapshot.getChildren())
                        {
                            Question_Score quest = data.getValue(Question_Score.class);
                            sum += Integer.parseInt(quest.getScore());

                        }

                        Ranking ranking = new Ranking(userName,sum);
                        callBack.callBack(ranking);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }
}
