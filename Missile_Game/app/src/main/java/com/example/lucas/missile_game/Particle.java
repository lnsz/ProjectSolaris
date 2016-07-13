package com.example.lucas.missile_game;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by Kiko on 11/07/2016.
 */
public class Particle extends Entity{
    Random generator;
    float life;
    public Particle(float locx, float locy, float rad, Random generator_){
        super(locx, locy);
        generator = generator_;
        radius = rad;
        life = 255;
        mass = 0;

    }

    public Particle(float locx, float locy, float velx, float vely, float rad, Random generator_){
        super(locx, locy);
        velocity.x = velx;
        velocity.y = vely;

        generator = generator_;
        radius = rad;
        life = 255;
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setARGB(255,255,255,255);

    }

    @Override
    public void display(Canvas canvas){
        paint.setAlpha((int)life);
        canvas.drawCircle(this.location.x, this.location.y, this.radius, this.paint);
    }

    @Override
    public void run(Canvas canvas){
        life -= 15f;
        if(life < 30){
            alive=false;
            return;
        }
        acceleration.x += 1f*(float)generator.nextGaussian();
        acceleration.y += 1f*(float)generator.nextGaussian();
        update();
        display(canvas);
    }
}
