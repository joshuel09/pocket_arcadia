package josusama.corp.pocketarcadia;



import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Josusama on 3/9/2018.
 */

public class Powercolor_save {


        private String sName = "PowerColor";
        private Context sContext;

        public Powercolor_save(Context context) {
            sContext = context;
        }

        public void saveScore(int score){
            SharedPreferences spp = sContext.getSharedPreferences(sName, Context.MODE_PRIVATE);
            SharedPreferences.Editor e = spp.edit();
            e.putInt("highscores", score);
            e.commit();
        }

        public int getScore(){
            SharedPreferences spp = sContext.getSharedPreferences(sName, Context.MODE_PRIVATE);
            return spp.getInt("highscores", 0);
        }
}


