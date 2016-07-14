package com.example.lucas.missile_game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.Random;

/**
 * Created by Kiko on 11/07/2016.
 */
public class Particle extends Entity{
    Random generator;
    float life, decay, randomness;
    public Particle(float locx, float locy, float rad, float duration, Random generator_){
        super(locx, locy);

        generator = generator_;
        radius = rad;
        life = 255;
        decay = (255 - 30) / duration;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setARGB(255,255,255,255);
        mass =0;
        randomness = 1;

    }

    public Particle(float locx, float locy, float velx, float vely, float rad, float duration, Random generator_){
        super(locx, locy);
        velocity.x = velx;
        velocity.y = vely;

        generator = generator_;
        radius = rad;
        life = 255;
        decay = (255 - 30) / duration;
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setARGB(255,255,255,255);
        mass =0;
        randomness = 1;

    }

    @Override
    public void display(Canvas canvas){
       // paint.setAlpha((int)life);
        Path path = new Path();
        path.moveTo(location.x, location.y - 5);
        path.lineTo(location.x, location.y + 5);
        path.close();
        canvas.drawPath(path, paint);
       // canvas.drawCircle(this.location.x, this.location.y, this.radius, this.paint);
    }

    @Override
    public void run(Canvas canvas){
        life -= decay;
        if(life < 30){
            alive=false;
            return;
        }
        acceleration.x += randomness*(float)generator.nextGaussian();
        acceleration.y += randomness*(float)generator.nextGaussian();
        update();
        display(canvas);
    }
}
