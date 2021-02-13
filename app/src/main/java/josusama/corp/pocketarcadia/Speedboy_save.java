package josusama.corp.pocketarcadia;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Josusama on 3/9/2018.
 */

public class Speedboy_save {

    private String sName = "SpeedBoy";
    private Context sContext;

    public Speedboy_save(Context context) {
        sContext = context;
    }

    public void saveScore(int score){
        SharedPreferences spp = sContext.getSharedPreferences(sName, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = spp.edit();
        e.putInt("highscore", score);
        e.commit();
    }

    public int getScore(){
        SharedPreferences spp = sContext.getSharedPreferences(sName, Context.MODE_PRIVATE);
        return spp.getInt("highscore", 0);
    }
}
