package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by lucas on 2016-08-27.
 */
public class Player extends Entity{

    public int ammo;
    public int maxAmmo;
    private int counter;
    private boolean flashing;
    public Player(float locX, float locY){
        super(ProjectSolaris.remap(locX, 0, ProjectSolaris.width,
                ProjectSolaris.origin.x,
                ProjectSolaris.origin.x + ProjectSolaris.width),
                ProjectSolaris.remap(locY, 0, ProjectSolaris.height,
                        ProjectSolaris.origin.y,
                        ProjectSolaris.origin.y + ProjectSolaris.height));
        maxAmmo = 3;
        ammo = maxAmmo;
        radius = 50;
        counter=0;
        flashing = false;
    }

    @Override
    public void draw(){
        ProjectSolaris.renderer.begin(ShapeRenderer.ShapeType.Line);
        ProjectSolaris.renderer.setColor(255, 255, 255, 1);
        ProjectSolaris.renderer.circle(position.x, position.y, radius); // Temp player character
        ProjectSolaris.renderer.end();
        if(flashing){
            if(counter % 7 != 0){
                showAmmo();
            }
            counter++;
            if (counter >=30){
                flashing = false;
            }
        }else {
            showAmmo();
        }
    }

    public void showAmmo(){
        ProjectSolaris.batch.begin();
        ProjectSolaris.arial.getData().setScale(ProjectSolaris.height / 3000 * ProjectSolaris.camera.zoom);
        ProjectSolaris.arial.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        String text = ammo + "";
        ProjectSolaris.glyphLayout.setText(ProjectSolaris.arial, text);
        float textX = ProjectSolaris.remap(0, 0, ProjectSolaris.width, ProjectSolaris.origin.x, ProjectSolaris.origin.x + ProjectSolaris.width);
        float textY = ProjectSolaris.remap(0, 0, ProjectSolaris.height, ProjectSolaris.origin.y, ProjectSolaris.origin.y + ProjectSolaris.height);
        ProjectSolaris.arial.draw(ProjectSolaris.batch, ProjectSolaris.glyphLayout, textX, textY);
        ProjectSolaris.batch.end();
    }

    public boolean hasAmmo(){
        return (ammo > 0);
    }

    public void shootMissile(float targetX, float targetY, float strength){
        if(this.hasAmmo()){
            ProjectSolaris.entities.addEntity(new Missile(this.position.x, this.position.y, targetX, targetY, strength));
            ammo--;
        }
    }

    public void resetAmmo(){
        ammo = maxAmmo;
    }
}
