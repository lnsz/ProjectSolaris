package com.example.lucas.missile_game;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Kiko on 11/07/2016.
 */
public class Missile extends Entity{
    Paint paint;
    float radius;

    public Missile(float locX, float locY, float tarX, float tarY, float str){
        super(locX, locY);

        // Set initial acceleraion of missile
        velocity = new Vector(tarX, tarY);
        velocity.sub(location);
        velocity.normalize();
        velocity.mult(str);



        // Temp code, will change to a sprite later
        radius = 20;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setARGB(255,255,255,255);
    }

    public void display(Canvas canvas){
        canvas.drawCircle(this.location.x, this.location.y, this.radius, this.paint);
    }

    @Override
    public void run(Canvas canvas){
        update();
        display(canvas);
    }
}
