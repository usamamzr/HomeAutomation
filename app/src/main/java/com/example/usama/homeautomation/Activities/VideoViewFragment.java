package com.example.usama.homeautomation.Activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.usama.homeautomation.R;

import java.net.HttpURLConnection;
import java.net.URL;

public class VideoViewFragment extends Fragment {

    // Declare variables
//        VideoView videoview;
//    MainActivity main = new MainActivity();
    private static final String TAG = "MjpegActivity";

    private MjpegView mv;
    // Physical display width and height.
    private static int displayWidth = 0;
    private static int displayHeight = 0;

    // Video URL
//    public String path = main.Path;
//    String VideoURL = path + "Video1.mp4";
//        String VideoURL = "http://192.168.43.1:8080";
    String URL=null;
    //sample public cam
//    String URL = "http://192.168.43.134:5432/XMLParser/Video1.mp4";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.video_view, container, false);
//        View view = inflater.inflate(R.layout.video_view,
//                container, false);
//        return view;
        if(getArguments()!=null){
            URL=getArguments().getString("IP");
        }
        else{
           URL  = "http://192.168.8.107/video.cgi";
            Toast.makeText(viewGroup.getContext(), "IP NOT FOUND", Toast.LENGTH_SHORT).show();
        }
        mv = (MjpegView) viewGroup.findViewById(R.id.surfaceView);
        new DoRead().execute(URL);
        return viewGroup;
//        mv = new MjpegView(this.getContext());
    }

    public void onPause(){
        super.onPause();
        mv.stopPlayback();
    }

    public void onResume(){
        super.onResume();
    }

    public class DoRead extends AsyncTask<String, Void, MjpegInputStream> {
        protected MjpegInputStream doInBackground(String... Url) {
            //TODO: if camera has authentication deal with it and don't just not work

//            HttpResponse res = null;
//            DefaultHttpClient httpclient = new DefaultHttpClient();
            Log.d(TAG, "1. Sending http request");
            try {
                java.net.URL url = new URL(Url[0]); // here is your URL path
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                int responseCode=conn.getResponseCode();

//                res = httpclient.execute(new HttpGet(URI.create(url[0])));
//                Log.d(TAG, "2. Request finished, status = " + res.getStatusLine().getStatusCode());
                Log.d(TAG, "2. Request finished, status = " + responseCode);
                if(responseCode==401){
                    //You must turn off camera User Access Control before this will work
                    return null;
                }
                return new MjpegInputStream(conn.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
                Log.d(TAG, "Request failed-ClientProtocolException", e);
                //Error connecting to camera
            }
            return null;
        }

        protected void onPostExecute(MjpegInputStream result) {
            if(result != null) {
                mv.setSource(result);
                mv.setDisplayMode(MjpegView.SIZE_BEST_FIT);
                mv.showFps(true);
            }
        }
    }
}
