package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotpassword extends AppCompatActivity {
 EditText emal;
 Button button;
 ProgressBar pb;
 FirebaseAuth ath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);
        emal=findViewById(R.id.emal);
        button=findViewById(R.id.button);
        pb=findViewById(R.id.pb);
        ath=FirebaseAuth.getInstance();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetpwd();
            }
        });
    }

    private void resetpwd() {
        String emao=emal.getText().toString().trim();
        if(emao.isEmpty()){
            emal.setError("Email is required");
            emal.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(emao).matches()){
            emal.setError("Enter a valid email");
            emal.requestFocus();
            return;
        }
        pb.setVisibility(View.VISIBLE);
        ath.sendPasswordResetEmail(emao).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(forgotpassword.this,"Check your email to reset your password",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(forgotpassword.this,"Oops!!! Try again",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}