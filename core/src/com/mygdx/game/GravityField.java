package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by lucas on 12/24/2017.
 */

public class GravityField extends Obstacle {

    public GravityField(float radius, float mass, double angle, Planet planet, float altitude) {
        super(0, 0, radius);
        position.x = 0;
        position.y = -altitude;
        position.rotate((float)angle);
        position.add(planet.position);
        this.mass = mass;
        gravity = true;
    }

    @Override
    public void draw(){
        ProjectSolaris.renderer.begin(ShapeRenderer.ShapeType.Filled);
        if (mass < 0){
            ProjectSolaris.renderer.setColor(Color.RED);
        } else {
            ProjectSolaris.renderer.setColor(Color.BLUE);
        }

        ProjectSolaris.renderer.circle(position.x, position.y, radius);
        ProjectSolaris.renderer.end();
    }
}
