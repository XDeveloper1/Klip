package com.idiots.klip.inbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.idiots.klip.R;
import com.idiots.klip.account.account;
import com.idiots.klip.discover.discover;
import com.idiots.klip.home.dashboard;
import com.idiots.klip.klip.klip;

public class inbox extends AppCompatActivity {
    BottomNavigationView bottomNavigation;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(R.id.inbox);

        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), dashboard.class));
                        overridePendingTransition(0, 0);
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
//                        startActivity(new Intent(getApplicationContext(),inbox.class));
//                        overridePendingTransition(0,0);
                        return true;
                    case R.id.account:
                        startActivity(new Intent(getApplicationContext(), account.class));
                        overridePendingTransition(0, 0);
                        return true;


                }

                return false;
            }
        });
    }
}