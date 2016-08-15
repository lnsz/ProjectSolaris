package com.mygdx.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


/**
 * Created by lucas on 7/22/2016.
 */
public class MissileGame extends ApplicationAdapter implements GestureDetector.GestureListener, InputProcessor {
    public static SpriteBatch batch;
    public static ShapeRenderer renderer;
    public static OrthographicCamera camera;
    public static float height, width, cameraHeight, cameraWidth, cameraOriginX, cameraOriginY,
            maxHeight, maxWidth, maxOriginX, maxOriginY,
            defaultHeight, defaultWidth, defaultOriginX, defaultOriginY, entityBorder;
    public static double GRAVITY_CONSTANT = 6.67e-11;
    public static double DISTANCE_UNITS = 500; // 1 pixel = 500 km
    public static double MASS_UNITS = 1e22; // 1 mass unit = 1e22 kg
    public static Random generator;
    enum Mode {START_SCREEN,
            MAIN_MENU,
            PLAY,
            PAUSE,
        LEVEL_SELECTOR
    }
    Button startButton, playButton, settingsButton, shopButton, pauseButton, resumeButton,
            menuButton, levelButton;
    Mode mode;
    EntitySystem entities;
    GestureDetector gestureDetector;
    InputMultiplexer inputMultiplexer;
    FPSLogger fpsLogger;
    float maxZoom, defaultZoom, startZoom;
    int levelX, levelY, levelNumber, levelSelected, episodeNumber, episodeSelected;
    boolean levelScroll = false;
    ArrayList<Button> levelList;

    // Touch variables
    long lastDown, lastDuration;
    Vector lastTouch, lastTap;
    boolean isPressed = false;

    @Override
    public void create(){
        // Set width and height
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        // Create the camera, SpriteBatch, ShapeRenderer and initialize camera variables
        setUpCamera();
        batch = new SpriteBatch(); // Use to draw sprites
        renderer = new ShapeRenderer(); // Use to draw shapes

        // Create fps logger
        fpsLogger = new FPSLogger();

        // Set mode
        mode = Mode.START_SCREEN;

        // Create buttons
        createButtons();

        // Start input processor
        gestureDetector = new GestureDetector(this);
        InputMultiplexer inputMultiplexer = new InputMultiplexer(gestureDetector, this);
        Gdx.input.setInputProcessor(inputMultiplexer);

        // Create Entity System and add a planet to it
        entities = new EntitySystem();
        Planet planet = new Planet(width / 2, height / 5, height / 10, 600);
        entities.addEntity(planet, true);
        entities.addEntity(new Moon(height / 20, 400, true, Math.PI / 2, planet, 1000), true);
        entities.addEntity(new Moon(height / 20, 400, true, 3 * Math.PI / 2, planet, 1000), true);
//        entities.addEntity(new Moon(width / 2, height / 5, height / 20, 400, true, 0, planet, 1000), true);
        entities.addEntity(new Moon(height / 20, 400, true, Math.PI, planet, 1000), true);
//        entities.addEntity(new Moon(width / 2, height / 5, height / 20, 400, true, Math.PI / 6, planet, 500, 2000), true);
        entities.addEntity(new Asteroid(height / 30, true, 0, planet, 1000), true);

        entities.addEntity(new Comet(Math.PI / 2 * 3, Math.PI / 2, 20, height / 20), true);

        // Start random number generator
        generator = new Random();

        lastTouch = new Vector(0, 0);
        lastTap = new Vector(0, 0);
    }

    @Override
    public void dispose(){
        batch.dispose();
        renderer.dispose();
    }

    public void clearScreen(){
        // Clear screen
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Reset camera
        updateCamera();

        // Tell the SpriteBatch to render in the camera
        renderer.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);
    }

    public void createButtons(){
        Texture tempTexture = new Texture(Gdx.files.internal("startButton.png"));
        Sprite tempSprite = new Sprite(tempTexture);
        startButton = new Button(0, 0, width, height, tempSprite);

        tempTexture = new Texture(Gdx.files.internal("playButton.png"));
        tempSprite = new Sprite(tempTexture);
        playButton = new Button(width / 6, height / 3, 2 * width / 3, height / 10, tempSprite);
        levelButton = new Button(width / 6, 8 * height / 9, 2 * width / 3, height / 10, tempSprite);

        tempTexture = new Texture(Gdx.files.internal("shopButton.png"));
        tempSprite = new Sprite(tempTexture);
        shopButton = new Button(width / 6, height / 2, 2 * width / 3, height / 10, tempSprite);

        tempTexture = new Texture(Gdx.files.internal("settingsButton.png"));
        tempSprite = new Sprite(tempTexture);
        settingsButton = new Button(width / 6, 2 * height / 3, 2 * width / 3, height / 10, tempSprite);

        tempTexture = new Texture(Gdx.files.internal("pauseButton.png"));
        tempSprite = new Sprite(tempTexture);
        pauseButton = new Button(0, height - height / 20, width / 10, height / 20, tempSprite);

        tempTexture = new Texture(Gdx.files.internal("resumeButton.png"));
        tempSprite = new Sprite(tempTexture);
        resumeButton = new Button(width / 6, height / 3, 2 * width / 3, height / 10, tempSprite);

        tempTexture = new Texture(Gdx.files.internal("menuButton.png"));
        tempSprite = new Sprite(tempTexture);
        menuButton = new Button(width / 6, 2 * height / 3, 2 * width / 3, height / 10, tempSprite);

        tempTexture = new Texture(Gdx.files.internal("levelButton.png"));
        tempSprite = new Sprite(tempTexture);
        episodeNumber = 3;
        levelX = 3;
        levelY = 5;
        levelSelected = -1;
        episodeSelected = -1;
        levelNumber = levelX * levelY * episodeNumber;
        levelList = new ArrayList<Button>();
        for (int i = 0; i < episodeNumber; i++){
            for (int y = 0; y < levelY; y++) {
                for (int x = 0; x < levelX; x++){
                    float levelButtonX = width / 5 + x * width / 5 + width * i + (width / 5 - width / 6) / 2;
                    float levelButtonY = height / 7 + y * height / 7 + (height / 7 - height / 8) / 2;
                    Button b = new Button(levelButtonX, levelButtonY, width / 6, height / 8, tempSprite);
                    levelList.add(b);
                }
            }
        }

    }

    public void updateCamera(){
        camera.update();
        cameraHeight = camera.viewportHeight * camera.zoom;
        cameraWidth = camera.viewportWidth * camera.zoom;
        cameraOriginX = -(cameraWidth - width) / 2;
        cameraOriginY = -(cameraHeight - height) / 2;
    }

    public void setUpCamera(){
        camera = new OrthographicCamera();
        camera.setToOrtho(true, width, height); // By default libgdx has 0, 0 be the bottom left
        // corner, this should make it normal, 0, 0 top right corner
        updateCamera();
        maxZoom = 4;
        startZoom = 1;
        defaultZoom = 2;
        maxHeight = camera.viewportHeight * maxZoom;
        maxWidth = camera.viewportWidth * maxZoom;
        maxOriginX = width / 2 - maxWidth / 2;
        maxOriginY = height / 2 - maxHeight / 2;
        entityBorder = (float)Math.sqrt(Math.pow(maxWidth / 2, 2) + Math.pow(maxHeight / 2, 2)); // Maximum distance that entities can be from the center
        defaultHeight = camera.viewportHeight * defaultZoom;
        defaultWidth = camera.viewportWidth * defaultZoom;
        defaultOriginX = width / 2 - defaultWidth / 2;
        defaultOriginY = height / 2 - defaultHeight / 2;
        camera.zoom = startZoom;
    }

    public void strMeter(){
        if (System.currentTimeMillis() - lastDown > 3000){ // Max str is 30, after that it resets
            lastDown = System.currentTimeMillis();
        }
        double timeRatio = (System.currentTimeMillis() - lastDown) / 3000.0;
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(255, 255, 255, 1);
        renderer.circle(remap(width / 2, 0, width, defaultOriginX, defaultOriginX + defaultWidth),
                remap(height - height / 15, 0, height, defaultOriginY, defaultOriginY + defaultHeight),
                (float)(500 * timeRatio));
        renderer.end();
    }

    public static float remap(float n, float min1, float max1, float min2, float max2){
        // Used to map a number from the screen resolution range to the camera resolution range
        if (min1 != max1 && min2 != max2) {
            return (((n - min1) * (max2 - min2)) / (max1 - min1)) + min2;
        }
        else{
            return (max2 + min2) / 2;
        }
    }

    public void background(){
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(0, 0, 0, 1);
        renderer.rect(cameraOriginX, cameraOriginY, cameraWidth,  cameraHeight);
        renderer.end();
    }

    @Override
    public void render() {
        clearScreen();
        background();

        checkMode();
    }

    public void checkMode(){
        switch(mode){
            case START_SCREEN:
                startButton.draw();
                break;

            case MAIN_MENU:
                playButton.draw();
                shopButton.draw();
                settingsButton.draw();
                break;

            case LEVEL_SELECTOR:
                if (levelSelected >= 0) {
                    levelButton.draw();
                }
                levelSelector();
                break;

            case PLAY:
                play();
                pauseButton.scale();
                pauseButton.draw();
                break;

            case PAUSE:
                pauseGame();
                break;
            default:
                break;
        }
    }

    public void play(){
        if (isPressed){
            strMeter();
        }
        drawShip();
        entities.run(true);
        // fpsLogger.log(); // Uncomment to show fps
    }

    public void pauseGame(){
        drawShip();
        entities.run(false);
        resumeButton.scale();
        resumeButton.draw();
        menuButton.scale();
        menuButton.draw();
    }

    public void levelSelector(){
        for (int i = 0; i < levelNumber; i++){
            if (i == levelSelected){
                levelList.get(i).selected = true;
            }
            else{
                levelList.get(i).selected = false;
            }

            if(!isPressed){
                episodeSelector();
            }
            levelList.get(i).draw();
        }
    }

    public void drawShip(){
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(255, 255, 255, 1);
        renderer.circle(remap(width / 2, 0, width, defaultOriginX, defaultOriginX + defaultWidth),
                remap(height - height / 15, 0, height, defaultOriginY, defaultOriginY + defaultHeight), 50); // Temp player character
        renderer.end();
    }
    @Override
    public boolean zoom(float initialDistance, float distance) {
        switch (mode) {
            case PLAY:
                if (initialDistance / distance > 1 && camera.zoom < maxZoom) {
                    camera.zoom += 0.01;
                } else if (camera.zoom > 1) {
                    camera.zoom -= 0.01;
                }
                isPressed = false;
                return true;

            default:
                return false;
        }
    }

    public void episodeSelector(){
        episodeSelected = (int)((width / 5 - levelList.get(0).location.x + 2 * width / 5) / width);
        //System.out.println((width / 5 - levelList.get(0).location.x + width / 5) + " " +episodeSelected);

        if(levelList.get(episodeSelected * levelX * levelY).location.x >
                width / 5 + width / 20){
            System.out.println("Move left");
//            for (int i = 0; i < episodeNumber; i++){
//                levelList.get(i).location.x -= width / 20;
//            }
        }
        else if (levelList.get(episodeSelected * levelX * levelY).location.x <
                width / 5 - width / 20){
            System.out.println("Move right");
//            for (int i = 0; i < episodeNumber; i++){
//                levelList.get(i).location.x += width / 20;
//            }
        }
    }

    @Override
    public boolean touchDown (float x, float y, int pointer, int button) {
        lastTouch.x = x;
        lastTouch.y = y;
        lastTap.x = x;
        lastTap.y = y;
        switch(mode){
            case PLAY:
                lastDown = System.currentTimeMillis();
                isPressed = true;
                break;

            default:
                break;
        }
        return true;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        float remapX = remap(x, 0, width, cameraOriginX, cameraOriginX + cameraWidth);
        float remapY = remap(y, 0, height, cameraOriginY, cameraOriginY + cameraHeight);
        switch(mode){
            case START_SCREEN:
                if (startButton.isClicked(remapX, remapY)){
                    mode = Mode.MAIN_MENU;
                }
                break;
            case MAIN_MENU:
                if (playButton.isClicked(remapX, remapY)){
                    levelSelected = -1;
                    episodeSelected = -1;
                    mode = Mode.LEVEL_SELECTOR;
                }
                break;

            case LEVEL_SELECTOR:
                if (levelSelected >= 0 && levelButton.isClicked(remapX, remapY)){
                    camera.zoom = defaultZoom;
                    mode = Mode.PLAY;
                }
                Vector pos = new Vector(x, y);
                pos.sub(lastTap);
                if(pos.mag() < width / 20) {
                    boolean nullTap = true;
                    for(int i = 0; i < levelNumber; i++){
                        if (levelList.get(i).isClicked(remapX, remapY)) {
                            nullTap = false;
                            levelSelected = i;
                        }
                    }
                    if(nullTap){
                        levelSelected = -1;
                    }
                }
                isPressed = false;
                break;

            case PAUSE:
                if (resumeButton.isClicked(remapX, remapY)){
                    mode = Mode.PLAY;
                }
                if (menuButton.isClicked(remapX, remapY)){
                    camera.zoom = startZoom;
                    mode = Mode.MAIN_MENU;
                }
                break;

            case PLAY:
                if (pauseButton.isClicked(remapX, remapY)){
                    mode = Mode.PAUSE;
                    isPressed = false;
                }

                if(isPressed) {
                    lastDuration = System.currentTimeMillis() - lastDown;
                    entities.addEntity(new Missile(remap(width / 2, 0, width, defaultOriginX, defaultOriginX + defaultWidth),
                            remap(height - height / 15, 0, height, defaultOriginY, defaultOriginY + defaultHeight),
                            remapX, remapY,
                            (lastDuration > 500) ? (lastDuration / 50) : 10, entities), true); // Min str is 10
                    isPressed = false;
                }
                break;

            default:
                break;
        }
        return true;
    }


    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        float dX = x - lastTouch.x;
        lastTouch.x = x;
        float dY = y - lastTouch.y;
        lastTouch.y = y;
        switch(mode) {
            case LEVEL_SELECTOR:
                if(levelList.get(0).location.x + dX < width / 5 &&
                        levelList.get(levelNumber - 1).location.x + dX > 3 * width / 5) {
                    for (int i = 0; i < levelNumber; i++) {
                        levelList.get(i).location.x += dX;
                    }
                }

                return true;

            default:
                return false;
        }
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button){
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button){

        return false;
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }


    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2)
    {
        return false;
    }

    @Override
    public void pinchStop(){
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY){
        return false;
    }

    @Override
    public boolean longPress(float x, float y){
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button){
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean mouseMoved(int x, int y) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }


}
