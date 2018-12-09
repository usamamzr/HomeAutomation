package com.example.usama.homeautomation.Activities;

import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import com.example.usama.homeautomation.Adapters.FloorsRecyclerAdapter;
import com.example.usama.homeautomation.Models.Floor;
import com.example.usama.homeautomation.R;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class FloorsActivity extends AppCompatActivity implements View.OnClickListener {

    VideoView videoView;
    Button btnPlayPause;
//    String videoURL = "http://192.168.0.183/image/jpeg.cgi";
    String videoURL = "http://192.168.0.183/video.cgi";

    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floors);

        videoView = findViewById(R.id.videoView);
//        btnPlayPause = findViewById(R.id.btnPlayPause);
//        btnPlayPause.setOnClickListener(FloorsActivity.this);

        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                // If you want to modify a view in your Activity
                FloorsActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!videoView.isPlaying()) {
                                Uri uri = Uri.parse(videoURL);
                                videoView.setVideoURI(uri);
                                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                    @Override
                                    public void onCompletion(MediaPlayer mediaPlayer) {
//                                        btnPlayPause.setText("Play");
                                    }
                                });
                            } else {
                                videoView.pause();
                            }
                        } catch (Exception ex) {

                        }
                        videoView.requestFocus();
                        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mediaPlayer) {
                                mediaPlayer.setLooping(true);
                                videoView.start();
                            }
                        });
                    }

                });
            }
        }, 5000, 5000); // initial delay 1 milisecond, interval 1 secon
    }

    @Override
    public void onClick(View view) {

    }
}
