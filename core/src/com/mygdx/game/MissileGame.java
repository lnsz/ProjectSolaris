package com.mygdx.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.Random;


/**
 * Created by lucas on 7/22/2016.
 */
public class MissileGame extends ApplicationAdapter implements GestureDetector.GestureListener, InputProcessor {
    private SpriteBatch batch;
    private ShapeRenderer renderer;
    private OrthographicCamera camera;
    public static float height, width, cameraHeight, cameraWidth, cameraOriginX, cameraOriginY,
            maxHeight, maxWidth, maxOriginX, maxOriginY,
            defaultHeight, defaultWidth, defaultOriginX, defaultOriginY, entityBorder;
    public static double GRAVITY_CONSTANT = 6.67e-11;
    public static double DISTANCE_UNITS = 500; // 1 pixel = 500 km
    public static double MASS_UNITS = 1e22; // 1 mass unit = 1e22 kg
    public static Random generator;
    EntitySystem entities;
    GestureDetector gestureDetector;
    InputMultiplexer inputMultiplexer;
    FPSLogger fpsLogger;
    float maxZoom, defaultZoom;

    // Touch variables
    long lastDown, lastDuration;
    boolean isPressed = false;

    @Override
    public void create(){
        // Set width and height
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        // Create the camera, SpriteBatch, ShapeRenderer and initialize camera variables
        camera = new OrthographicCamera();
        camera.setToOrtho(true, width, height); // By default libgdx has 0, 0 be the bottom left
        // corner, this should make it normal, 0, 0 top right corner
        updateCamera();
        maxZoom = 4;
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
        camera.zoom = defaultZoom;
        batch = new SpriteBatch(); // Use to draw sprites
        renderer = new ShapeRenderer(); // Use to draw shapes

        // Create fps logger
        fpsLogger = new FPSLogger();

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

        entities.addEntity(new Comet(0, Math.PI, 20, height / 10), true);
        generator = new Random();
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

    public void updateCamera(){
        camera.update();
        cameraHeight = camera.viewportHeight * camera.zoom;
        cameraWidth = camera.viewportWidth * camera.zoom;
        cameraOriginX = -(cameraWidth - width) / 2;
        cameraOriginY = -(cameraHeight - height) / 2;
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

    public float remap(float n, float min1, float max1, float min2, float max2){
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

        if (isPressed){
            strMeter();
        }
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(255, 255, 255, 1);
        renderer.circle(remap(width / 2, 0, width, defaultOriginX, defaultOriginX + defaultWidth),
                remap(height - height / 15, 0, height, defaultOriginY, defaultOriginY + defaultHeight), 50); // Temp player character
        renderer.end();
        entities.run(batch, renderer);
        // fpsLogger.log(); // Uncomment to show fps
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        if (initialDistance / distance > 1 && camera.zoom < maxZoom){
            camera.zoom += 0.01;
        }
        else if(camera.zoom > 1){
            camera.zoom -= 0.01;
        }
        isPressed = false;
        return true;
    }

    @Override
    public boolean touchDown (float x, float y, int pointer, int button) {
        lastDown = System.currentTimeMillis();
        isPressed = true;
        return true;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        if(isPressed) {
            lastDuration = System.currentTimeMillis() - lastDown;
            entities.addEntity(new Missile(remap(width / 2, 0, width, defaultOriginX, defaultOriginX + defaultWidth),
                    remap(height - height / 15, 0, height, defaultOriginY, defaultOriginY + defaultHeight),
                    remap(x, 0, width, cameraOriginX, cameraOriginX + cameraWidth),
                    remap(y, 0, height, cameraOriginY, cameraOriginY + cameraHeight),
                    (lastDuration > 500) ? (lastDuration / 50) : 10, entities), true); // Min str is 10
            isPressed = false;
        }
        return true;
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
    public boolean fling(float velocityX, float velocityY, int button){
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button){
        return false;
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
    public boolean touchDragged(int x, int y, int pointer) {
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
