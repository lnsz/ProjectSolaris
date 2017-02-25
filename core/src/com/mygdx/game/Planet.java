package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by lucas on 8/3/2016.
 */
public class Planet extends Obstacle{
    // Stationary, have their own gravity

    // Planet images are in planet/ with the format layerAB, where A is the layer number and
    // B is the index
    // Layer 00: Atmosphere
    // Layer 10: Planet background
    // Layer 2x: Land
    //           0 <= x <= 5: Earth like continents
    // Layer 3x: Clouds
    //           0:           No clouds
    //           1 <= x <= A: Clouds

    Sprite sprite0;
    Sprite sprite1;
    Sprite sprite2;
    Sprite sprite3;
    Texture texture0;
    Texture texture1;
    Texture texture2;
    Texture texture3;


    public Planet(float locX, float locY, float radius, float mass){
        super(MissileGame.remap(locX, 0, MissileGame.width,
                MissileGame.defaultOriginX,
                MissileGame.defaultOriginX + MissileGame.defaultWidth),
                MissileGame.remap(locY, 0, MissileGame.height,
                        MissileGame.defaultOriginY,
                        MissileGame.defaultOriginY + MissileGame.defaultHeight), radius);
        if(radius > Levels.LARGE_PLANET_RADIUS) {
            texture = new Texture(Gdx.files.internal("largePlanet.png"));
        }
        else if(radius < Levels.SMALL_MOON_RADIUS){
            texture = new Texture(Gdx.files.internal("smallPlanet.png"));
        }
        else{
            texture = new Texture(Gdx.files.internal("earth.png"));
        }
        sprite = new Sprite(texture);
        gravity = true;
        this.mass = mass;
    }

    public Planet(MissileGame.Preset pos, float radius, float mass){
        // Create a planet at a preset position
        super(0, 0, radius);
        position = MissileGame.generatePreset(pos);
//        if(radius > Levels.LARGE_PLANET_RADIUS) {
//            texture = new Texture(Gdx.files.internal("largePlanet.png"));
//        }
//        else if(radius < Levels.SMALL_PLANET_RADIUS){
//            texture = new Texture(Gdx.files.internal("smallPlanet.png"));
//        }
//        else{
//            texture = new Texture(Gdx.files.internal("planetLayer0.png"));
//        }
        generateSprites();
        gravity = true;
        this.mass = mass;
    }

    public void generateSprites(){
        texture0 = new Texture(Gdx.files.internal("planet/layer0_00.png"));
        sprite0 = new Sprite(texture0);
        sprite0.setOrigin(radius, radius);
        sprite0.setColor(Color.valueOf("204699"));

        texture1 = new Texture(Gdx.files.internal("planet/layer1_00.png"));
        sprite1 = new Sprite(texture1);
        sprite1.setOrigin(radius, radius);
        sprite1.setColor(Color.valueOf("15649B"));

        texture2 = new Texture(Gdx.files.internal("planet/layer2_14.png"));
        sprite2 = new Sprite(texture2);
        sprite2.setOrigin(radius, radius);
        sprite2.setColor(Color.valueOf("6FCD51"));

        texture3 = new Texture(Gdx.files.internal("planet/layer3_00.png"));
        sprite3 = new Sprite(texture3);
        sprite3.setOrigin(radius, radius);
    }

    @Override
    public void update(){
        // planet doesn't move
    }

    @Override
    public void draw(){
        MissileGame.batch.begin();
        sprite0.setSize(radius * (float)2.8, radius * (float)2.8);
        sprite0.setPosition(position.x - radius * (float)1.4, position.y - radius* (float)1.4);
        sprite0.draw(MissileGame.batch);

        sprite1.setSize(radius * 2, radius * 2);
        sprite1.setPosition(position.x - radius, position.y - radius);
        sprite1.draw(MissileGame.batch);

        sprite2.setSize(radius * 2, radius * 2);
        sprite2.setPosition(position.x - radius, position.y - radius);
        sprite2.draw(MissileGame.batch);

        sprite3.setSize(radius * 2, radius * 2);
        sprite3.setPosition(position.x - radius, position.y - radius);
        sprite3.draw(MissileGame.batch);
        MissileGame.batch.end();
    }
}
