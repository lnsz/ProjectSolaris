package com.example.lucas.missile_game;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

/**
 * Created by Kiko on 11/07/2016.
 */
public class Missile extends Entity{
    Paint paint;
    float radius;
    EntitySystem entities;

    public Missile(float locX, float locY, float tarX, float tarY, float str, EntitySystem entities_){
        super(locX, locY);
        //get entities array
        entities=entities_;

        // Set initial velocity of missile
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
        Particle p = new Particle(location.x, location.y, 10, entities.generator);
        p.velocity= Vector.mult(this.velocity, -1);
        entities.addEntity(p);
    }

    @Override
    public void run(Canvas canvas){
        update();
        display(canvas);
    }
}
