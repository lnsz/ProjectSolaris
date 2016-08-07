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
        location.y = (MissileGame.maxHeight -  MissileGame.maxOriginY) / 2;
        location.rotate((float)originAngle);
        location.x += MissileGame.width;
        initialLocation.x = location.x;
        initialLocation.y = location.y;
        velocity.x = 0;
        velocity.y = 1;
        velocity.rotate((float)targetAngle);
        velocity.mult(speed);

        texture = new Texture(Gdx.files.internal("asteroid.png"));
        sprite = new Sprite(texture);
        gravity = true;
    }

    @Override
    public void display(SpriteBatch batch, ShapeRenderer renderer){
//        renderer.begin(ShapeRenderer.ShapeType.Filled);
//        renderer.setColor(Color.GRAY);
//        renderer.circle(location.x, location.y, radius);
//        renderer.end();
        batch.begin();
        sprite.setSize(radius * 2, radius * 2);
        sprite.setPosition(location.x - radius, location.y - radius);
        sprite.draw(batch);
        batch.end();
    }

    public void update(){
        // Goes back to the start instead of being destroyed when offscreen
        velocity.add(acceleration);
        location.add(velocity);
        acceleration.mult(0);

        float distanceX = location.x - (MissileGame.maxWidth -  MissileGame.maxOriginX) / 2;
        float distanceY = location.y - (MissileGame.maxHeight -  MissileGame.maxOriginY) / 2;
        if(distanceX > (MissileGame.maxWidth -  MissileGame.maxOriginX) / 2 || distanceY >
                (MissileGame.maxHeight -  MissileGame.maxOriginY) / 2){
            location.x = initialLocation.x;
            location.y = initialLocation.y;
        }
    }
}
