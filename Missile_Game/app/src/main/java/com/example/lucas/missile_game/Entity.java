package com.example.lucas.missile_game;

import android.graphics.Canvas;

/**
 * Created by lucas on 7/11/2016.
 */
public class Entity {
    Vector location, velocity, acceleration;
    boolean alive;

    public Entity (float locX, float locY){
        this.location = new Vector(locX, locY);
        this.velocity = new Vector();
        this.acceleration = new Vector();
        this.alive = true;
    }

    public void update(){
        velocity.add(acceleration);
        location.add(velocity);
        acceleration.mult(0);
    }

    public void run(Canvas canvas){
        update();
    }
}
