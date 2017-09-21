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
                ProjectSolaris.defaultOriginX,
                ProjectSolaris.defaultOriginX + ProjectSolaris.defaultWidth),
                ProjectSolaris.remap(locY, 0, ProjectSolaris.height,
                        ProjectSolaris.defaultOriginY,
                        ProjectSolaris.defaultOriginY + ProjectSolaris.defaultHeight));
        maxAmmo = 5;
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
        float textX = ProjectSolaris.remap(0, 0, ProjectSolaris.width, ProjectSolaris.cameraOriginX, ProjectSolaris.cameraOriginX + ProjectSolaris.cameraWidth);
        float textY = ProjectSolaris.remap(0, 0, ProjectSolaris.height, ProjectSolaris.cameraOriginY, ProjectSolaris.cameraOriginY + ProjectSolaris.cameraHeight);
        ProjectSolaris.arial.draw(ProjectSolaris.batch, ProjectSolaris.glyphLayout, textX, textY);
        ProjectSolaris.batch.end();
    }

    public void shootMissile(float targetX, float targetY, float strength){
        if(ammo <= 0){
            //show no ammo animation
            flashing = true;
            counter=0;
            return;
        }
        ProjectSolaris.entities.addEntity(new Missile(this.position.x, this.position.y, targetX, targetY, strength));
        ammo--;
    }

    public void resetAmmo(){
        ammo = maxAmmo;
    }
}
