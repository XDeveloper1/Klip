package com.idiots.klip.home;

import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.idiots.klip.R;

public class followingAdapter extends FirebaseRecyclerAdapter<fVideo, followingAdapter.followingviewholder> {

    public followingAdapter(@NonNull FirebaseRecyclerOptions<fVideo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull followingviewholder holder, int position, @NonNull fVideo model) {
        holder.setVideoData(model);
    }

    @NonNull
    @Override
    public followingviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.followings_fv, parent, false);
        return new followingviewholder(view);
    }


    class followingviewholder extends RecyclerView.ViewHolder {
        TextView desc;
        TextView title;
        VideoView videoView;
        VideoView video;
        ProgressBar pbar;
        ImageButton fav;
        boolean isfav = false;

        public followingviewholder(@NonNull View view) {
            super(view);
            videoView = (VideoView) view.findViewById(R.id.vedioviewf);
            title = (TextView) view.findViewById(R.id.vediotitlef);
            desc = (TextView) view.findViewById(R.id.vediodescf);
            pbar = (ProgressBar) view.findViewById(R.id.videoProgressBarf);
            fav = (ImageButton) view.findViewById(R.id.btn_likef);
            video =view.findViewById(R.id.vedioviewforyou);


        }

        public void setVideoData(fVideo fVideo) {
            title.setText(fVideo.getTitle());
            desc.setText(fVideo.getDesc());
            videoView.setVideoPath(fVideo.getUrl());

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
}
