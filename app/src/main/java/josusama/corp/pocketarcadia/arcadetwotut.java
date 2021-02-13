package josusama.corp.pocketarcadia;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class arcadetwotut extends AppCompatActivity {

    TextView how, play;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arcadetwotut);


        //set fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/DisposableDroidBB.ttf");



        how = (TextView)findViewById(R.id.how);
        play = (TextView)findViewById(R.id.play);

        how.setTypeface(typeface);
        play.setTypeface(typeface);




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(arcadetwotut.this, Powercolor.class);
        startActivity(intent);
        finish();
    }
}
