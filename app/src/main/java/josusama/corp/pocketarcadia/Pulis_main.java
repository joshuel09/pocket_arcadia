package josusama.corp.pocketarcadia;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class Pulis_main extends AppCompatActivity implements SensorEventListener {

    private GameView mGameView;
    private float mXTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        Display display = getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        Log.d("X and Y size", "X = " + point.x + ", Y = " + point.y);

        mGameView = new GameView(this, point.x, point.y);
        setContentView(mGameView);

        //Sensor Accelerometer
        SensorManager manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
        manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mGameView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGameView.pause();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        mXTemp = event.values[0];

        if (event.values[0] > 1){
            mGameView.steerLeft(event.values[0]);
        }
        else if (event.values[0] < -1){
            mGameView.steerRight(event.values[0]);
        }else{
            mGameView.stay();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Pulis_main.this, Pulispangkalawakan.class);
        startActivity(intent);
        finish();
    }
}