package com.example.lucas.missile_game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.speech.tts.TextToSpeech;

/**
 * Created by Kiko on 11/07/2016.
 */
public class Missile extends Entity{
    EntitySystem entities;
    Bitmap particleSprite;
    public Missile(float locX, float locY, float tarX, float tarY, float str, EntitySystem entities_, Bitmap particleSprite){
        super(locX, locY);
        //get entities array
        entities=entities_;
        this.particleSprite = particleSprite;
        // Set initial velocity of missile
        velocity = new Vector(tarX, tarY);
        velocity.sub(location);
        velocity.normalize();
        velocity.mult(str);

    }

    @Override
    public void display(Canvas canvas){
        float angle = velocity.getHeading();

        Path path = new Path();
        Vector diff = new Vector(0, 2 * radius);
        diff.rotate(angle);
        path.moveTo(location.x + diff.x, location.y + diff.y);

        diff = new Vector(-radius,-radius*2);
        diff.rotate(angle);
        path.lineTo(location.x +diff.x, location.y + diff.y);

        diff = new Vector(radius,-radius*2);
        diff.rotate(angle);
        path.lineTo(location.x + diff.x, location.y + diff.y);

        path.close();
        canvas.drawPath(path, paint);

        Particle p;
        for (int i = 0; i < 8; i++) {
            p = new Particle(location.x, location.y, 3, 20, entities.generator, particleSprite);
            p.randomness = 2;
            p.velocity = Vector.mult(this.velocity, -1);
            p.velocity.normalize();
            p.velocity.mult(20);
            entities.addEntity(p, false);
        }
    }

    @Override
    public void run(Canvas canvas){
        entities.gravity(this);
        if(entities.collision(this)){
            //explode!
            explode();
        }
        display(canvas);
        update();
    }

    public void explode(){
        alive=false;
        for(int i=0;i<4;i++) {
            Particle p = new Particle(location.x, location.y, 10, 60, entities.generator, particleSprite);
            if(i%2 == 0) {
                p.paint.setARGB(255,255, 0, 0);
            }else{
                p.paint.setARGB(255, 255, 69, 0);
            }
            p.velocity = Vector.random();
            p.velocity.mult(0.5f);
            p.radius = 40;
            entities.addEntity(p, false);
        }
    }



}
