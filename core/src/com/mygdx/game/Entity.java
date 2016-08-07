package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by lucas on 7/11/2016.
 */
public class Entity {
    Vector location, velocity, acceleration;
    float radius, mass;
    boolean alive;
    boolean visible;

    public Entity (float locX, float locY){
        this.location = new Vector(locX, locY);
        this.velocity = new Vector();
        this.acceleration = new Vector();
        this.radius = 10;
        this.mass = 0;
        this.alive = true;
        this.visible = true;
    }

    public void update(){
        velocity.add(acceleration);
        location.add(velocity);
        acceleration.mult(0);

        Vector distance = new Vector(location.x - MissileGame.width / 2,
                location.y - MissileGame.height / 2); // Distance from current location to center
        if(distance.mag() > MissileGame.entityBorder){
            alive = false;
        }
    }

    public void display(SpriteBatch batch, ShapeRenderer renderer){
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Color.WHITE);
        renderer.circle(location.x, location.y, radius);
        renderer.end();
    }


    public void run(SpriteBatch batch, ShapeRenderer renderer){
        display(batch, renderer);
        update();
    }

    public static boolean collision(Entity ent1, Entity ent2){
        Vector dispRel = Vector.add(ent2.velocity, ent2.acceleration);
        dispRel.sub(Vector.add(ent1.velocity, ent1.acceleration));

        Vector shortestLength = Vector.sub(ent1.location, ent2.location);
        if(shortestLength.dot(dispRel) <= 0){
            return false;
        }
        float sL = shortestLength.mag();
        float totalRadius = ent1.radius + ent2.radius;
        if(dispRel.mag() <= sL - totalRadius){
            return false;
        }
        float d = dispRel.dot(shortestLength) / dispRel.mag();
        float f = (sL * sL) - (d * d);
        if(f >= totalRadius *totalRadius){
            return false;
        }
        //ent1.alive=false;
        //ent2.alive=false;

        return true;
    }
}
