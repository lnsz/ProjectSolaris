package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by lucas on 8/6/2016.
 */
public class Comet extends Obstacle{
    // Move in straight lines, don't have gravity
    Vector initialposition;
    Trail trail;
    public Comet(double originAngle, double targetAngle, float speed, float radius){
        super(0, 0, radius);
        initialposition = new Vector();
        position.x = 0;
        position.y = ProjectSolaris.entityBorder;
        position.rotate((float)originAngle);
        position.x += ProjectSolaris.width / 2;
        position.y += ProjectSolaris.height / 2;
        initialposition.x = position.x;
        initialposition.y = position.y;
        System.out.println(position);
        velocity.x = 0;
        velocity.y = 1;
        velocity.rotate((float)targetAngle);
        velocity.mult(speed);
        texture = new Texture(Gdx.files.internal("asteroid.png"));
        sprite = new Sprite(texture);
        gravity = true;
        trail = new Trail(position.x, position.y, radius, 30);
    }

    @Override
    public void draw(){
//        ProjectSolaris.renderer.begin(ShapeRenderer.ShapeType.Filled);
//        ProjectSolaris.renderer.setColor(Color.GRAY);
//        ProjectSolaris.renderer.circle(position.x, position.y, radius);
//        ProjectSolaris.renderer.end();
        ProjectSolaris.batch.begin();
        sprite.setSize(radius * 2, radius * 2);
        sprite.setPosition(position.x - radius, position.y - radius);
        sprite.draw(ProjectSolaris.batch);
        ProjectSolaris.batch.end();
    }

    public void update(){
        // Goes back to the start instead of being destroyed when offscreen
        velocity.add(acceleration.scale());
        position.add(velocity.scale());
        acceleration.mult(0);

        Vector distance = new Vector(position.x - ProjectSolaris.width / 2,
                position.y - ProjectSolaris.height / 2); // Distance from current position to center
        if(distance.mag() > ProjectSolaris.entityBorder){
            position.x = initialposition.x;
            position.y = initialposition.y;
            trail = new Trail(position.x, position.y, radius, 30);
        }
        updateTrail();
    }

    public void updateTrail(){
        trail.update(position.x, position.y);
        trail.compute();
        trail.draw();
    }
}
