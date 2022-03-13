package com.idiots.klip.account;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.idiots.klip.R;

import java.util.HashMap;

public class Editprofile extends AppCompatActivity {
    EditText usernam,userid,bios,phone;
    String username,id,bio,number,gender,cuser;
    Spinner spinner;
    DatePicker datePicker ;
    Button save;
    FirebaseDatabase db;
    DatabaseReference reference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);
        usernam=findViewById(R.id.usernam);
        userid=findViewById(R.id.useridss);
        bios=findViewById(R.id.bios);
        phone=findViewById(R.id.phnnumber);
        save=findViewById(R.id.save);
        spinner=findViewById(R.id.gender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(Editprofile.this, R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        db = FirebaseDatabase.getInstance();
        cuser =user.getPhoneNumber();
        reference = db.getReference("users");
        getdata();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatedata();
            }
        });



    }

    private void getdata() {
        reference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String uname =dataSnapshot.child(cuser).child("username").getValue(String.class);
                String id =dataSnapshot.child(cuser).child("id").getValue(String.class);
                String bio =dataSnapshot.child(cuser).child("bio").getValue(String.class);
                String number =dataSnapshot.child(cuser).child("number").getValue(String.class);
                String gender =dataSnapshot.child(cuser).child("gender").getValue(String.class);
                    usernam.setText(uname);
                    userid.setText(id);
                    bios.setText(bio);
                    phone.setText(number);
                    spinner.setAutofillHints(new String[]{gender});


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void updatedata() {
         username= usernam.getText().toString();
         id=    userid.getText().toString();
         bio=    bios.getText().toString();
         number=     phone.getText().toString();
         gender= spinner.getSelectedItem().toString();

        HashMap udata =new HashMap();
        udata.put("username",username);
        udata.put("id",id);
        udata.put("bio",bio);
        udata.put("number",number);
        udata.put("gender",gender);

     reference.child(cuser).updateChildren(udata).addOnCompleteListener(new OnCompleteListener<Void>() {
         @Override
         public void onComplete(@NonNull Task<Void> task) {
             if (task.isSuccessful()){
                 Toast.makeText(Editprofile.this, "data updated", Toast.LENGTH_SHORT).show();

         }else {
                 Toast.makeText(Editprofile.this, "something went wrong", Toast.LENGTH_SHORT).show();
             }
     }

        });


}
}