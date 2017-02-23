package com.brainiacs.seandroidapp;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by benmoreland1 on 2/14/17.
 */

public class Background {

    private Bitmap image;
    private int x, y;

    public Background(Bitmap res)
    {
        image = res;
    }

    public void update()
    {

    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(image, x+GamePanel.WIDTH, y, null);
    }

}