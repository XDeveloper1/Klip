package com.idiots.klip.home;

import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.recyclerview.widget.RecyclerView;

import com.idiots.klip.R;

import java.util.List;


public class VedioAdapter extends RecyclerView.Adapter<VedioAdapter.VedioViewHolder> {
    List<Vedio> videoList;

    public VedioAdapter(List<Vedio> vedioList) {
        videoList = vedioList;
    }

    @Override
    public VedioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_vedio,parent,false);
    return new VedioViewHolder(view);

    }

    public void onBindViewHolder(VedioViewHolder holder , int position) {
        holder.setVideoData(videoList.get(position));
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }


    public class VedioViewHolder extends RecyclerView.ViewHolder {
        TextView desc;
        TextView title;
        VideoView videoView;
//        ProgressBar progressBar;

        public VedioViewHolder(View view) {
            super(view);
            videoView = (VideoView) view.findViewById(R.id.vedioview);
            title = (TextView) view.findViewById(R.id.vediotitle);
            desc = (TextView) view.findViewById(R.id.vediodesc);
//            progressBar=view.findViewById(R.id.eachvedioprogressbar);
        }

        public void setVideoData(Vedio vedio) {
            title.setText(vedio.getTitle());
           desc.setText(vedio.getDesc());
           videoView.setVideoPath(vedio.getVediourl());

            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                    float videoRatio = mediaPlayer.getVideoWidth()/(float)mediaPlayer.getVideoHeight();
                    float screenRatio =videoView.getWidth()/(float) videoView.getHeight();
                    float scale = videoRatio/screenRatio;

                    if (scale >= 1f) {
                      videoView.setScaleX(scale);
                    } else {
                        videoView.setScaleY(1f/scale);
                    }
                }
            });
            videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer2) {
                            mediaPlayer2.start();
                        }
                    });
                }
            });
        }
    }
}