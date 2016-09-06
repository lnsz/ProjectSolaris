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
        super(MissileGame.remap(locX, 0, MissileGame.width,
                MissileGame.defaultOriginX,
                MissileGame.defaultOriginX + MissileGame.defaultWidth),
                MissileGame.remap(locY, 0, MissileGame.height,
                        MissileGame.defaultOriginY,
                        MissileGame.defaultOriginY + MissileGame.defaultHeight));
        maxAmmo = 5;
        ammo = maxAmmo;
        radius = 50;
        counter=0;
        flashing = false;
    }

    @Override
    public void draw(){
        MissileGame.renderer.begin(ShapeRenderer.ShapeType.Line);
        MissileGame.renderer.setColor(255, 255, 255, 1);
        MissileGame.renderer.circle(position.x, position.y, radius); // Temp player character
        MissileGame.renderer.end();
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
        MissileGame.batch.begin();
        MissileGame.arial.getData().setScale(MissileGame.height / 3000 * MissileGame.camera.zoom);
        MissileGame.arial.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        String text = ammo + "";
        MissileGame.glyphLayout.setText(MissileGame.arial, text);
        float textX = MissileGame.remap(0, 0, MissileGame.width, MissileGame.cameraOriginX, MissileGame.cameraOriginX + MissileGame.cameraWidth);
        float textY = MissileGame.remap(0, 0, MissileGame.height, MissileGame.cameraOriginY, MissileGame.cameraOriginY + MissileGame.cameraHeight);
        MissileGame.arial.draw(MissileGame.batch, MissileGame.glyphLayout, textX, textY);
        MissileGame.batch.end();
    }

    public void shootMissile(float targetX, float targetY, float strength){
        if(ammo <= 0){
            //show no ammo animation
            flashing = true;
            counter=0;
            return;
        }
        MissileGame.entities.addEntity(new Missile(this.position.x, this.position.y, targetX, targetY, strength));
        ammo--;
    }

    public void resetAmmo(){
        ammo = maxAmmo;
    }
}
