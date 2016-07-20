package com.example.lucas.missile_game;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    Bitmap sprite;
    public Particle(float locX, float locY, float rad, float duration, Random generator_, Bitmap sprite){
        super(locX, locY);
        this.sprite = sprite;
        generator = generator_;
        radius = rad;
        life = 255;
        decay = (255 - 30) / duration;
//        paint = new Paint();
//        paint.setStyle(Paint.Style.FILL_AND_STROKE);
//        paint.setStrokeWidth(2);
//        paint.setARGB(255,255,255,255);
        mass =0;
        randomness = 1;

    }

    public Particle(float locX, float locY, float velX, float velY, float rad, float duration, Random generator_){
        super(locX, locY);
        velocity.x = velX;
        velocity.y = velY;

        generator = generator_;
        radius = rad;
        life = 255;
        decay = (255 - 30) / duration;
//        paint = new Paint();
//        paint.setStyle(Paint.Style.FILL_AND_STROKE);
//        paint.setStrokeWidth(2);
//        paint.setARGB(255,255,255,255);
        mass =0;
        randomness = 1;

    }

    @Override
    public void display(Canvas canvas){
//        paint.setAlpha((int)life);
//        canvas.drawCircle(this.location.x, this.location.y, this.radius, this.paint);
        canvas.drawBitmap(sprite, this.location.x, this.location.y, null);
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
