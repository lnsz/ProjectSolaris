package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by lucas on 2016-08-27.
 */
public class Player extends Entity{
    public Player(float locX, float locY){
        super(locX, locY);
        radius = 50;
    }

    @Override
    public void draw(){
        MissileGame.renderer.begin(ShapeRenderer.ShapeType.Line);
        MissileGame.renderer.setColor(255, 255, 255, 1);
        MissileGame.renderer.circle(position.x, position.y, radius); // Temp player character
        MissileGame.renderer.end();
    }
}
