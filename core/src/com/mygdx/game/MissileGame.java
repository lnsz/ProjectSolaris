package com.mygdx.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Random;


/**
 * Created by lucas on 7/22/2016.
 */
public class MissileGame extends ApplicationAdapter implements InputProcessor {
    private SpriteBatch batch;
    private ShapeRenderer renderer;
    private OrthographicCamera camera;
    public static float height, width;
    public static Random generator;
    EntitySystem entities;

    // Touch variables
    long lastDown, lastDuration;
    boolean isPressed = false;

    @Override
    public void create(){
        // Set width and height
        width = Gdx.graphics.getWidth();
        height = Gdx.graphics.getHeight();

        // Create the camera, SpriteBatch and ShapeRenderer
        camera = new OrthographicCamera();
        camera.setToOrtho(true, width, height); // By default libgdx has 0, 0 be the bottom left corner, this should make it normal, 0, 0 top right corner
        batch = new SpriteBatch(); // Use to draw sprites
        renderer = new ShapeRenderer(); // Use to draw shapes

        // Start input processor
        Gdx.input.setInputProcessor(this);

        // Create Entity System and add a planet to it
        entities = new EntitySystem();
        entities.addEntity(new Obstacle(Obstacle.Type.PLANET, width / 2, 500, 100), true);

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
        camera.update();

        // Tell the SpriteBatch to render in the camera
        renderer.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);
    }

    public void strMeter(){
        if (System.currentTimeMillis() - lastDown > 3000){ // Max str is 30, after that it resets
            lastDown = System.currentTimeMillis();
        }
        double timeRatio = (System.currentTimeMillis() - lastDown) / 3000.0;
        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(255, 255, 255, 1);
        renderer.circle(width / 2, height - 100, (float)(500 * timeRatio));
        renderer.end();
    }

    public void background(){
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(0, 0, 0, 1);
        renderer.rect(0, 0, width, height);
        renderer.end();
    }

    @Override
    public void render() {
        clearScreen();
        background();

        if (isPressed){
            strMeter();
        }

        entities.run(batch, renderer);

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
    public boolean keyDown (int keycode) {
        return false;
    }

    @Override
    public boolean keyUp (int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped (char character) {
        return false;
    }

    @Override
    public boolean touchDown (int x, int y, int pointer, int button) {
        lastDown = System.currentTimeMillis();
        isPressed = true;
        return true;
    }

    @Override
    public boolean touchUp (int x, int y, int pointer, int button) {
        lastDuration = System.currentTimeMillis() - lastDown;
        entities.addEntity(new Missile(width / 2, height- 100, x, y,
                (lastDuration > 500)?(lastDuration / 50) : 10, entities), true); // Min str is 10
        isPressed = false;
        return true;
    }

    @Override
    public boolean touchDragged (int x, int y, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved (int x, int y) {
        return false;
    }

    @Override
    public boolean scrolled (int amount) {
        return false;
    }
}
