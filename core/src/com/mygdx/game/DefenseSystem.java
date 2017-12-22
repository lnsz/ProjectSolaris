package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by lucas on 12/22/2017.
 */

public class DefenseSystem extends Obstacle {
    float targettingRadius;
    float distanceToMissile;
    boolean missileInRange;
    double targettingTime;
    double timeToKill;
    Entity missile;

    public DefenseSystem(float radius, double angle, Planet planet, float altitude, float targettingRadius, float timeToKill) {
        super(0, 0, radius);
        position.x = 0;
        position.y = -altitude;
        position.rotate((float) angle);
        position.add(planet.position);
        velocity.rotate((float) angle);
        this.timeToKill = timeToKill;
        this.targettingRadius = targettingRadius;
        targettingTime = ProjectSolaris.getTime();
    }

    public void update(){

        missile = ProjectSolaris.entities.missile;
        if (missile != null){
            System.out.println(ProjectSolaris.getTime() - targettingTime);
            distanceToMissile = Vector.distance(position, missile.position);
            if (distanceToMissile > targettingRadius){
                targettingTime = ProjectSolaris.getTime();
            } else {
                //System.out.println(System.currentTimeMillis() - targettingTime);
                if (ProjectSolaris.getTime() - targettingTime > timeToKill){
                    missile.explode();
                }
            }
        } else {
            distanceToMissile = Integer.MAX_VALUE;
        }
    }

    @Override
    public void draw(){
        ProjectSolaris.renderer.begin(ShapeRenderer.ShapeType.Filled);
        ProjectSolaris.renderer.setColor(Color.WHITE);
        ProjectSolaris.renderer.circle(position.x, position.y, radius);
        ProjectSolaris.renderer.end();
        ProjectSolaris.renderer.begin(ShapeRenderer.ShapeType.Line);
        ProjectSolaris.renderer.setColor(Color.WHITE);
        ProjectSolaris.renderer.circle(position.x, position.y, targettingRadius);
        ProjectSolaris.renderer.end();

        if (distanceToMissile < targettingRadius){
            Vector dir;
            Vector perp;
            Vector a;
            Vector b;
            dir = Vector.sub(missile.position, position);
            dir.normalize();
            perp = new Vector(-dir.y, dir.x);
            perp.mult((float)(Math.pow((timeToKill / (ProjectSolaris.getTime() - targettingTime + timeToKill / 10)), 2)));
            a = Vector.sub(missile.position, perp);
            b = Vector.add(missile.position, perp);

            Gdx.gl.glEnable(GL20.GL_BLEND);
            ProjectSolaris.renderer.begin(ShapeRenderer.ShapeType.Line);
            ProjectSolaris.renderer.setColor(Color.RED);
            ProjectSolaris.renderer.triangle(position.x, position.y, a.x, a.y, b.x, b.y);
            ProjectSolaris.renderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

//            ProjectSolaris.renderer.begin(ShapeRenderer.ShapeType.Filled);
//            ProjectSolaris.renderer.setColor(Color.RED);
//            ProjectSolaris.renderer.rectLine(position.x, position.y, missile.position.x, missile.position.y, 1);
//            ProjectSolaris.renderer.end();
        }

    }
}
