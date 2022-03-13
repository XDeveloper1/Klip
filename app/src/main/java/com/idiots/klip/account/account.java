package com.idiots.klip.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.idiots.klip.R;
import com.idiots.klip.discover.discover;
import com.idiots.klip.home.dashboard;
import com.idiots.klip.inbox.inbox;
import com.idiots.klip.klip.klip;

public class account extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    Button im1,im2;
    TextView username;
    TextView followers;
    TextView following;
    TextView likes;
    TextView bio;
    Button editprofile;
    FirebaseDatabase db;
    String number,id,biodata ,gender,dob,photo,usernames,followerss,followings,likess,phonenumber,email;
    String cuser;
    FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        bottomNavigation=findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(R.id.account);
        bio=findViewById(R.id.bio);
        followers=findViewById(R.id.followers);
        following=findViewById(R.id.following);
        im1=findViewById(R.id.like_button);
        im2=findViewById(R.id.dashboard_button);
        likes=findViewById(R.id.likes);
        editprofile=findViewById(R.id.edit_button);
         db= FirebaseDatabase.getInstance();
          username=findViewById(R.id.id);
          cuser =user.getPhoneNumber();
          reference=FirebaseDatabase.getInstance().getReference("users");
          reference.child(cuser).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               followerss=dataSnapshot.child("followers").getValue(String.class);
               followings=dataSnapshot.child("following").getValue(String.class);
               likess=dataSnapshot.child("likes").getValue(String.class);
               usernames=dataSnapshot.child("username").getValue(String.class);
            biodata = dataSnapshot.child("bio").getValue(String.class);

            bio.setText(biodata);
            username.setText(usernames);
            followers.setText(followerss);
            following.setText(followings);
            likes.setText(likess);

           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {
               Toast.makeText(account.this, error.getMessage(), Toast.LENGTH_SHORT).show();

           }
       });
       im1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               com.idiots.klip.account.likes likes =new likes();
               loadlikefragment(likes);
           }
       });
       im2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               com.idiots.klip.account.dashboard dashboard =new com.idiots.klip.account.dashboard();
               loaddashfragments(dashboard);

           }
       });
        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
                     startActivity(new Intent(getApplicationContext(), dashboard.class));
                     overridePendingTransition(0,0);
                        return true;

                    case R.id.discover:
                        startActivity(new Intent(getApplicationContext(), discover.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.Klip:
                        startActivity(new Intent(getApplicationContext(), klip.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.inbox:
                        startActivity(new Intent(getApplicationContext(), inbox.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.account:
                        return true;


                }

                return false;
            }
        });
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),Editprofile.class);
                startActivity(intent);

            }
        });


    }

    private void loadlikefragment(com.idiots.klip.account.likes likes) {
        FragmentManager fragmentManager =getSupportFragmentManager();
        FragmentTransaction transaction =fragmentManager.beginTransaction();
        transaction.replace(R.id.frag_container,likes);
//        transaction.remove(likes);
        transaction.commit();
        Toast.makeText(this, "likes start", Toast.LENGTH_SHORT).show();

    }

    private void loaddashfragments(com.idiots.klip.account.dashboard dashboard) {
        FragmentManager fragmentManager =getSupportFragmentManager();
        FragmentTransaction transaction =fragmentManager.beginTransaction();
        transaction.replace(R.id.frag_container,dashboard);
//        transaction.remove(dashboard);
        transaction.commit();
        Toast.makeText(this, "dash started", Toast.LENGTH_SHORT).show();
    }


}