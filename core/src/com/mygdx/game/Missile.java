package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Kiko on 11/07/2016.
 */
public class Missile extends Entity{
    ParticleSystem flightParticles, deathParticles;
    Sprite particleSprite, explosionSprite;
    Texture particleTexture, explosionTexture;
    float startTime, life, trailTimer, lastTrail;
    int trailCount, trailSize, trailWeight;
    Queue<Vector> trailPoints;
    ArrayList<Vector> trail;
    public Missile(float locX, float locY, float tarX, float tarY, float str){
        super(locX, locY);
        MissileGame.missile = true;

        // Load sprites and textures
        particleTexture = new Texture(Gdx.files.internal("dot.png"));
        particleSprite = new Sprite(particleTexture);
        explosionTexture = new Texture(Gdx.files.internal("explosion.png"));
        explosionSprite = new Sprite(explosionTexture);
        life = 10000; // Missile lasts 10 seconds
        startTime = TimeUtils.nanosToMillis(TimeUtils.nanoTime());
        // Set initial velocity of missile
        velocity = new Vector(tarX, tarY);
        velocity.sub(position);
        velocity.normalize();
        velocity.mult(str);

        trailWeight = 20;
        trailSize = 15;
        trailTimer = 1; // Update trail every iteration. Increase this number for longer trails
        trailPoints = new LinkedList<Vector>();
        trail = new ArrayList<Vector>();
        // Add the first point to the trail
        trailPoints.add(new Vector(position.x, position.y));
        trailCount = 1;
        lastTrail = TimeUtils.nanosToMillis(TimeUtils.nanoTime());
        this.flightParticles = new ParticleSystem(position.x, position.y, velocity, 300, 20, true, particleSprite);
    }

    @Override
    public void draw(){
        if (visible) {
            //float angle = velocity.getHeading();
            MissileGame.renderer.begin(ShapeRenderer.ShapeType.Filled);
            MissileGame.renderer.setColor(Color.WHITE);
            MissileGame.renderer.circle(position.x, position.y, radius);
            MissileGame.renderer.end();
        }
}

    @Override
    public void run(){
        if(visible) {
            if (!MissileGame.isPaused) {
                update();
                MissileGame.entities.gravity(this);
                if (MissileGame.entities.collision(this)) {
                    // explode!
                    explode();
                }
                if (TimeUtils.nanosToMillis(TimeUtils.nanoTime()) - startTime > life){
                    explode();
                }
            }
            draw();
        }
        else{
            deathParticles.update(position.x, position.y, velocity);
            if (!deathParticles.isAlive()){
                alive = false;
            }
        }

        // After some time has passed, add another instance of the trail to the list of trail points
        if (TimeUtils.nanosToMillis(TimeUtils.nanoTime()) - lastTrail > trailTimer){
            if(visible) {
                trailPoints.add(new Vector(position.x, position.y));
                trailCount++;
            }
            // Remove a point if the trail is too long
            if(trailCount > trailSize && trailPoints.peek() != null){
                trailPoints.remove();
            }
            lastTrail = TimeUtils.nanosToMillis(TimeUtils.nanoTime());
        }
        //flightParticles.update(position.x, position.y, velocity);
        if(trailPoints.peek() != null) {
            computeTrail();
            drawTrail();
        }
    }

    void computeTrail(){
        ArrayList<Vector> temp = new ArrayList<Vector>();
        for(Vector pos: trailPoints){
            temp.add(pos);
        }
        trail.add(temp.get(0));
        Vector dir;
        Vector perp;
        Vector a;
        Vector b;
        for(int i = 0; i < temp.size() - 1; i++){
            dir = Vector.sub(temp.get(i + 1), temp.get(i));
            dir.normalize();
            perp = new Vector(-dir.y, dir.x);
            perp.mult(trailWeight * i / temp.size());
            a = Vector.sub(temp.get(i + 1), perp);
            trail.add(a);
            b = Vector.add(temp.get(i + 1), perp);
            trail.add(b);
        }
    }

    void drawTrail(){
        MissileGame.renderer.begin(ShapeRenderer.ShapeType.Filled);
        if (trail.size() < 3 && trail.size() > 0){
            MissileGame.renderer.circle(trail.get(0).x, trail.get(0).y, 5);
        }else {
            for (int i = 0; i < trail.size() - 2; i ++) {
                MissileGame.renderer.triangle(trail.get(i).x, trail.get(i).y, trail.get(i + 1).x, trail.get(i + 1).y, trail.get(i + 2).x, trail.get(i + 2).y);
            }
        }
        MissileGame.renderer.end();
        trail.clear();
    }

    public void explode(){
        MissileGame.missile = false;
        visible = false;
        deathParticles = new ParticleSystem(position.x, position.y, 500, 100, false, explosionSprite);
        flightParticles.recycle = false;
    }



}
