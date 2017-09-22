package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by lucas on 7/11/2016.
 */
public class Entity {
    Vector position, velocity, acceleration;
    float radius, mass;
    boolean alive;
    boolean visible;

    public Entity (float locX, float locY){
        this.position = new Vector(locX, locY);
        this.velocity = new Vector();
        this.acceleration = new Vector();
        this.radius = 15;
        this.mass = 0;
        this.alive = true;
        this.visible = true;
    }

    public void update(){
        velocity.add(acceleration.scale());
        position.add(velocity.scale());
        acceleration.mult(0);

    }

    public void draw(){
        ProjectSolaris.renderer.begin(ShapeRenderer.ShapeType.Filled);
        ProjectSolaris.renderer.setColor(Color.WHITE);
        ProjectSolaris.renderer.circle(position.x, position.y, radius);
        ProjectSolaris.renderer.end();
    }


    public void run(){
        if (!ProjectSolaris.isPaused) {
            update();
        }
        draw();
    }

    public static boolean collision(Entity ent1, Entity ent2){
        if(!ent1.visible || !ent2.visible){
            return false;
        }
        Vector dispRel = Vector.add(ent2.velocity.scale(), ent2.acceleration.scale());
        dispRel.sub(Vector.add(ent1.velocity.scale(), ent1.acceleration.scale()));

        Vector shortestLength = Vector.sub(ent1.position, ent2.position);
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
