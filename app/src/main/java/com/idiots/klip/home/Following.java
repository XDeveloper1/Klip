package com.idiots.klip.home;

import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.idiots.klip.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;


public class Following extends Fragment  {
    ViewPager2 viewPager2;
    BottomNavigationView bottomNavigation;
    String vediourl;
    float x1, x2, y1, y2;
    private static final String TAG= "Swipe Position";
    private static int MIN_DISTANCE =150;
    GestureDetector   gestureDetector;
    List<Vedio> vedioList;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    int pageNumber = 1;
    VedioAdapter vedioAdapter;
    String url = "https://api.pexels.com/videos/popular/?page=" + pageNumber + "&per_page=80";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following, container, false);
        viewPager2 = view.findViewById(R.id.viewpager2);
        vedioList = new ArrayList<>();
        vedioAdapter = new VedioAdapter(getContext(), vedioList);
        viewPager2.setAdapter(vedioAdapter);
        fetchVideofromapi();
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent touchEvent) {
                switch (touchEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x1 = touchEvent.getX();
                        y1 = touchEvent.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = touchEvent.getX();
                        y2 = touchEvent.getY();
                        if (x1 > x2) {
                            Following following = new Following();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.for_you_frame, following);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        } else if (x1 < x2) {
                            Toast.makeText(getContext(), "please swipe right ", Toast.LENGTH_SHORT).show();
                        }
                }


                return false;
            }
        });


        return view;

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
                            System.out.println(url+" "+ "ankits");
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

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);

    }



}



