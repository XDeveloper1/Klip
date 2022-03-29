package com.idiots.klip.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.idiots.klip.R;

import java.util.List;

public class VedioAdapter extends RecyclerView.Adapter<VedioAdapterViewholder> {

    private Context context;
    private List<Vedio> vedioList;

    public VedioAdapter(Context context, List<Vedio> vedioList) {
        this.context = context;
        this.vedioList = vedioList;
    }


    @NonNull
    @Override
    public VedioAdapterViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.each_vedio, parent, false);
        return new VedioAdapterViewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VedioAdapterViewholder holder, int position) {
        holder.setVideoData(vedioList.get(position));
//        Glide.with(context).load(vedioList.get(position).getPicture()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return vedioList.size();
    }
}

class VedioAdapterViewholder extends RecyclerView.ViewHolder {
    TextView desc;
    TextView title;
    VideoView videoView;
    ProgressBar pbar;
    ImageButton fav;
    boolean isfav = false;

    public VedioAdapterViewholder(@NonNull View view) {
        super(view);
        videoView = (VideoView) view.findViewById(R.id.vedioviewforyou);
        title = (TextView) view.findViewById(R.id.vediotitle);
        desc = (TextView) view.findViewById(R.id.vediodesc);
        pbar = (ProgressBar) view.findViewById(R.id.videoProgressBar);
        fav = (ImageButton) view.findViewById(R.id.btn_like);
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setVideoData(Vedio vedio) {
        title.setText(vedio.getTitle());
        desc.setText(vedio.getDesc());
        videoView.setVideoPath(vedio.getUrl());

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                pbar.setVisibility(View.GONE);
                mp.start();
                float videoRatio = mp.getVideoWidth() / (float) mp.getVideoHeight();
                float screenRatio = videoView.getWidth() / (float) videoView.getHeight();
                float scale = videoRatio / screenRatio;

                if (scale >= 1f) {
                    videoView.setScaleX(scale);
                } else {
                    videoView.setScaleY(1f / scale);
                }
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.start();

                    }
                });
            }
        });
        videoView.setOnTouchListener(new View.OnTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(videoView.getContext(),new GestureDetector.SimpleOnGestureListener(){
                @Override
                public void onLongPress(MotionEvent e) {
                    videoView.stopPlayback();
                    super.onLongPress(e);
                }
            });
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);
                return false;
            }
        });
        videoView.setOnClickListener(new View.OnClickListener() {
            int i = 0;

            @Override
            public void onClick(View view) {
                i++;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (i == 1) {
                            videoView.resume();
                        } else if (i == 2) {
                            fav.setImageResource(R.drawable.ic_baseline_favorite_24);
                            isfav = true;
                        }
                        i = 0;

                    }
                }, 0);
            }
        });
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isfav) {
                    fav.setImageResource(R.drawable.ic_baseline_favorite_24);
                    isfav = true;
                } else {
                    fav.setImageResource(R.drawable.icon_like_home);
                    isfav = false;
                }
            }
        });

    }
}
