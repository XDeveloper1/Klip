package com.idiots.klip.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;
import com.idiots.klip.R;
import com.idiots.klip.home.dashboard;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    EditText phone;
    CountryCodePicker ccp;
    Button button;
    EditText phone1, otps;
    Button button1;
    String number,id,bio,gender,dob,photo,username,followers,following,likes,phonenumber,email,imageurl;
    String otpid;
    String Snapshot;
    TextView optsdf;
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    DatabaseReference reference;
    public static  String PREFS_NAME="myprefsfile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phone=findViewById(R.id.phone);
        button=findViewById(R.id.otpsend);
        ccp=(CountryCodePicker)findViewById(R.id.ccp) ;
        ccp.registerCarrierNumberEditText(phone);
        optsdf=findViewById(R.id.otpsdf);
        phone = findViewById(R.id.phone);
        button1 = findViewById(R.id.button1);
        otps = findViewById(R.id.otp);
        mAuth = FirebaseAuth.getInstance();
        db= FirebaseDatabase.getInstance();
        reference= db.getReference("users");


        button.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View view) {
                  if( !ccp.getFullNumberWithPlus().isEmpty()){
                   number= ccp.getFullNumberWithPlus();
                    initiateotp();

                  }else{
                      phone.setError("please enter the number");
                  }


            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (otps.getText().toString().isEmpty())
                    Toast.makeText(MainActivity.this, "blank field cannot be processed", Toast.LENGTH_SHORT).show();
                else if (otps.getText().toString().length() != 6)
                    Toast.makeText(MainActivity.this, "invalid otp", Toast.LENGTH_SHORT).show();
                else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpid, otps.getText().toString());
                    signInWithPhoneAuthCredential(credential);
                }

            }
        });
    }



    private void initiateotp() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,        // Phone number to verify
                10,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
                {
                    @Override
                    public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken)
                    {
                        otpid=s;
                        optsdf.setText(s);
                    }

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential)
                    {
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });        // OnVerificationStateChangedCallbacks

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                                    insertdata();
                            Toast.makeText(MainActivity.this, "going", Toast.LENGTH_SHORT).show();

                           SharedPreferences sharedPreferences =getSharedPreferences(MainActivity.PREFS_NAME,0);
                          SharedPreferences.Editor editor =sharedPreferences.edit();
                          editor.putBoolean("hasloggedIN",true);
                          editor.commit();
                          insertdata();
                          startActivity(new Intent(getApplicationContext(), dashboard.class));


                        } else {
                            Toast.makeText(getApplicationContext(),"Signin Code Error",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    private void insertdata() {

        reference.child(number).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

              if(dataSnapshot.exists()){
                  for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                      Snapshot =dataSnapshot1.getValue(String.class);
                      if(Snapshot.equals(number)){
                          Toast.makeText(MainActivity.this, "prsent", Toast.LENGTH_SHORT).show();
                          Log.d(Snapshot, "onDataChange: ");

                          break;
                      }

                  }

              }else{
                  id ="";
                  bio ="";
                  gender="";
                  dob="";
                  photo="";
                  username=number+"klip";
                  followers="0";
                  following="0";
                  likes="0";
                  email="";



                  Luser luser = new Luser(number,id,bio,gender,dob,photo,username,followers,following,likes,email);
                  if (reference.child(number).setValue(luser).isSuccessful()){
                  Toast.makeText(MainActivity.this, "sucess", Toast.LENGTH_SHORT).show();
          }

              }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}

