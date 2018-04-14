package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by lucas on 2016-08-27.
 */
public class Player extends Entity{

    public int ammo;
    public int maxAmmo;
    private int counter;
    private boolean flashing;
    private double rotation;
    private Sprite chargingSprite;
    private Texture chargingTexture;
    float chargeRadius;
    private Vector missileOrigin;
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
        counter = 0;
        flashing = false;
        texture = new Texture(Gdx.files.internal("player.png"));
        sprite = new Sprite(texture);
        sprite.setOrigin(radius, radius);
        rotation = 0;
        // Initialize charging animation
        chargingTexture = new Texture(Gdx.files.internal("charge.png"));
        chargingSprite = new Sprite(chargingTexture);
        missileOrigin = position;
        chargeRadius = 0;
    }

    @Override
    public void draw(){
        rotation = Math.toDegrees(Vector.sub(position, ProjectSolaris.lastTouch).angle()) + 90;
        ProjectSolaris.batch.begin();
        sprite.setRotation((float)rotation);
        sprite.setSize(radius * 2, radius * 2);
        sprite.setPosition(position.x - radius, position.y - radius);
        sprite.draw(ProjectSolaris.batch);
        ProjectSolaris.batch.end();
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
            ProjectSolaris.entities.addEntity(new Missile(this.missileOrigin.x, this.missileOrigin.y, targetX, targetY, strength));
            ammo--;
        }
    }

    public void resetAmmo(){
        ammo = maxAmmo;
    }

    public void drawCharge(double timeRatio){
        System.out.println(timeRatio);
        Vector disp = new Vector(0, 1);
        disp = Vector.rotate(disp, (float)Math.toRadians(rotation));
        ProjectSolaris.batch.begin();
        chargeRadius = radius * 3 * (float)timeRatio;
        missileOrigin = new Vector(position.x + disp.x * radius, position.y + disp.y * radius);
        chargingSprite.setSize(chargeRadius, chargeRadius);
        chargingSprite.setPosition(missileOrigin.x - chargeRadius / 2, missileOrigin.y - chargeRadius / 2);
        chargingSprite.draw(ProjectSolaris.batch);
        ProjectSolaris.batch.end();
    }
}
