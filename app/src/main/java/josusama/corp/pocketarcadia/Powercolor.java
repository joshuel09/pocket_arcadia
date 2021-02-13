package josusama.corp.pocketarcadia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

public class Powercolor extends AppCompatActivity {

    public final static String DIFFICULTY_MESSAGE = "Default";
    private RelativeLayout relLayout;
    private TextView playerScoreTextView,difficulty,slower,faster,playerScoreText;
    private String playerScoreMessage = "0";
    private SeekBar seekBar;
    private Button Button, Buttons;
    private int difficultyValue = 0;
    private final static String HIGH_SCORE = "Highscore: ";
    private Powercolor_save sps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/DisposableDroidBB.ttf");
        setContentView(R.layout.activity_powercolor);

        //set fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


        relLayout = new RelativeLayout(this);
        relLayout.setBackgroundColor(Color.CYAN);

        difficulty = (TextView)findViewById(R.id.textViewDifficulty);
        slower = (TextView)findViewById(R.id.textViewSlow);
        faster = (TextView)findViewById(R.id.textViewFast);
        Button = (Button)findViewById(R.id.buttonPlayGame);
        Buttons = (Button)findViewById(R.id.Buttons);

        difficulty.setTypeface(typeface);
        slower.setTypeface(typeface);
        faster.setTypeface(typeface);
        Button.setTypeface(typeface);
        Buttons.setTypeface(typeface);


        playerScoreText = (TextView)findViewById(R.id.textViewMain);
        playerScoreText.setTypeface(typeface);




        sps = new Powercolor_save(Powercolor.this);

        final Button buttons = findViewById(R.id.Buttons);
        buttons.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i;
                i = new Intent(Powercolor.this,arcadetwotut.class); startActivity(i);
            }
        });

        //Video
        final VideoView videoview = (VideoView) findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.arcadetwover);
        videoview.setVideoURI(uri);
        videoview.start();
        videoview.setZOrderOnTop(true);

        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                videoview.setZOrderOnTop(true);
                videoview.start(); //need to make transition seamless.
            }
        });

        // Fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        SharedPreferences prefs = this.getSharedPreferences("myPrefsKey", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        int oldHighScore = prefs.getInt("key", 0); //9000 is the default value

        seekBar = (SeekBar)findViewById(R.id.difficultyBar);
        seekBar.setProgress(0);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser)
            {
                difficultyValue = progressValue;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {}
        });

        if(getIntent().getStringExtra(Powercolor_main.EXTRA_MESSAGE) != null)
        {
            playerScoreMessage = getIntent().getStringExtra(Powercolor_main.EXTRA_MESSAGE);
            //Log.e("playerScore getIntent", playerScoreMessage);

            if(Integer.parseInt(playerScoreMessage) > oldHighScore)
            {
                editor.putInt("key", Integer.parseInt(playerScoreMessage));
                editor.apply();
            }
            else
            {
                editor.putInt("key", oldHighScore);
                editor.commit();
            }
        }

        playerScoreTextView = (TextView)findViewById(R.id.textViewMainScore);
        playerScoreTextView.setTypeface(typeface);

        playerScoreTextView.setText("" + sps.getScore());
    }


    public void gameStart(View view)
    {
        //Log.e("Difficulty value", "Diffculty value: " + difficultyValue);
        Intent intent = new Intent(this, Powercolor_main.class);
        String toSend = "" + difficultyValue;
        intent.putExtra(DIFFICULTY_MESSAGE, toSend);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();

        //Video
        final VideoView videoview = (VideoView) findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.arcadetwover);
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
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Powercolor.this, MainActivity.class);
        startActivity(intent);
        finish();
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

}