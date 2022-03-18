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


public class myviewholder extends FirebaseRecyclerAdapter<Vedio, myviewholder.VedioViewHolder> {

    public myviewholder(@NonNull FirebaseRecyclerOptions<Vedio> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull VedioViewHolder holder, int position, @NonNull Vedio model) {
        holder.setVideoData(model);


    }

    @NonNull
    @Override
    public VedioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_vedio, parent, false);
        return new VedioViewHolder(view);
    }

    public class VedioViewHolder extends RecyclerView.ViewHolder {
        TextView desc;
        TextView title;
        VideoView videoView;
        ProgressBar pbar;
        ImageButton fav;
        boolean isfav = false;


        public VedioViewHolder(View view) {
            super(view);
            videoView = (VideoView) view.findViewById(R.id.vedioview);
            title = (TextView) view.findViewById(R.id.vediotitle);
            desc = (TextView) view.findViewById(R.id.vediodesc);
            pbar = (ProgressBar) view.findViewById(R.id.videoProgressBar);
            fav = (ImageButton) view.findViewById(R.id.btn_like);

        }

        public void setVideoData(Vedio vedio) {
            title.setText(vedio.getTitle());
            desc.setText(vedio.getDesc());
            videoView.setVideoPath(vedio.getUrl());

            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    pbar.setVisibility(View.GONE);
                    mediaPlayer.start();
                    float videoRatio = mediaPlayer.getVideoWidth() / (float) mediaPlayer.getVideoHeight();
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
                        public void onCompletion(MediaPlayer mp1) {
                            mp1.start();

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