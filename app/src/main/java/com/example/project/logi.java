package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class logi extends AppCompatActivity {
    Button lo;
    EditText email1,pwd1;
    TextView su,rp;
    FirebaseAuth ath;
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logi);
        lo=findViewById(R.id.logi);
        email1=findViewById(R.id.email1);
        pwd1=findViewById(R.id.pwd1);
        ath=FirebaseAuth.getInstance();
        pb=findViewById(R.id.pb);
        rp=findViewById(R.id.rp);
        rp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(logi.this,forgotpassword.class);
                startActivity(i);
            }
        });
        lo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userlogin();
            }
        });
       su=findViewById(R.id.su);
        su.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ie=new Intent(logi.this,register_Activity.class);
                startActivity(ie);
            }
        });
    }

    private void userlogin() {
        String email=email1.getText().toString().trim();
        String pwd=pwd1.getText().toString().trim();
        if(email.isEmpty()){
            email1.setError("Email is required!");
            email1.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email1.setError("Enter a valid email");
            email1.requestFocus();
            return;
        }
        if(pwd.isEmpty()){
            pwd1.setError("Password cannot be empty");
            pwd1.requestFocus();
            return;
        }
        if(pwd.length()<8){
            pwd1.setError("Min password length should be 8");
            pwd1.requestFocus();
            return;
        }
        pb.setVisibility(View.VISIBLE);
        ath.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                   pb.setVisibility(View.GONE);
                   Intent ie=new Intent(logi.this, swrdp.class);
                   startActivity(ie);
               }
               else{
                   Toast.makeText(logi.this,"Failed to login! please check your credentials",Toast.LENGTH_LONG).show();
                   pb.setVisibility(View.GONE);
               }
            }
        });
    }
}
