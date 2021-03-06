package com.example.usama.homeautomation.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.usama.homeautomation.R;

import java.util.Timer;
import java.util.TimerTask;

public class CameraActivity extends AppCompatActivity implements View.OnClickListener {


    SharedPreferences sharedPreferences;
    String videoURL = "http://192.168.0.183/video.cgi";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        sharedPreferences = getSharedPreferences("CameraIP", MODE_PRIVATE);
        videoURL = sharedPreferences.getString("IP", "192.168.10.18");

        Fragment fragment = new VideoViewFragment();
//
        Bundle data = new Bundle();//Use bundle to pass data
        data.putString("IP", videoURL);//put string, int, etc in bundle with a key value
        fragment.setArguments(data);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main, fragment).commit();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.camera_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_ip) {

            AlertDialog.Builder builder = new AlertDialog.Builder(CameraActivity.this);
            View view = LayoutInflater.from(CameraActivity.this).inflate(R.layout.new_dialouge, null);
            builder.setView(view);
            final EditText editText = new EditText(CameraActivity.this);
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setTitle("Enter Camera IP");
            builder.setView(editText);
            builder.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {

                    sharedPreferences = getSharedPreferences("CameraIP", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("IP",editText.getText().toString().trim());
                    editor.apply();
                    editor.commit();

                    Intent intent= getIntent();
                    finish();
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("Exit", new DialogInterface.OnClickListener()

            {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        finish();
    }
}
