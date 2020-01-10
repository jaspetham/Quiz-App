package com.example.jaspe.fypquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.jaspe.fypquiz.Gameplay.Common;
import com.example.jaspe.fypquiz.Model.Question;
import com.example.jaspe.fypquiz.Model.Question_Score;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class End extends AppCompatActivity {

    Button try_again_button;
    TextView resultScore,getTextQuestion;
    ProgressBar progressBar;

    FirebaseDatabase database;
    DatabaseReference question_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        database = FirebaseDatabase.getInstance();
        question_score = database.getReference("Question_Score");

        resultScore = (TextView)findViewById(R.id.text_totalScore);
        getTextQuestion = (TextView)findViewById(R.id.text_totalQuestion);
        progressBar = (ProgressBar) findViewById(R.id.done_progressBar);
        try_again_button = (Button) findViewById(R.id.try_again_button);

        try_again_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(End.this,main_page.class);
                startActivity(intent);
                finish();
            }
        });

        Bundle extra = getIntent().getExtras();
        if (extra != null)
        {
            int score =  extra.getInt("Score");
            int totalQuestion =  extra.getInt("Total");
            int correctAnswer =  extra.getInt("Correct");

            resultScore.setText(String.format("Score : %d",score));
            getTextQuestion.setText(String.format("Passed : %d / %d  ",correctAnswer,totalQuestion));

            progressBar.setMax(totalQuestion);
            progressBar.setProgress(correctAnswer);

            //transfer score to firebase
            question_score.child(String.format("%s_%s", Common.currentUser.getUserName(),
                                                        Common.categoryId))
                    .setValue(new Question_Score(String.format("%s_%s", Common.currentUser.getUserName(),
                                                                        Common.categoryId),
                                                                        Common.currentUser.getUserName(),
                                                                        String.valueOf(score),
                                                                        Common.categoryId,
                                                                        Common.categoryName));
        }
    }
}
