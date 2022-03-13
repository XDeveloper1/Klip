package com.idiots.klip.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
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
import com.idiots.klip.inbox.inbox;
import com.idiots.klip.klip.klip;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class dashboard extends AppCompatActivity {
    private VedioAdapter adapter;
    BottomNavigationView bottomNavigation;
    String cuser, url;
    ViewPager2 viewPager2;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference reference;
    RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_dashboard);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(R.id.home);
        reference = FirebaseDatabase.getInstance().getReference().child("users");
        cuser = user.getPhoneNumber();
        viewPager2 = findViewById(R.id.viewpager2);
        addingdata();
        mQueue = Volley.newRequestQueue(this);


//        String furl = "https://firebasestorage.googleapis.com/v0/b/klip-b2755.appspot.com/o/1646637316065.mp4?alt=media&token=1843c5a4-393f-443b-8965-73b28a693873";
//        List<Vedio> vedioList = new ArrayList<>();
//        Vedio eachvedio = new Vedio();
//        eachvedio.vediourl = "https://firebasestorage.googleapis.com/v0/b/klip-b2755.appspot.com/o/1646804157539.mp4?alt=media&token=c028eb57-249e-4a51-867c-1a42c90f451e";
//        eachvedio.desc = "coded by ankit prajapati";
//        eachvedio.title = "coded by ankit prajapati";
//        vedioList.add(eachvedio);
//
//        Vedio aeachvedio = new Vedio();
//        aeachvedio.vediourl = "https://firebasestorage.googleapis.com/v0/b/klip-b2755.appspot.com/o/1646804157539.mp4?alt=media&token=c028eb57-249e-4a51-867c-1a42c90f451e";
//        aeachvedio.desc = "coded by ankit prajapati";
//        aeachvedio.title = "coded by ankit prajapati";
//        vedioList.add(aeachvedio);
//
//        Vedio baeachvedio = new Vedio();
//        baeachvedio.vediourl = "https://firebasestorage.googleapis.com/v0/b/klip-b2755.appspot.com/o/1646804157539.mp4?alt=media&token=c028eb57-249e-4a51-867c-1a42c90f451e";
//        baeachvedio.desc = "coded by ankit prajapati";
//        baeachvedio.title = "coded by ankit prajapati";
//        vedioList.add(baeachvedio);
//        viewPager2.setAdapter(new VedioAdapter(vedioList));


        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        return true;

                    case R.id.discover:
                        startActivity(new Intent(getApplicationContext(), discover.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.Klip:
                        startActivity(new Intent(getApplicationContext(), klip.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.inbox:
                        startActivity(new Intent(getApplicationContext(), inbox.class));
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.account:
                        return true;

                }

                return false;
            }
        });
    }

    private void addingdata() {

        reference.child("vediolink").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String urls = dataSnapshot.getValue().toString();
                StringTokenizer tokenizer = new StringTokenizer(urls, ",");
                ArrayList<String>arrayList=new ArrayList<>();
                while (tokenizer.hasMoreTokens()) {
                    String data = tokenizer.nextToken();
                    newdata(data);
                    System.out.println(newdata(data));
                     arrayList.add(newdata(data));
                }
               for (int i=0;i<=arrayList.size()-1;i++){
                   List<Vedio> vedioList = new ArrayList<>();
                   Vedio eachvedio = new Vedio();
                   eachvedio.vediourl = arrayList.get(i);
                   eachvedio.desc = "coded by ankit prajapati";
                   eachvedio.title = "coded by ankit prajapati";
                   vedioList.add(eachvedio);
                   viewPager2.setAdapter(new VedioAdapter(vedioList));
               }
            }

            private String newdata(String data) {
                StringBuilder str = new StringBuilder(data);
                String new_data = str.substring(data.indexOf("=") + 1);
                return new_data;


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}