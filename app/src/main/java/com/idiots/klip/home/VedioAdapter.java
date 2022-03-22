package com.idiots.klip.home;

import android.content.Context;
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

import com.idiots.klip.R;

import java.util.List;

public class VedioAdapter extends RecyclerView.Adapter<VedioAdapterViewholder>{

    private Context context;
    private List<Vedio> vedioList;

    public VedioAdapter(Context context, List<Vedio> vedioList) {
        this.context = context;
        this.vedioList = vedioList;
    }

    @NonNull
    @Override
    public VedioAdapterViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v   = LayoutInflater.from(context).inflate(R.layout.each_vedio,parent,false);
        return  new VedioAdapterViewholder(v);
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
