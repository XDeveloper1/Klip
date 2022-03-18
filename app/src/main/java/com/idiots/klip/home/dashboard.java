package com.idiots.klip.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.idiots.klip.R;
import com.idiots.klip.account.account;
import com.idiots.klip.discover.discover;
import com.idiots.klip.inbox.inbox;
import com.idiots.klip.klip.klip;

public class dashboard extends AppCompatActivity {

    ViewPager2 viewPager2;
    BottomNavigationView bottomNavigation;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    myviewholder adapter;



    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_dashboard);
        viewPager2=findViewById(R.id.viewpager2);

        bottomNavigation = findViewById(R.id.bottom_navigation);


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
                        startActivity(new Intent(getApplicationContext(), account.class));
                        overridePendingTransition(0, 0);
                        return true;

                }

                return false;
            }
        });
        FirebaseRecyclerOptions<Vedio> options =
                new FirebaseRecyclerOptions.Builder<Vedio>().setQuery(FirebaseDatabase.getInstance().getReference().child("users").child("unidata"), Vedio.class).build();
        adapter=new myviewholder(options);
        viewPager2.setAdapter(adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}


