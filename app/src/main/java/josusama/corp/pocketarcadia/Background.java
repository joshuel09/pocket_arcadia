package josusama.corp.pocketarcadia;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Josusama on 3/9/2018.
 */

public class Background {

    private Bitmap image;
    private int x, y, dx;

    public Background(Bitmap res)
    {
        image = res;
        dx = 100;
    }

    public void update()
    {
        y+=dx;
        if(y>1280){
            y=0;
        }
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image, x,y-1280,null);
        if(y==0)
        {
            canvas.drawBitmap(image, x, y+856, null);
        }
    }
}
