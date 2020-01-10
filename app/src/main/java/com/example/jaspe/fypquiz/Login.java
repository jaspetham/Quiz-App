package com.example.jaspe.fypquiz;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaspe.fypquiz.Gameplay.Common;
import com.example.jaspe.fypquiz.Model.User_Info;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Random;

public class Login extends AppCompatActivity {
    EditText editUsername,editPassword;
    Button button_sign_in;
    TextView sign_up;
    FirebaseDatabase firedatabase; //access firebase
    DatabaseReference user_table;
    AlarmReceiver helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        helper = new AlarmReceiver(this);
        Notification.Builder builder = helper.getFYPChannelNotification();
        helper.getManager().notify(new Random().nextInt(),builder.build());

        editPassword = (EditText)findViewById(R.id.editPassword);
        editUsername = (EditText)findViewById(R.id.editUsername);
        button_sign_in = (Button) findViewById(R.id.button_login);
        sign_up = (TextView) findViewById(R.id.sign_up_here);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUp = new Intent(Login.this,SignUp.class);
                startActivity(signUp);

            }
        });

        firedatabase =  FirebaseDatabase.getInstance();
        user_table = firedatabase.getReference("Users");
        button_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(Login.this);
                mDialog.setMessage("Processing...");
                mDialog.show();

                user_table.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Check if user exist
                        if (dataSnapshot.child(editUsername.getText().toString()).exists()) {
                            mDialog.dismiss();
                            //Get user information
                            User_Info user = dataSnapshot.child(editUsername.getText().toString()).getValue(User_Info.class);
                            if (user.getPwd().equals(editPassword.getText().toString())) {
                                Intent main_page_activity = new Intent(Login.this, main_page.class);
                                Common.currentUser = user;
                                startActivity(main_page_activity);
                                finish();
                            } else {
                                Toast.makeText(Login.this, "Incorrect password!", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            mDialog.dismiss();
                            Toast.makeText(Login.this, "This user does not exist!", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
    }

//    private void registerAlarmReceiver() {
//        Calendar calender = Calendar.getInstance();
//        calender.set(Calendar.HOUR_OF_DAY,15);
//        calender.set(Calendar.MINUTE,39);
//        calender.set(Calendar.SECOND,0);
//        Notification.Builder builder = helper.getFYPChannelNotification();
//
//        Intent intent = new Intent(Login.this, AlarmReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(Login.this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
//        AlarmManager alarmManager = (AlarmManager)this.getSystemService(this.ALARM_SERVICE);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calender.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
//
//        helper.getManager().notify(new Random().nextInt(),builder.build());
//    }
}
