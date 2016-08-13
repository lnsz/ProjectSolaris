package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by lucas on 8/6/2016.
 */
public class Comet extends Obstacle{
    // Move in straight lines, don't have gravity
    Vector initialLocation;
    public Comet(double originAngle, double targetAngle, float speed, float radius){
        super(0, 0, radius);
        initialLocation = new Vector();
        location.x = 0;
        location.y = MissileGame.entityBorder;
        location.rotate((float)originAngle);
        location.x += MissileGame.width / 2;
        location.y += MissileGame.height / 2;
        initialLocation.x = location.x;
        initialLocation.y = location.y;
        System.out.println(location);
        velocity.x = 0;
        velocity.y = 1;
        velocity.rotate((float)targetAngle);
        velocity.mult(speed);
        texture = new Texture(Gdx.files.internal("asteroid.png"));
        sprite = new Sprite(texture);
        gravity = true;
    }

    @Override
    public void draw(){
//        MissileGame.renderer.begin(ShapeRenderer.ShapeType.Filled);
//        MissileGame.renderer.setColor(Color.GRAY);
//        MissileGame.renderer.circle(location.x, location.y, radius);
//        MissileGame.renderer.end();
        MissileGame.batch.begin();
        sprite.setSize(radius * 2, radius * 2);
        sprite.setPosition(location.x - radius, location.y - radius);
        sprite.draw(MissileGame.batch);
        MissileGame.batch.end();
    }

    public void update(){
        // Goes back to the start instead of being destroyed when offscreen
        velocity.add(acceleration);
        location.add(velocity);
        acceleration.mult(0);

        Vector distance = new Vector(location.x - MissileGame.width / 2,
                location.y - MissileGame.height / 2); // Distance from current location to center
        if(distance.mag() > MissileGame.entityBorder){
            location.x = initialLocation.x;
            location.y = initialLocation.y;
        }
    }
}
