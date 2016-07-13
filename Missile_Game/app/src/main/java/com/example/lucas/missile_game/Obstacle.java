package com.example.lucas.missile_game;

/**
 * Created by lucas on 7/13/2016.
 */
public class Obstacle extends Entity{
    enum Type{PLANET, MOON, ASTEROID, COMET
    }
    boolean gravity;
    Type type;
    public Obstacle(Type type, float locX, float locY, float radius){
        super(locX, locY);
        this.type = type;
        this.radius = radius;
        if (type == Type.PLANET || type == Type.MOON){
            gravity = true;
            mass = (float)Math.pow(radius, 3);
        }
        else{
            gravity = false;
        }
    }

    public void gravity (Entity other){
        other.acceleration = new Vector(this.location.x - other.location.x, this.location.y - other.location.y);
        other.acceleration.normalize();
        other.acceleration.mult((float)(1E-6 * this.mass));
    }



}
