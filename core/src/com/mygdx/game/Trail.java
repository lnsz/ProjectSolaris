package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by lucas on 5/1/2017.
 */

public class Trail extends Entity{
    // float trailTimer, lastTrail;
    int trailCount, trailSize;
    float trailWeight;
    Queue<Vector> trailPoints;
    ArrayList<Vector> trail;

    public Trail(float locX, float locY, float trailWeight, int trailSize){
        super(locX, locY);
        this.trailWeight = trailWeight;
        this.trailSize = trailSize;
        trailPoints = new LinkedList<Vector>();
        trail = new ArrayList<Vector>();
        // Add the first point to the trail
        trailPoints.add(new Vector(position.x, position.y));
        trailCount = 1;
        // Trail is currently updated every game loop, can be changed to be time based.
        // trailTimer = 1; // Update trail every iteration. Increase this number for longer trails
        // lastTrail = TimeUtils.nanosToMillis(TimeUtils.nanoTime());
    }

    public void compute(){
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

    public void update(float locX, float locY){
        trailPoints.add(new Vector(locX, locY));
        trailCount++;
        // Remove a point if the trail is too long
        if(trailCount > trailSize && trailPoints.peek() != null){
            trailPoints.remove();
        }
        //lastTrail = TimeUtils.nanosToMillis(TimeUtils.nanoTime());
    }

    @Override
    public void draw(){
        ProjectSolaris.renderer.begin(ShapeRenderer.ShapeType.Filled);
        if (trail.size() < 3 && trail.size() > 0){
            ProjectSolaris.renderer.circle(trail.get(0).x, trail.get(0).y, 5);
        }else {
            for (int i = 0; i < trail.size() - 2; i ++) {
                ProjectSolaris.renderer.triangle(trail.get(i).x, trail.get(i).y, trail.get(i + 1).x, trail.get(i + 1).y, trail.get(i + 2).x, trail.get(i + 2).y);
            }
        }
        ProjectSolaris.renderer.end();
        trail.clear();
    }

}
