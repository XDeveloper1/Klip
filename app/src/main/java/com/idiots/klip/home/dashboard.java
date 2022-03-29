package com.idiots.klip.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.idiots.klip.R;
import com.idiots.klip.account.account;
import com.idiots.klip.discover.discover;
import com.idiots.klip.inbox.inbox;
import com.idiots.klip.klip.klip;

import java.util.List;

public class dashboard extends AppCompatActivity {

    ViewPager pager;
    VedioAdapter vedioAdapter ;
    TabLayout tabLayout;
    ViewPager viewPager;
    VideoView videoView;
    BottomNavigationView bottomNavigation;
    String vediourl;
    List<Vedio> vedioList;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    int pageNumber = 1;
    String url = "https://api.pexels.com/videos/popular/?page=" + pageNumber + "&per_page=80";




    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_dashboard);
        tabLayout =findViewById(R.id.tablayout);
        pager =findViewById(R.id.viewpagerdashboard);
        tabLayout.setupWithViewPager(viewPager);
        videoView=findViewById(R.id.vedioviewforyou);
        slideadapter adapter = new slideadapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragments(new For_you(),"for_you");
        adapter.addFragments(new Following(),"following");
        pager.setAdapter(adapter);


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


    }

}


