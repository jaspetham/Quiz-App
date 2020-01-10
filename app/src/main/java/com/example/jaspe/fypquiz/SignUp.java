package com.example.jaspe.fypquiz;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.jaspe.fypquiz.Model.User_Info;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp extends AppCompatActivity {

    EditText newUsername, newPassword, newEmail;
    Button button_sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_layout);

        newUsername = (EditText)findViewById(R.id.new_username);
        newPassword = (EditText)findViewById(R.id.new_password);
        newEmail = (EditText)findViewById(R.id.newEmail);

        button_sign_up = (Button) findViewById(R.id.button_sign_up);

        final FirebaseDatabase database = FirebaseDatabase.getInstance(); //access firebase
        final DatabaseReference user_table  = database.getReference("Users");

        button_sign_up.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("Registering...");
                mDialog.show();

                user_table.addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(newUsername .getText().toString()).exists()) {
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this, "User already exist!",
                                    Toast.LENGTH_SHORT).show();
                            finish();

                        }
                        else {
                            mDialog.dismiss();
                            User_Info user_info = new User_Info(newUsername.getText().toString(),newPassword.getText().toString(), newEmail.getText().toString());
                            user_table.child(newUsername.getText().toString()).setValue(user_info);
                            Toast.makeText(SignUp.this, "Sign up successfully!",
                                    Toast.LENGTH_SHORT).show();

                            finish();
                            user_table.removeEventListener(this);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
