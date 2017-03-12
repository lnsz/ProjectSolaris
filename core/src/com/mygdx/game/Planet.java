package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.text.NumberFormat;
import java.util.Locale;


/**
 * Created by lucas on 8/3/2016.
 */
// Colour schemes (layer 0, layer 1, layer 2, layer 3):
// 00 - Earth             : 204699, 15649B, 6FCD51, 000000
// 01 - Jupiter           : FFEBD0, FFD7A3, F5CC94, ******
public class Planet extends Obstacle{
    // Stationary, have their own gravity

    // Planet images are in planet/ with the format layerAB, where A is the layer number and
    // B is the index
    // Layer 00: Atmosphere
    // Layer 10: Planet background
    // Layer 2x: Planet foreground
    //           0  <= x <= 05: Rock planets
    //           20 <= x <= 39: Gas planets
    // Layer 3x: Clouds
    //           00:           No clouds
    //           01 <= x <= 10: Clouds


    Sprite sprite0;
    Sprite sprite1;
    Sprite sprite2;
    Sprite sprite3;
    Texture texture0;
    Texture texture1;
    Texture texture2;
    Texture texture3;

    // Planet type and surface temp determine colour scheme
    // Planet type determines the layer pattern
    int planetType; //  0: Rock, 1: Gas
    int surfaceTemp; // -250 to 1000
    int tempRange;
    ColourScheme colourScheme; // 0-x for rock planets, x-y for gas planets
    boolean reversedColours;
    int layer0;
    int layer1;
    int layer2;
    int layer3;
    int rotation;
    int seed;
    boolean randomSize;
    String name;
    int nameLine;
    String atmosphere;
    float atmosphereComposition;
    boolean displayInfo;


    public Planet(float locX, float locY, float radius, float mass){
        super(MissileGame.remap(locX, 0, MissileGame.width,
                MissileGame.defaultOriginX,
                MissileGame.defaultOriginX + MissileGame.defaultWidth),
                MissileGame.remap(locY, 0, MissileGame.height,
                        MissileGame.defaultOriginY,
                        MissileGame.defaultOriginY + MissileGame.defaultHeight), radius);
        this.mass = mass;
        randomSize = radius < 0 && mass < 0; // If radius and mass are less than zero, they are randomized
        generateSeed();
        generateSprites();
        gravity = true;
    }

    public Planet(MissileGame.Preset pos, float radius, float mass){
        // Create a planet at a preset position
        super(0, 0, radius);
        position = MissileGame.generatePreset(pos);
        this.mass = mass;
        randomSize = radius < 0 && mass < 0; // If radius and mass are less than zero, they are randomized
        generateSeed();
        generateSprites();
        gravity = true;
    }

    public void generateSeed(){
        if (randomSize){
            radius = MissileGame.randomInt((int)MissileGame.height / 12, (int)MissileGame.height / 5);
            mass = MissileGame.randomInt(600, 2000);
        }

        nameLine = 0;
        planetType = MissileGame.randomInt(0, 1);

        tempRange = MissileGame.randomInt(0, 4);
        switch(tempRange) {
            case 0:
                surfaceTemp = MissileGame.randomInt(-250, -100);
                colourScheme = new ColourScheme(MissileGame.randomInt(0, 7), MissileGame.randomInt(0, 7), planetType);
                break;
            case 1:
                surfaceTemp = MissileGame.randomInt(-99, 0);
                colourScheme = new ColourScheme(MissileGame.randomInt(8, 15), MissileGame.randomInt(8, 15), planetType);
                break;
            case 2:
                surfaceTemp = MissileGame.randomInt(1, 70);
                colourScheme = new ColourScheme(MissileGame.randomInt(16, 23), MissileGame.randomInt(16, 23), planetType);
                break;
            case 3:
                surfaceTemp = MissileGame.randomInt(71, 200);
                colourScheme = new ColourScheme(MissileGame.randomInt(24, 31), MissileGame.randomInt(24, 31), planetType);
                break;
            case 4:
                surfaceTemp = MissileGame.randomInt(201, 1000);
                colourScheme = new ColourScheme(MissileGame.randomInt(32, 39), MissileGame.randomInt(32, 39), planetType);
                break;
        }
        layer0 = 0;
        layer1 = 0;

        if (planetType == 0){ // Rock
            layer2 =  MissileGame.randomInt(0, 5);
            if (tempRange == 2 && MissileGame.randomInt(0, 1) == 1){ // 50% chance to have clouds if the planet is in temp range 2
                layer3 =  MissileGame.randomInt(1, 10);
            } else{
                layer3 = 0;
            }
            reversedColours = false;
        } else{ // Gas
            layer2 =  MissileGame.randomInt(20, 39);
            if (MissileGame.randomInt(1, 4) == 1){
                layer3 = MissileGame.randomInt(30, 32);
            } else{
                layer3 = 0;
            }
            reversedColours = MissileGame.randomInt(0, 3) == 0; // 25% chance of reversing colours
        }

        rotation = MissileGame.randomInt(0, 360);

        name = MissileGame.planetNames[MissileGame.randomInt(0, 23)] + "-" + String.format("%02d", MissileGame.randomInt(0, 99));
        atmosphere =  MissileGame.planetAtmosphere[MissileGame.randomInt(0, 7)];
        atmosphereComposition = MissileGame.randomInt(60, 100);
    }

    public void readSeed(){

    }


    public void generateSprites(){
        String c1 = reversedColours ? colourScheme.c2  : colourScheme.c1;
        String c2 = reversedColours ? colourScheme.c1  : colourScheme.c2;
        String c3 = colourScheme.c3;
        System.out.println("c1: " + c1 + ", c2: " + c2 + ", c3: " + c3);
        texture0 = new Texture(Gdx.files.internal("planet/layer0_" + String.format("%02d", layer0) + ".png"));
        sprite0 = new Sprite(texture0);
        sprite0.setOrigin(radius, radius);
        sprite0.setColor(Color.valueOf(c1));

        texture1 = new Texture(Gdx.files.internal("planet/layer1_" + String.format("%02d", layer1) + ".png"));
        sprite1 = new Sprite(texture1);
        sprite1.setOrigin(radius, radius);
        sprite1.setColor(Color.valueOf(c1));

        texture2 = new Texture(Gdx.files.internal("planet/layer2_" + String.format("%02d", layer2) + ".png"));
        sprite2 = new Sprite(texture2);
        sprite2.setOrigin(radius, radius);
        sprite2.setColor(Color.valueOf(c2));
        sprite2.rotate(rotation);

        texture3 = new Texture(Gdx.files.internal("planet/layer3_" + String.format("%02d", layer3) + ".png"));
        sprite3 = new Sprite(texture3);
        sprite3.setColor(Color.valueOf(c3));
        if (planetType == 1) {
            sprite3.setOrigin(radius * (float) 1.5, radius * (float) 1.5);
        } else{
            sprite3.setOrigin(radius, radius);
        }
        sprite3.rotate(rotation);
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

        if (planetType == 1){
            sprite3.setSize(radius * 3, radius * 3);
            sprite3.setPosition(position.x - radius * (float)1.5, position.y - radius * (float)1.5);
        } else {
            sprite3.setSize(radius * 2, radius * 2);
            sprite3.setPosition(position.x - radius, position.y - radius);
        }
        sprite3.draw(MissileGame.batch);

        MissileGame.batch.end();

        if (randomSize){
            drawInfo();
        }
    }

    public void drawInfo(){
        int textY = (int)(MissileGame.height / 100.0);
        int lineHeight = 0;
        int lineWidth = 0;

        MissileGame.batch.begin();
        MissileGame.arial.getData().setScale((float)0.7);
        MissileGame.glyphLayout.setText(MissileGame.arial, name);
        MissileGame.arial.draw(MissileGame.batch, MissileGame.glyphLayout, 0, textY);
        textY +=  MissileGame.glyphLayout.height;
        lineWidth +=  MissileGame.glyphLayout.width;
        MissileGame.arial.getData().setScale((float)0.3);
        MissileGame.glyphLayout.setText(MissileGame.arial, "Mass: " + String.format("%6.3e", Math.pow(mass, 8)) + " kg");
        textY += MissileGame.height / 100.0;
        lineHeight += textY;
        textY +=  MissileGame.glyphLayout.height;
        MissileGame.arial.draw(MissileGame.batch, MissileGame.glyphLayout, 0, textY);
        MissileGame.glyphLayout.setText(MissileGame.arial, "Radius: " + NumberFormat.getNumberInstance(Locale.US).format((int)(Math.pow(radius, 3) / 700)) + " km");
        textY +=  MissileGame.glyphLayout.height +  MissileGame.height / 150.0;
        MissileGame.arial.draw(MissileGame.batch, MissileGame.glyphLayout, 0, textY);
        MissileGame.glyphLayout.setText(MissileGame.arial, "Surface Temperature: " + this.surfaceTemp + " °C");
        textY +=  MissileGame.glyphLayout.height +  MissileGame.height / 150.0;
        MissileGame.arial.draw(MissileGame.batch, MissileGame.glyphLayout, 0, textY);
        MissileGame.glyphLayout.setText(MissileGame.arial, "Axial Rotation: " + rotation + "°");
        textY +=  MissileGame.glyphLayout.height +  MissileGame.height / 150.0;
        MissileGame.arial.draw(MissileGame.batch, MissileGame.glyphLayout, 0, textY);
        MissileGame.glyphLayout.setText(MissileGame.arial, "Atmospheric Composition: " + atmosphere + "(" + (int)atmosphereComposition + "%)");
        textY +=  MissileGame.glyphLayout.height +  MissileGame.height / 150.0;
        MissileGame.arial.draw(MissileGame.batch, MissileGame.glyphLayout, 0, textY);
        MissileGame.batch.end();

        MissileGame.renderer.begin(ShapeRenderer.ShapeType.Line);
        MissileGame.renderer.setColor(255, 255, 255, 1);
        if (nameLine < lineWidth){
            nameLine +=  MissileGame.width / 40.0;
            System.out.println(nameLine);
        }

        MissileGame.renderer.line(0, lineHeight, nameLine, lineHeight);
        MissileGame.renderer.end();
    }
}
