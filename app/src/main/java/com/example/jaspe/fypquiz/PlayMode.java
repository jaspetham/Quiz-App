package com.example.jaspe.fypquiz;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaspe.fypquiz.Gameplay.Common;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class PlayMode extends AppCompatActivity implements View.OnClickListener {

    final static long INTERVAL = 1000; //1000 =  1 SEC
    final static long TIMEOUT = 20000; // 10000 = 10 sec
    int progressValue = 0; // progress bar value
    CountDownTimer countDownTimer;

    int index=0,score=0,currentQuestion=0;
    int totalQuestion,correctAnswer;


    ProgressBar progressBar;
    ImageView image_question;
    TextView  text_question,textQuestionNo,text_score;
    Button buttonA,buttonB,buttonC,buttonD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_mode);

        text_score = (TextView)findViewById(R.id.text_score);
        text_question = (TextView)findViewById(R.id.text_question);
        textQuestionNo = (TextView)findViewById(R.id.totalQuestion);
        image_question = (ImageView)findViewById(R.id.image_question);

        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        buttonA = (Button)findViewById(R.id.btn_answerA);
        buttonB = (Button)findViewById(R.id.btn_answerB);
        buttonC = (Button)findViewById(R.id.btn_answerC);
        buttonD = (Button)findViewById(R.id.btn_answerD);

        buttonA.setOnClickListener(this);
        buttonB.setOnClickListener(this);
        buttonC.setOnClickListener(this);
        buttonD.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        countDownTimer.cancel();
        if (index < totalQuestion) // if there are more question in the list
        {
            Button clickedButton = (Button)view;
            if (clickedButton.getText().equals(Common.questionList.get(index).getCorrectAnswer()))
            {
                //correct answer add score
                score += 10;
                correctAnswer++;
                revealQuestion(++index); // proceed next question
                Toast.makeText(PlayMode.this,"Correct Answer!",Toast.LENGTH_SHORT).show();
            }
            else
            {
                //incorrect answer
                revealQuestion(++index);
                Toast.makeText(PlayMode.this,"Incorrect Answer!",Toast.LENGTH_SHORT).show();
            }

            text_score.setText(String.format("%d",score));
        }
    }

    private void revealQuestion(int index) {
        if (index < totalQuestion)
        {
            currentQuestion++;
            textQuestionNo.setText(String.format("%d / %d",currentQuestion,totalQuestion));
            progressBar.setProgress(0);
            progressValue = 0;

            if (Common.questionList.get(index).getIsImageQuestion().equals("true"))
            {
                Picasso.with(getBaseContext())
                        .load(Common.questionList.get(index).getQuestion())
                        .into(image_question);
                image_question.setVisibility(View.VISIBLE);
                text_question.setVisibility(View.INVISIBLE);
            }
            else
            {
                text_question.setText(Common.questionList.get(index).getQuestion());
                image_question.setVisibility(View.INVISIBLE);
                text_question.setVisibility(View.VISIBLE);
            }

            buttonA.setText(Common.questionList.get(index).getAnswerA());
            buttonB.setText(Common.questionList.get(index).getAnswerB());
            buttonC.setText(Common.questionList.get(index).getAnswerC());
            buttonD.setText(Common.questionList.get(index).getAnswerD());

            countDownTimer.start();
        }
        else
        {
            //else is a final question
            Intent intent = new Intent(this,End.class);
            Bundle send_data = new Bundle();
            send_data.putInt("Score",score);
            send_data.putInt("Total",totalQuestion);
            send_data.putInt("Correct",correctAnswer);
            intent.putExtras(send_data);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        totalQuestion = Common.questionList.size();
        progressBar.setProgress(progressValue);
        countDownTimer = new CountDownTimer(15000,1000) {
            @Override
            public void onTick(long milliSecond) {
                progressBar.setProgress((int) progressValue*100/(15000/1000));
                progressValue++;

            }

            @Override
            public void onFinish() {
                countDownTimer.cancel();
                revealQuestion(index++);
            }
        };
        revealQuestion(index);
    }
}
