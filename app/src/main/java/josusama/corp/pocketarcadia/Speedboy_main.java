package josusama.corp.pocketarcadia;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Speedboy_main extends AppCompatActivity implements SensorEventListener {

    // sound

    MediaPlayer mp;
    MediaPlayer mps;
    MediaPlayer mpss;
    MediaPlayer car_race;
    Intent bgMusic;

    // game

    GameView gv;
    Paint drawPaint = new Paint();
    Bitmap player;
    Bitmap gameOver;
    Rect playerRect;
    List<Bitmap> asteroidList;
    List<Rect> asteroidRectList;
    List<Integer> asteroidYSpeed;
    List<Integer> asteroidXPos;
    List<Integer> asteroidYPos;

    List<Bitmap> bgObjectsList;
    List<Integer> bgObjectsListYSpeed;
    List<Integer> bgObjectsListXPos;
    List<Integer> bgObjectsListYPos;

    private int backgroundColor = Color.LTGRAY;
    private int playerX = 500, playerY = 1200, playerXSpeed = 10, playerYSpeed = 10;
    private int health = 100;  // start with 100 health
    private int Distance = 0;
    private Speedboy_save sps;


    public final static String EXTRA_MESSAGE = "0";

    private Background bg;

    // gyro

    private float x;
    private float y;
    private float z;

    SensorManager sManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bgMusic = new Intent(Speedboy_main.this, MusicPlayer.class);
        startService(bgMusic);

        gv = new GameView(Speedboy_main.this);
        this.setContentView(gv);

        sManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        // initialize lists to store asteroid objects

        asteroidList = new ArrayList<>();
        asteroidRectList = new ArrayList<>();
        asteroidYSpeed = new ArrayList<>();
        asteroidXPos = new ArrayList<>();
        asteroidYPos = new ArrayList<>();

        // initialize lists to store background objects

        bgObjectsList = new ArrayList<>();
        bgObjectsListYSpeed = new ArrayList<>();
        bgObjectsListXPos = new ArrayList<>();
        bgObjectsListYPos = new ArrayList<>();



        bg = new Background(BitmapFactory.decodeResource(getResources(),R.drawable.bg_2));

        // prevent screen dimming as there is little screen tapping involved in this game
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //set fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void update()
    {


    }

    @Override
    public void onAccuracyChanged(Sensor arg0, int arg1)
    {
        //Do nothing.
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        x = event.values[2]; // roll
        y = event.values[1]; // pitch
        z = event.values[0]; // yaw
    }

    @Override
    protected void onPause(){
        super.onPause();
        gv.pause();
        sManager.unregisterListener(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        gv.resume();
        sManager.registerListener(this, sManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_DELAY_FASTEST);
    }

    // --------------------------
    // GameView Class starts here
    // --------------------------

    class GameView extends SurfaceView implements Runnable {

        Thread viewThread = null;
        SurfaceHolder holder;
        Canvas gameCanvas;
        Timer timer;
        boolean threadOK = true;

        public GameView(Context context){
            super(context);
            sps = new Speedboy_save(context);
            holder = this.getHolder();
            timer = new Timer();
            player = BitmapFactory.decodeResource(getResources(), R.drawable.speedboyplayer_2);
            mp = MediaPlayer.create(getApplicationContext(), R.raw.hit_sound);
            mp.setLooping(false);
            mp.setVolume(100,100);

            mps = MediaPlayer.create(getApplicationContext(), R.raw.theme_audio);
            mps.setLooping(true);
            mps.setVolume(100,100);

            mpss = MediaPlayer.create(getApplicationContext(), R.raw.gameover);
            mpss.setLooping(false);
            mpss.setVolume(200,200);

            car_race= MediaPlayer.create(getApplicationContext(), R.raw.car);
            car_race.setLooping(false);
            car_race.setVolume(100,100);

            car_race.start();
        }

        @Override
        public void run(){

            while (threadOK == true){

                if (!holder.getSurface().isValid()){
                    continue;
                }

                gameCanvas = holder.lockCanvas();

                addBgObjects();
                addCarEnemy();
                updateCarRectangles();

                playerRect = new Rect(playerX, playerY, playerX + player.getWidth(), playerY + player.getHeight());

                myOnDraw(gameCanvas);
                holder.unlockCanvasAndPost(gameCanvas);
                bg.update();
                mps.start();


            }
        }

        public void addBgObjects(){

            if (bgObjectsList.size() < 25){

                int randomStartPos = (int)(Math.random() * gameCanvas.getWidth());
                int randomStar = (int)(Math.random() * 1000);

                if (randomStar < 975){
                    bgObjectsList.add(BitmapFactory.decodeResource(getResources(), R.drawable.starline));
                    bgObjectsListYSpeed.add(new Integer((int)(Math.random() * 30 + 50)));
                }

                bgObjectsListXPos.add(new Integer(randomStartPos));
                bgObjectsListYPos.add(new Integer(0));
            }
        }

        public void addCarEnemy(){

            int min = 1;
            int max = 4;

            Random r = new Random();
            int i = r.nextInt(max - min + 1) + min;

            if (asteroidList.size() < i){


                int randomStartPos = (int)(Math.random() * gameCanvas.getWidth());

                asteroidList.add(BitmapFactory.decodeResource(getResources(), R.drawable.enemy_car));
                asteroidRectList.add(new Rect(randomStartPos,-20,20,20));
                asteroidYSpeed.add(new Integer((int)(Math.random() * 30 + 15)));
                asteroidXPos.add(new Integer(randomStartPos+110));
                asteroidYPos.add(new Integer(0));
            }

        }

        public void updateCarRectangles(){
            for (int i = 0; i < asteroidList.size(); i++){
                asteroidRectList.set(i, new Rect(asteroidXPos.get(i), asteroidYPos.get(i), asteroidXPos.get(i) + asteroidList.get(i).getWidth(), asteroidYPos.get(i) + asteroidList.get(i).getHeight()));
            }
        }

        protected void myOnDraw(Canvas canvas){

            drawPaint.setAlpha(255);

            if (health <= 0){

               if (Distance>=sps.getScore()){
                    sps.saveScore(Distance);
               }

                mpss.start();
                mp.stop();
                mps.stop();
                bg.draw(canvas);
                car_race.stop();
                Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/DisposableDroidBB.ttf");
                Paint gameOver = new Paint();
                gameOver.setTextSize(120);
                gameOver.setTextAlign(Paint.Align.CENTER);
                gameOver.setColor(Color.WHITE);
                gameOver.setTypeface(typeface);
                canvas.drawText("Game Over!", (canvas.getWidth()/2), (canvas.getHeight()/2), gameOver);
                Paint bayani = new Paint();
                bayani.setTypeface(typeface);
                bayani.setTextSize(70);
                bayani.setTextAlign(Paint.Align.CENTER);
                bayani.setColor(Color.WHITE);
//                canvas.drawText("Isa Kang Bayani..", (canvas.getWidth()/2),(canvas.getHeight()/2) + 60, bayani);

                Paint highScore = new Paint();

                highScore.setTypeface(typeface);
                highScore.setTextSize(80);
                highScore.setTextAlign(Paint.Align.CENTER);
                highScore.setColor(Color.WHITE);
                canvas.drawText("HighScore : " + sps.getScore()+"m", (canvas.getWidth()/2), (canvas.getHeight()/2) + 250, highScore);

                Paint score = new Paint();
                score.setTypeface(typeface);
                score.setTextSize(70);
                score.setTextAlign(Paint.Align.CENTER);
                score.setColor(Color.WHITE);
                canvas.drawText("Your Score : " + Distance + "m", (canvas.getWidth()/2), (canvas.getHeight()/2) + 150, score);

                drawbgObjects(canvas);
                //  drawAsteroids(canvas);
                // drawPaint.setColor(Color.WHITE);
                // drawPaint.setTextSize(50);

                onCollision();
                updatePlayerPosition(canvas);
                removeAsteroid(canvas);
                removeBgObjects(canvas);

            } else {
                mps.start();
                bg.draw(canvas);
                Distance++;

                drawbgObjects(canvas);
                drawCars(canvas);

                canvas.drawBitmap(player, playerX, playerY, drawPaint);
                drawPaint.setColor(Color.WHITE);
                drawPaint.setTextSize(70);
                Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/DisposableDroidBB.ttf");
                drawPaint.setTypeface(typeface);
                canvas.drawText("Health: " + health, 10, canvas.getHeight() - 30, drawPaint);
                canvas.drawText("Distance: " + Distance + "m", 650, canvas.getHeight() - 30, drawPaint);
                onCollision();
                updatePlayerPosition(canvas);
                removeAsteroid(canvas);
                removeBgObjects(canvas);
            }
        }

        // redraw asteroids
        public void drawCars(Canvas canvas){
            for (int i = 0; i < asteroidList.size(); i++){
                asteroidYPos.set(i, new Integer(asteroidYPos.get(i) + asteroidYSpeed.get(i)));
                canvas.drawBitmap(asteroidList.get(i), asteroidXPos.get(i), asteroidYPos.get(i), drawPaint);
            }
        }

        // redraw background objects
        public void drawbgObjects(Canvas canvas){
            for (int i = 0; i < bgObjectsList.size(); i++){
                bgObjectsListYPos.set(i, new Integer(bgObjectsListYPos.get(i) + bgObjectsListYSpeed.get(i)));
                canvas.drawBitmap(bgObjectsList.get(i), bgObjectsListXPos.get(i), bgObjectsListYPos.get(i), drawPaint);


            }


        }

        // handle collision
        public void onCollision(){
            for (int i = 0; i < asteroidList.size(); i++){
                if (Rect.intersects(playerRect, asteroidRectList.get(i))){

                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                    }else{
                        //deprecated in API 26
                        v.vibrate(500);
                    }
                    mp.start();
                    health -= 2;
                    backgroundColor = Color.DKGRAY;
                    player = BitmapFactory.decodeResource(getResources(), R.drawable.speedboyplayer_hit);
                    timer.schedule(new CollisionTask(), 100);
                }
            }
        }

        // only handles restart button when health gets below 0
        @Override
        public boolean onTouchEvent(MotionEvent event)
        {
            if (health > 0){
                return true;
            }

            switch(event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    playerX = 500;
                    playerY = 1200;
                    playerXSpeed = 10;
                    playerYSpeed = 10;
                    health = 100;  // start with 100 health

                    asteroidList.clear();
                    asteroidRectList.clear();
                    asteroidYSpeed.clear();
                    asteroidXPos.clear();
                    asteroidYPos.clear();
                    mpss.stop();
                    car_race.stop();

                    displayWelcome();
            }
            return false;
        }

        public void updatePlayerPosition(Canvas canvas){

            // set player speed
            playerXSpeed = Math.round(x);
            playerYSpeed = Math.round(y) + 30; // add 30 so that the user can have the phone on an angle without the UFO moving (more natural)

            // update player position
            if ((playerX < 0 && playerXSpeed > 0) || (playerX > (canvas.getWidth() - player.getWidth()) && playerXSpeed < 0)){
                // do nothing
            } else {
                playerX += playerXSpeed * -2;
            }

            if ((playerY < 0 && playerYSpeed > 0) || (playerY > (canvas.getHeight() - player.getHeight()) && playerYSpeed < 0)){
                // do nothing
            } else {
                playerY += playerYSpeed * -2;
            }
        }

        // remove asteroid when it passes bottom of the screen
        public void removeAsteroid(Canvas canvas){
            for (int i = 0; i < asteroidList.size(); i++){
                if (asteroidYPos.get(i) > canvas.getHeight() + 30){
                    asteroidList.remove(i);
                    asteroidYSpeed.remove(i);
                    asteroidYPos.remove(i);
                    asteroidXPos.remove(i);
                    asteroidRectList.remove(i);


                }
            }
        }

        // remove background objects when they pass bottom of the screen
        public void removeBgObjects(Canvas canvas){
            for (int i = 0; i < bgObjectsList.size(); i++){
                if (bgObjectsListYPos.get(i) > canvas.getHeight() + 30){
                    bgObjectsList.remove(i);
                    bgObjectsListYSpeed.remove(i);
                    bgObjectsListYPos.remove(i);
                    bgObjectsListXPos.remove(i);
                }
            }
        }

        public void pause(){
            threadOK = false;

            while (true){
                try {
                    viewThread.join();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                break;
            }

            viewThread = null;
        }

        public void resume(){

            threadOK = true;
            viewThread = new Thread(this);
            viewThread.start();
        }

        private void displayWelcome() {
            //Sending the playerScore to the welcome screen activity

            Intent intent = new Intent(Speedboy_main.this, Speedboy.class);
            startActivity(intent);
        }

    }

    public class CollisionTask extends TimerTask {

        public void run(){

            player = BitmapFactory.decodeResource(getResources(), R.drawable.speedboyplayer_2);


        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mp.stop();
        mpss.stop();
        mps.stop();
        car_race.stop();
        Intent intent = new Intent(Speedboy_main.this, Speedboy.class);
        startActivity(intent);
        finish();
    }
}
