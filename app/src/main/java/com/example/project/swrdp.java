package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class swrdp extends AppCompatActivity {
    ImageButton st;
    Button lout;
    private FirebaseUser user;
    private DatabaseReference ref;
    private String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swrdp);
        st=findViewById(R.id.steps);
        lout=findViewById(R.id.lout);

        st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(swrdp.this,Pedometer.class);
                startActivity(it);
            }
        });
        lout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent ir=new Intent(swrdp.this,logi.class);
                startActivity(ir);
            }
        });
        user=FirebaseAuth.getInstance().getCurrentUser();
        ref= FirebaseDatabase.getInstance().getReference("Signup");
        userID=user.getUid();
         final TextView name=findViewById(R.id.eman);
         final TextView weigh=findViewById(R.id.weight);
         final TextView bm=findViewById(R.id.bmi);
        ref.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserHelperClass up=snapshot.getValue(UserHelperClass.class);
                if(up!=null){
                    String fn=up.name;
                    String weight=up.weight;
                    String height=up.height;
                    Log.d("name",fn);
                    Log.d("weight",weight);
                    Log.d("height",height);
                   name.setText(fn);
                   weigh.setText(weight);
                   bm.setText(fbmi(weight,height));
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            Toast.makeText(swrdp.this,"Something wrong happend!",Toast.LENGTH_LONG).show();
            }
        });
    }

    private String fbmi(String k, String htc) {
            int h2,m,ht,kg;
        double bm;
        double f_bmi;
        double diff;
        String result;
        ht=Integer.parseInt(htc);
        kg=Integer.parseInt(k);
        m = ht / 100;
            h2 = m * m;
            bm = kg/h2;
            f_bmi = Math.floor(bm);
            diff  = bm - f_bmi;
            diff = diff * 10;
            diff = Math.round(diff);
            if (diff == 10){
                f_bmi += 1;
                diff = 0;
            }
            result = String.valueOf(f_bmi);//+ "." + String.valueOf(diff);
            return result;
    }
}