package com.mygdx.game;

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
        this.radius = 20;
        this.mass = 1;
        this.alive = true;
        this.visible = true;
    }

    public void update(){
        velocity.add(acceleration);
        location.add(velocity);
        acceleration.mult(0);

        if(location.x < MissileGame.cameraOriginX||
                location.x > MissileGame.cameraWidth ||
                location.y > MissileGame.cameraHeight ||
                location.y < MissileGame.cameraOriginY){
            alive = false;
        }
    }

    public void display(SpriteBatch batch, ShapeRenderer renderer){
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(255, 255, 255, 1);
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
