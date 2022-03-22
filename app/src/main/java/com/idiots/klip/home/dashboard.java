package com.idiots.klip.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.idiots.klip.R;
import com.idiots.klip.account.account;
import com.idiots.klip.discover.discover;
import com.idiots.klip.inbox.inbox;
import com.idiots.klip.klip.klip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class dashboard extends AppCompatActivity {

    ViewPager2 viewPager2;
    BottomNavigationView bottomNavigation;
    String vediourl;
    List<Vedio> vedioList;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    int pageNumber = 1;
    String url = "https://api.pexels.com/videos/popular/?page=" + pageNumber + "&per_page=80";

    VedioAdapter vedioAdapter;


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_dashboard);
        viewPager2 = findViewById(R.id.viewpager2);
        vedioList = new ArrayList<>();
        vedioAdapter = new VedioAdapter(this, vedioList);
        viewPager2.setAdapter(vedioAdapter);

        bottomNavigation = findViewById(R.id.bottom_navigation);

        fetchVideofromapi();
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
//        FirebaseRecyclerOptions<Vedio> options =
//                new FirebaseRecyclerOptions.Builder<Vedio>().setQuery(FirebaseDatabase.getInstance().getReference().child("users").child("unidata"), Vedio.class).build();
//        adapter = new VedioAdapter(options);
//        viewPager2.setAdapter(adapter);


    }

    private void fetchVideofromapi() {
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("videos");

                    int length1 = jsonArray.length();

                    for (int i = 0; i < length1; i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        String title = object.getString("id");
                        String desc = object.getString("duration");

                        JSONArray jsonArray1pic = object.getJSONArray("video_pictures");
                        int len = jsonArray1pic.length();

                        for (int r = 0; r < 1; r++) {
                            JSONObject obj = jsonArray1pic.getJSONObject(r);
                            String picture = obj.getString("picture");


                            JSONArray jsonArray1 = object.getJSONArray("video_files");

                            int length = jsonArray1.length();

                            for (int k = 0; k < length - 1; k++) {
                                JSONObject object1 = jsonArray1.getJSONObject(k);
                                String s = object1.getString("link");
                                if (s.contains(".sd")) {
                                    int sl = s.indexOf("h");
                                    int hdl = s.indexOf("&pro");
                                    vediourl = s.substring(sl, hdl);
                                    break;
                                }
                            }
                            String url = "";
                            String allWords[];
                            String data = vediourl;
                            allWords = data.split(" ");
                            LinkedHashSet<String> set = new LinkedHashSet<String>(Arrays.asList(allWords));
                            for (String word : set) {
                                url = url + word + " ";
                            }
                            Vedio v = new Vedio(title, desc, url);
                            vedioList.add(v);


                        }

                    }


                    vedioAdapter.notifyDataSetChanged();
                    pageNumber++;

                } catch (JSONException e) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", "563492ad6f91700001000001c53e0cd3077f4ed4a243129926dd31c9");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);

    }


}


