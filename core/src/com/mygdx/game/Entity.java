package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by lucas on 7/11/2016.
 */
public class Entity {
    ParticleSystem deathParticles;
    Sprite explosionSprite;
    Texture explosionTexture;
    Vector position, velocity, acceleration;
    int explosionDuration, explosionSize;
    float radius, mass;
    boolean alive;
    boolean visible;
    Sprite sprite;
    Texture texture;

    public Entity (float locX, float locY){
        this.position = new Vector(locX, locY);
        this.velocity = new Vector();
        this.acceleration = new Vector();
        this.radius = 15;
        this.mass = 0;
        this.alive = true;
        this.visible = true;
//        this.explosionSize = 50;
//        this.explosionDuration = 75;
        this.explosionSize = 1;
        this.explosionDuration = 1;
        this.explosionTexture = new Texture(Gdx.files.internal("explosion.png"));
        this.explosionSprite = new Sprite(explosionTexture);
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
        if(visible) {
            if (!ProjectSolaris.isPaused) {
                update();
            }
            draw();
        }
        else{
            deathParticles.update(position.x, position.y, velocity);
            if (!deathParticles.isAlive()){
                alive = false;
            }
        }
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

    public void explode(){
        visible = false;
        deathParticles = new ParticleSystem(position.x, position.y, explosionSize, explosionDuration, false, explosionSprite);
    }
}
