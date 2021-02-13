package josusama.corp.pocketarcadia;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

/**
 * Created by Josusama on 3/9/2018.
 */

public class MusicPlayer extends Service {

    MediaPlayer player;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    public void onCreate() {
        player = MediaPlayer.create(MusicPlayer.this, R.raw.theme_audio);
        player.setLooping(true);
        player.setVolume(100,100);
    }

    public void onDestroy() {
        player.stop();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();

        return 1;
    }
}