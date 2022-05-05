package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.CharArrayWriter;

public class register_Activity extends AppCompatActivity{
    EditText name,age,mail,pwd,height,weight;
    RadioGroup gender;
    FirebaseAuth ath;
    Button signu,reset;
    RadioButton rb;
    Spinner spinner;
//    ProgressBar progressbar;
    FirebaseDatabase rn;
    DatabaseReference ref;
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        spinner=findViewById(R.id.spinner);
        ath=FirebaseAuth.getInstance();
        signu=findViewById(R.id.signup);
        name=findViewById(R.id.name);
        age=findViewById(R.id.age);
        mail=findViewById(R.id.mail);
        pwd=findViewById(R.id.pwd);
        gender=findViewById(R.id.gender);
        height=findViewById(R.id.height);
        weight=findViewById(R.id.weight);
        reset=findViewById(R.id.reset);
        spinner=findViewById(R.id.spinner);
        pb=findViewById(R.id.pb);
        signu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createuser();

            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });
    }


    private void createuser(){
        String[] gen = new String[1];
        int radid=gender.getCheckedRadioButtonId();
        rb=findViewById(radid);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.bloodgrp, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        final String[] text = new String[10];
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                text[0] =spinner.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        String ag=age.getText().toString();
        String mal=mail.getText().toString();
        String hei=height.getText().toString();
        String wei=weight.getText().toString();
        String pw=pwd.getText().toString();
        String na=name.getText().toString();
      // if(TextUtils.isEmpty(na) || TextUtils.isEmpty(ag) || TextUtils.isEmpty(mal) || TextUtils.isEmpty(pw) || TextUtils.isEmpty(gen[0]) || TextUtils.isEmpty(hei) || TextUtils.isEmpty(wei) || !Patterns.EMAIL_ADDRESS.matcher(mal).matches() || pw.length()<8) {
            if (TextUtils.isEmpty(na)) {
                name.setError("Name cannot be empty");
                name.requestFocus();
            }
            if (TextUtils.isEmpty(ag)) {
                age.setError("Age cannot be empty");
                age.requestFocus();
            }
            if (TextUtils.isEmpty(mal)) {
                mail.setError("Email cannot be empty");
                mail.requestFocus();
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(mal).matches()) {
                mail.setError("Enter a valid email");
                mail.requestFocus();
            }
            if (TextUtils.isEmpty(pw)) {
                pwd.setError("Password cannot be empty");
                pwd.requestFocus();
            }
            if (pw.length() < 8) {
                pwd.setError("Min password length should be 8");
                pwd.requestFocus();
            }
            if (TextUtils.isEmpty(gen[0])) {
                gender.setContentDescription("Gender cannot be empty");
                gender.requestFocus();
            }
            if (TextUtils.isEmpty(hei)) {
                height.setError("Height cannot be empty");
                height.requestFocus();
            }
            if (TextUtils.isEmpty(wei)) {
                weight.setError("Weight cannot be empty");
                weight.requestFocus();
            }
      //}
    //else if(!TextUtils.isEmpty(na) && !TextUtils.isEmpty(ag) && !TextUtils.isEmpty(mal) && !TextUtils.isEmpty(pw) && !TextUtils.isEmpty(gen[0]) && !TextUtils.isEmpty(hei) &&!TextUtils.isEmpty(wei) && Patterns.EMAIL_ADDRESS.matcher(mal).matches() && pw.length()>=8){
    else{        String[] g = new String[100];
            String[] text1 = new String[10];
            text1[0] =spinner.getSelectedItem().toString();
            String te = text1[0];
            radid=gender.getCheckedRadioButtonId();
            rb=findViewById(radid);
            g[0]=rb.getText().toString();
            pb.setVisibility(View.VISIBLE);
            ath.createUserWithEmailAndPassword(mal,pw)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                             UserHelperClass helperClass = new UserHelperClass(na, ag, mal, pw, g[0], hei, wei, te);
                                FirebaseDatabase.getInstance().getReference("Signup")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(helperClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(register_Activity.this,"User Registration Successful",Toast.LENGTH_LONG).show();
                                            pb.setVisibility(View.GONE);
                                            Intent i=new Intent(register_Activity.this,logi.class);
                                            startActivity(i);
                                        }
                                        else{
                                            Toast.makeText(register_Activity.this,"Failed to register! Try again",Toast.LENGTH_LONG).show();
                                            pb.setVisibility(View.GONE);
                                        }
                                    }
                                });
                            }
                            else{
                                Toast.makeText(register_Activity.this,"Failed to register!! Try again",Toast.LENGTH_LONG).show();
                                pb.setVisibility(View.GONE);
                            }
                        }

                    });


        }
    }

    public void reset(){
        name.setText("");
        age.setText("");
        mail.setText("");
        pwd.setText("");
        weight.setText("");
        gender.clearCheck();
        height.setText("");
    }


}
