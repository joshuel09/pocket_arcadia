package josusama.corp.pocketarcadia;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

public class Pulispangkalawakan extends AppCompatActivity {



    private SharedPreferencesManager mSP;
    private Button Botton, Buttons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulispangkalawakan);


        //set fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        TextView textView = (TextView) findViewById(R.id.score);
        TextView textViews = (TextView) findViewById(R.id.scores);
        Botton = (Button)findViewById(R.id.Button);
        Buttons= (Button)findViewById(R.id.Buttons);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/DisposableDroidBB.ttf");
        textView.setTypeface(typeface);
        textViews.setTypeface(typeface);
        Botton.setTypeface(typeface);
        Buttons.setTypeface(typeface);

        mSP = new SharedPreferencesManager(Pulispangkalawakan.this);

        textView.setText("" + mSP.getHighScore());

        //set fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //Video
        final VideoView videoview = (VideoView) findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.arcadeoneever);
        videoview.setVideoURI(uri);
        videoview.start();
        videoview.setZOrderOnTop(true);

        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                videoview.setZOrderOnTop(true);
                videoview.start(); //need to make transition seamless.
            }
        });

        final Button button = findViewById(R.id.Button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i;
                i = new Intent(Pulispangkalawakan.this,Pulis_main.class); startActivity(i);
            }
        });

        final Button buttons = findViewById(R.id.Buttons);
        buttons.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i;
                i = new Intent(Pulispangkalawakan.this,Arcadeonetut.class); startActivity(i);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();

        //Video
        final VideoView videoview = (VideoView) findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.arcadeoneever);
        videoview.setVideoURI(uri);
        videoview.start();
        videoview.setZOrderOnTop(true);

        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                videoview.setZOrderOnTop(true);
                videoview.start(); //need to make transition seamless.
            }
        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Pulispangkalawakan.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
