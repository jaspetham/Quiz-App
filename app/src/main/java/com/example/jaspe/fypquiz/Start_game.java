package com.example.jaspe.fypquiz;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.jaspe.fypquiz.Gameplay.Common;
import com.example.jaspe.fypquiz.Model.Question;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Array;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Start_game extends AppCompatActivity {

    Button play_button;

    FirebaseDatabase database;
    DatabaseReference questions;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        database = FirebaseDatabase.getInstance();
        questions = database.getReference().child("Questions");

        loadQuestion(Common.categoryId);

        play_button = (Button) findViewById(R.id.play_button);
        play_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Start_game.this,PlayMode.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void loadQuestion(String categoryId) {
        //First clear the list if there are any old question
        if (Common.questionList.size() > 0)
        {
            Common.questionList.clear();
        }
        questions.orderByChild("CategoryId").equalTo(categoryId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                        {
                            Question quest = postSnapshot.getValue(Question.class);
                            Common.questionList.add(quest);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

        //Randomize question list
        Collections.shuffle(Common.questionList);
    }
}
