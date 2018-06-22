package tashfik.youtube.view;

import android.content.Intent;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.Timer;
import java.util.TimerTask;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class MainActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{

    public static final String API_KEY = "AIzaSyDyt7bk_QF7q2qBUm1rTyCo7zgEM2LrSnc";
    public static final String VIDEO_ID = "BtvSbq-UPLM";
    YouTubePlayer player;
    int c=0;
    TextView tvCounter,tvIP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCounter=(TextView)findViewById(R.id.counter);
        tvIP=(TextView)findViewById(R.id.ip);

        YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.yt);
        youTubePlayerView.initialize(API_KEY, this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                c++;
                start();
                player.play();
                Snackbar.make(view, "Started.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //tvCounter.setText(""+c);

            }
        });



    }


    public void start(){

        Timer t = new Timer();
//Set the schedule function and rate
        t.scheduleAtFixedRate(new TimerTask() {
                                  @Override
                                  public void run() {
                                  restart();
                                  }},
//Set how long before to start calling the TimerTask (in milliseconds)
                0,
//Set the amount of time between each execution (in milliseconds)
                30000);
    }

    public void restart(){

        c++;
        if(c>10){
           // changeIp();
        }
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);
        player.cueVideo(VIDEO_ID);
        player.play();
         Log.e("Roger",""+c);


        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        Log.e("Roger",""+ip);

    }

    private void changeIp() {
        android.provider.Settings.System.putString(getContentResolver(), android.provider.Settings.System.WIFI_USE_STATIC_IP, "0");
        android.provider.Settings.System.putString(getContentResolver(), android.provider.Settings.System.WIFI_STATIC_DNS1, "192.168.0.2");
        android.provider.Settings.System.putString(getContentResolver(), android.provider.Settings.System.WIFI_STATIC_DNS2, "192.168.0.3");
        android.provider.Settings.System.putString(getContentResolver(), android.provider.Settings.System.WIFI_STATIC_GATEWAY, "192.168.0.1");
        android.provider.Settings.System.putString(getContentResolver(), android.provider.Settings.System.WIFI_STATIC_NETMASK, "255.255.255.0");
        android.provider.Settings.System.putString(getContentResolver(), android.provider.Settings.System.WIFI_STATIC_IP, "1");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this,Settings.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
       // Toast.makeText(this, "Failured to Initialize!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {

        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);
        this.player=player;

        if (!wasRestored) {
            player.cueVideo(VIDEO_ID);

        }
    }

    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onBuffering(boolean arg0) {
        }
        @Override
        public void onPaused() {
        }
        @Override
        public void onPlaying() {
        }
        @Override
        public void onSeekTo(int arg0) {
        }
        @Override
        public void onStopped() {
        }
    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onAdStarted() {
        }
        @Override
        public void onError(YouTubePlayer.ErrorReason arg0) {
        }
        @Override
        public void onLoaded(String arg0) {
        }
        @Override
        public void onLoading() {
        }
        @Override
        public void onVideoEnded() {
        }
        @Override
        public void onVideoStarted() {
        }
    };


}
