package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * Created by Kiko on 11/07/2016.
 */
public class Missile extends Entity{
    float startTime, maxLife, life;
    Trail trail;
    Entity entityHit;
    Vector explosionPosition;
    int explosionCounter;

    public Missile(float locX, float locY, float tarX, float tarY, float str){
        super(locX, locY);
        ProjectSolaris.missile = true;
        this.explosionSize = 30;
        this.explosionDuration = 150;
        // Load sprites and textures
        maxLife = 5000; // Missile lasts 5 seconds
        life = maxLife;
        this.radius = 10;
        startTime = TimeUtils.nanosToMillis(TimeUtils.nanoTime());
        // Set initial velocity of missile
        velocity = new Vector(tarX, tarY);
        velocity.sub(position);
        velocity.normalize();
        velocity.mult(str);
        trail = new Trail(locX, locY, radius, 20);
        deathParticles = new ParticleSystem(position.x, position.y, explosionSize, explosionDuration, false, explosionSprite);
        explosionCounter = 0;

    }

    @ Override
    public void update(){
        velocity.add(acceleration.scale());
        position.add(velocity.scale());
        acceleration.mult(0);
        life = maxLife - (TimeUtils.nanosToMillis(TimeUtils.nanoTime()) - startTime);

        // Destroy missiles outside entity border
        Vector distance = new Vector(position.x - ProjectSolaris.width / 2,
                position.y - ProjectSolaris.height / 2); // Distance from current position to center
        updateTrail();
    }

    @Override
    public void draw(){
        if (visible) {
            //float angle = velocity.getHeading();
            ProjectSolaris.renderer.begin(ShapeRenderer.ShapeType.Filled);
            ProjectSolaris.renderer.setColor(Color.WHITE);
            ProjectSolaris.renderer.circle(position.x, position.y, radius);
            ProjectSolaris.renderer.end();
        }
}

    @Override
    public void run(){
        ProjectSolaris.camera.position.x += (position.x - ProjectSolaris.camera.position.x) * (maxLife / Math.max(life, 500)) * 0.1;
        ProjectSolaris.camera.position.y += (position.y - ProjectSolaris.camera.position.y) * (maxLife / Math.max(life, 500)) * 0.1;
        if (visible) {
            if (!ProjectSolaris.isPaused) {
                update();
                ProjectSolaris.entities.gravity(this);
                if (entityHit != null) {
                    // explode!
                    explode();
                    entityHit.explode();
                    ProjectSolaris.screenFlash = true;
                }
                float distanceToCenter = Vector.distance(this.position, new Vector(ProjectSolaris.width / 2, ProjectSolaris.height / 2));
                if (life <= 0 || distanceToCenter > ProjectSolaris.entityBorder) {
                    explode();
                }
                entityHit = ProjectSolaris.entities.collision(this);
            }
            draw();
        } else {
            //if (!deathParticles.isAlive()){
            if (ProjectSolaris.resetCamera && ((deathParticles != null && !deathParticles.isAlive()) || deathParticles == null)) {
                //ProjectSolaris.resetCamera = true;
                alive = false;
            }
            deathParticles.update(position.x, position.y, velocity);
            // Explosion sprite / animation
            if (explosionPosition != null && explosionCounter < 5){
                ProjectSolaris.renderer.begin(ShapeRenderer.ShapeType.Filled);
                ProjectSolaris.renderer.setColor(Color.WHITE);
                ProjectSolaris.renderer.circle(explosionPosition.x, explosionPosition.y, radius * 15);
                ProjectSolaris.renderer.end();
                explosionCounter++;
            }
        }
    }

    public void updateTrail(){
        ProjectSolaris.renderer.begin(ShapeRenderer.ShapeType.Filled);
        ProjectSolaris.renderer.setColor(Color.WHITE);
        trail.update(position.x, position.y);
        trail.compute();
        trail.draw();
        ProjectSolaris.renderer.end();
    }


    public void explode(){
        deathParticles.setPosition(position);
        ProjectSolaris.missile = false;
        visible = false;
        ProjectSolaris.camera.zoom += 0.5;
//        ProjectSolaris.shaderPosition = Vector.sub(this.position,
//                new Vector(ProjectSolaris.camera.position.x - ProjectSolaris.width / 4,
//                        ProjectSolaris.camera.position.y - ProjectSolaris.height / 4));
        ProjectSolaris.shaderPosition = this.position;
        ProjectSolaris.shaderTime = 0;
        velocity.add(acceleration.scale());
        Vector normalizedVelocity = velocity;
        normalizedVelocity.normalize();
        ProjectSolaris.camera.position.x -= normalizedVelocity.x * 100;
        ProjectSolaris.camera.position.y -= normalizedVelocity.y * 100;
        position.add(velocity.scale());
        ProjectSolaris.entities.missile = null;
        ProjectSolaris.resetCamera = true;
        explosionPosition = new Vector(position.x, position.y);

    }
}
