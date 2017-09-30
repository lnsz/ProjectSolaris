package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
    int planetType; //  0: Rock, F1: Gas
    int surfaceTemp; // -250 to 1000
    int tempRange;
    ColourScheme colourScheme; // 0-x for rock planets, x-y for gas planets
    boolean reversedColours;
    int layer0;
    int layer1;
    int layer2;
    int layer3;
    int rotation;
    boolean randomSize;
    String name;
    int nameLine;
    String atmosphere;
    float atmosphereComposition;
    boolean displayInfo;


    public Planet(float locX, float locY, float radius, float mass){
        super(ProjectSolaris.remap(locX, 0, ProjectSolaris.width,
                ProjectSolaris.origin.x,
                ProjectSolaris.origin.x + ProjectSolaris.width),
                ProjectSolaris.remap(locY, 0, ProjectSolaris.height,
                        ProjectSolaris.origin.y,
                        ProjectSolaris.origin.y + ProjectSolaris.height), radius);
        this.mass = mass;
        randomSize = radius < 0 && mass < 0; // If radius and mass are less than zero, they are randomized
        generateVariables();
        generateSprites();
        gravity = true;
    }

    public Planet(ProjectSolaris.Preset pos, float radius, float mass){
        // Create a planet at a preset position
        super(0, 0, radius);
        position = ProjectSolaris.generatePreset(pos);
        this.mass = mass;
        randomSize = radius < 0 && mass < 0; // If radius and mass are less than zero, they are randomized
        generateVariables();
        generateSprites();
        gravity = true;
    }

    public void generateSize(){
        radius = ProjectSolaris.randomInt((int) ProjectSolaris.height / 12, (int) ProjectSolaris.height / 5);
        mass = ProjectSolaris.randomInt(600, 2000);
    }
    public void generatePlanetType(){
        planetType = ProjectSolaris.randomInt(0, 1);
    }
    public void generateTempRange(){
        tempRange = ProjectSolaris.randomInt(0, 4);
    }
    public void generateColourScheme(){
        switch(tempRange) {
            case 0:
                surfaceTemp = ProjectSolaris.randomInt(-250, -100);
                colourScheme = new ColourScheme(ProjectSolaris.randomInt(0, 7), ProjectSolaris.randomInt(0, 7), planetType);
                break;
            case 1:
                surfaceTemp = ProjectSolaris.randomInt(-99, 0);
                colourScheme = new ColourScheme(ProjectSolaris.randomInt(8, 15), ProjectSolaris.randomInt(8, 15), planetType);
                break;
            case 2:
                surfaceTemp = ProjectSolaris.randomInt(1, 70);
                colourScheme = new ColourScheme(ProjectSolaris.randomInt(16, 23), ProjectSolaris.randomInt(16, 23), planetType);
                break;
            case 3:
                surfaceTemp = ProjectSolaris.randomInt(71, 200);
                colourScheme = new ColourScheme(ProjectSolaris.randomInt(24, 31), ProjectSolaris.randomInt(24, 31), planetType);
                break;
            case 4:
                surfaceTemp = ProjectSolaris.randomInt(201, 1000);
                colourScheme = new ColourScheme(ProjectSolaris.randomInt(32, 39), ProjectSolaris.randomInt(32, 39), planetType);
                break;
        }
    }
    public void generateLayers(){
        if (planetType == 0){ // Rock
            layer2 =  ProjectSolaris.randomInt(0, 5);
            if (tempRange == 2 && ProjectSolaris.randomInt(0, 1) == 1){ // 50% chance to have clouds if the planet is in temp range 2
                layer3 =  ProjectSolaris.randomInt(1, 10);
            } else{
                layer3 = 0;
            }
            reversedColours = false;
        } else{ // Gas
            layer2 =  ProjectSolaris.randomInt(20, 39);
            if (ProjectSolaris.randomInt(1, 4) == 1){
                layer3 = ProjectSolaris.randomInt(30, 32);
            } else{
                layer3 = 0;
            }
            reversedColours = ProjectSolaris.randomInt(0, 3) == 0; // 25% chance of reversing colours
        }
    }
    public void generateRotation(){
        rotation = ProjectSolaris.randomInt(0, 360);
    }
    public void generateName(){
        name = ProjectSolaris.planetNames[ProjectSolaris.randomInt(0, 23)] + "-" + String.format(Locale.US, "%02d", ProjectSolaris.randomInt(0, 99));
    }
    public void generateAtmosphere(){
        atmosphere =  ProjectSolaris.planetAtmosphere[ProjectSolaris.randomInt(0, 7)];
        atmosphereComposition = ProjectSolaris.randomInt(60, 100);
    }


    public void generateVariables(){
        layer0 = 0;
        layer1 = 0;
        nameLine = 0;
        if (randomSize){
            generateSize();
        }
        generatePlanetType();
        generateTempRange();
        generateColourScheme();
        generateLayers();
        generateRotation();
        generateName();
        generateAtmosphere();
    }

    public void generateSprites(){
        String c1 = reversedColours ? colourScheme.c2  : colourScheme.c1;
        String c2 = reversedColours ? colourScheme.c1  : colourScheme.c2;
        String c3 = colourScheme.c3;
        texture0 = new Texture(Gdx.files.internal("planet/layer0_" + String.format(Locale.US, "%02d", layer0) + ".png"));
        sprite0 = new Sprite(texture0);
        sprite0.setOrigin(radius, radius);
        sprite0.setColor(Color.valueOf(c1));

        texture1 = new Texture(Gdx.files.internal("planet/layer1_" + String.format(Locale.US, "%02d", layer1) + ".png"));
        sprite1 = new Sprite(texture1);
        sprite1.setOrigin(radius, radius);
        sprite1.setColor(Color.valueOf(c1));

        texture2 = new Texture(Gdx.files.internal("planet/layer2_" + String.format(Locale.US, "%02d", layer2) + ".png"));
        sprite2 = new Sprite(texture2);
        sprite2.setOrigin(radius, radius);
        sprite2.setColor(Color.valueOf(c2));
        sprite2.rotate(rotation);

        texture3 = new Texture(Gdx.files.internal("planet/layer3_" + String.format(Locale.US, "%02d", layer3) + ".png"));
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
        ProjectSolaris.batch.begin();
        sprite0.setSize(radius * (float)2.8, radius * (float)2.8);
        sprite0.setPosition(position.x - radius * (float)1.4, position.y - radius* (float)1.4);
        sprite0.draw(ProjectSolaris.batch);

        sprite1.setSize(radius * 2, radius * 2);
        sprite1.setPosition(position.x - radius, position.y - radius);
        sprite1.draw(ProjectSolaris.batch);

        sprite2.setSize(radius * 2, radius * 2);
        sprite2.setPosition(position.x - radius, position.y - radius);
        sprite2.draw(ProjectSolaris.batch);

        if (planetType == 1){
            sprite3.setSize(radius * 3, radius * 3);
            sprite3.setPosition(position.x - radius * (float)1.5, position.y - radius * (float)1.5);
        } else {
            sprite3.setSize(radius * 2, radius * 2);
            sprite3.setPosition(position.x - radius, position.y - radius);
        }
        sprite3.draw(ProjectSolaris.batch);

        ProjectSolaris.batch.end();

        if (displayInfo){
            drawInfo();
        }
    }

    public void drawInfo(){
        int textY = (int)(ProjectSolaris.height / 100.0);
        int lineHeight = 0;
        int lineWidth = 0;
        String text = "";

        ProjectSolaris.batch.begin();
        ProjectSolaris.dinPro1.getData().setScale((float)0.7);
        ProjectSolaris.dinPro2.getData().setScale((float)0.7);
        ProjectSolaris.glyphLayout.setText(ProjectSolaris.dinPro2, name.toUpperCase());
        ProjectSolaris.dinPro2.draw(ProjectSolaris.batch, ProjectSolaris.glyphLayout, 0, textY);
        textY +=  ProjectSolaris.glyphLayout.height;
        System.out.println(ProjectSolaris.glyphLayout.height);
        lineWidth +=  ProjectSolaris.glyphLayout.width;
        ProjectSolaris.dinPro1.getData().setScale((float)0.3);
        text = "Mass: " + String.format(Locale.US, "%6.3e", Math.pow(mass, 8)) + " kg";
        ProjectSolaris.glyphLayout.setText(ProjectSolaris.dinPro1, text);
        System.out.println(ProjectSolaris.glyphLayout.height);
        textY += ProjectSolaris.glyphLayout.height +  ProjectSolaris.height / 150.0;
        lineHeight += textY;
        textY +=  ProjectSolaris.height / 150.0;
        ProjectSolaris.dinPro1.draw(ProjectSolaris.batch, ProjectSolaris.glyphLayout, 0, textY);
        text = "Radius: " + NumberFormat.getNumberInstance(Locale.US).format((int)(Math.pow(radius, 3) / 700)) + " km";
        ProjectSolaris.glyphLayout.setText(ProjectSolaris.dinPro1, text);
        textY +=  ProjectSolaris.glyphLayout.height +  ProjectSolaris.height / 150.0;
        ProjectSolaris.dinPro1.draw(ProjectSolaris.batch, ProjectSolaris.glyphLayout, 0, textY);
        text = "Surface Temperature: " + this.surfaceTemp + " °C";
        ProjectSolaris.glyphLayout.setText(ProjectSolaris.dinPro1, text);
        textY +=  ProjectSolaris.glyphLayout.height +  ProjectSolaris.height / 150.0;
        ProjectSolaris.dinPro1.draw(ProjectSolaris.batch, ProjectSolaris.glyphLayout, 0, textY);
        text = "Axial Rotation: " + rotation + "°";
        ProjectSolaris.glyphLayout.setText(ProjectSolaris.dinPro1, text);
        textY +=  ProjectSolaris.glyphLayout.height +  ProjectSolaris.height / 150.0;
        ProjectSolaris.dinPro1.draw(ProjectSolaris.batch, ProjectSolaris.glyphLayout, 0, textY);
        text = "Atmospheric Composition: " + atmosphere + " (" + (int)atmosphereComposition + "%)";
        ProjectSolaris.glyphLayout.setText(ProjectSolaris.dinPro1, text);
        textY +=  ProjectSolaris.glyphLayout.height +  ProjectSolaris.height / 150.0;
        ProjectSolaris.dinPro1.draw(ProjectSolaris.batch, ProjectSolaris.glyphLayout, 0, textY);
        ProjectSolaris.batch.end();

        ProjectSolaris.renderer.begin(ShapeRenderer.ShapeType.Line);
        ProjectSolaris.renderer.setColor(255, 255, 255, 1);
        if (nameLine < lineWidth){
            nameLine +=  ProjectSolaris.width / 40.0;
        }

        ProjectSolaris.renderer.line(0, lineHeight, nameLine, lineHeight);
        ProjectSolaris.renderer.end();
    }
}
