package com.example.lucas.missile_game;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by Kiko on 11/07/2016.
 */
public class Particle extends Entity{
    Paint paint;
    Random generator;
    float life, radius;
    public Particle(float locx, float locy, float rad){
        super(locx, locy);
        generator = new Random();
        radius = rad;
        life = 255;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setARGB(255,255,255,255);

    }

    public void display(Canvas canvas){
        paint.setAlpha((int)life);
        canvas.drawCircle(this.location.x, this.location.y, this.radius, this.paint);
    }

    public void run(Canvas canvas){
        life -= 0.05f;
        if(!alive || life < 30){
            alive=false;
            return;
        }
        acceleration.x = (float)generator.nextGaussian();
        acceleration.y = (float)generator.nextGaussian();
        update();
        display(canvas);
    }
}
