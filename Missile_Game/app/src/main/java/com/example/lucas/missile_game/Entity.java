package com.example.lucas.missile_game;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by lucas on 7/11/2016.
 */
public class Entity {
    Vector location, velocity, acceleration;
    Paint paint;
    float radius;
    boolean alive;

    public Entity (float locX, float locY){
        this.location = new Vector(locX, locY);
        this.velocity = new Vector();
        this.acceleration = new Vector();
        this.radius = 20;
        this.alive = true;
        this.paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setARGB(255,255,255,255);
    }

    public void update(){
        velocity.add(acceleration);
        location.add(velocity);
        acceleration.mult(0);

        if(location.x < 0 || location.x > GamePanel.width || location.y > GamePanel.height || location.y < 0){
            alive = false;
        }
    }

    public void display(Canvas canvas){
        canvas.drawCircle(location.x, location.y, radius, paint);
    }


    public void run(Canvas canvas){
        display(canvas);
        update();
    }
}
