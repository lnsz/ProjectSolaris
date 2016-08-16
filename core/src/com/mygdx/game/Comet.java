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
    Vector initialposition;
    public Comet(double originAngle, double targetAngle, float speed, float radius){
        super(0, 0, radius);
        initialposition = new Vector();
        position.x = 0;
        position.y = MissileGame.entityBorder;
        position.rotate((float)originAngle);
        position.x += MissileGame.width / 2;
        position.y += MissileGame.height / 2;
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
    }

    @Override
    public void draw(){
//        MissileGame.renderer.begin(ShapeRenderer.ShapeType.Filled);
//        MissileGame.renderer.setColor(Color.GRAY);
//        MissileGame.renderer.circle(position.x, position.y, radius);
//        MissileGame.renderer.end();
        MissileGame.batch.begin();
        sprite.setSize(radius * 2, radius * 2);
        sprite.setPosition(position.x - radius, position.y - radius);
        sprite.draw(MissileGame.batch);
        MissileGame.batch.end();
    }

    public void update(){
        // Goes back to the start instead of being destroyed when offscreen
        velocity.add(acceleration);
        position.add(velocity);
        acceleration.mult(0);

        Vector distance = new Vector(position.x - MissileGame.width / 2,
                position.y - MissileGame.height / 2); // Distance from current position to center
        if(distance.mag() > MissileGame.entityBorder){
            position.x = initialposition.x;
            position.y = initialposition.y;
        }
    }
}
