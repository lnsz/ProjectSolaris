package com.mygdx.game;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by lucas on 7/22/2016.
 */
public class ProjectSolaris extends ApplicationAdapter implements GestureDetector.GestureListener, InputProcessor {
    public static SpriteBatch batch;
    public static ShapeRenderer renderer;
    public static OrthographicCamera camera;

    public static String[] planetNames = {"Alpha", "Beta", "Gamma", "Delta", "Epsilon", "Zeta", "Eta", "Theta", "Iota",
            "Kappa", "Lambda", "Mu", "Nu", "Xi", "Omicron", "Pi", "Rho", "Sigma", "Tau", "Upsilon",
            "Phi", "Chi", "Psi", "Omega"};
    public static String[] planetAtmosphere = {"Hydrogen", "Helium", "Oxygen", "Carbon Dioxide", "Nitrogen", "Methane",
            "Sulfur", "Ammonia"};
    // Screen size variables
    public static float height, width, entityBorder;
    public static Vector center, origin;
    public static double GRAVITY_CONSTANT = 6.67e-11;
    public static double DISTANCE_UNITS = 500; // 1 pixel = 500 km
    public static double MASS_UNITS = 1e22; // 1 mass unit = 1e22 kg
    public static Random generator;
    public static boolean isPaused = false; // True iff game is paused
    public static boolean resetCamera = false; // When a missile explodes, this is set to true until camera is reset
    public static float timeScale, maxTimeScale, minTimeScale, timeScaleStage,
            resolutionMult; // Used to scale velocity of entities
    public static EntitySystem entities; // Obstacles, missiles and most game objects are stored in this entity system
    public static boolean missile = false; // True iff there is a live missile
    public static boolean missileReady = true;
    public enum Mode {START_SCREEN,
            MAIN_MENU,
            PLAY,
            PAUSE,
        LEVEL_SELECTOR,
        TEST,
        LEVEL_FAILED,
    }
    public enum Preset{TOP_RIGHT,
        TOP,
        TOP_LEFT,
        CENTER_LEFT,
        CENTER,
        CENTER_RIGHT,
        BOTTOM_LEFT,
        BOTTOM,
        BOTTOM_RIGHT
    }
    Button startButton, playButton, settingsButton, testButton, shopButton, pauseButton, resumeButton,
            menuButton, levelButton, timeScaleButton, seedButton, retryButton; // Buttons
    public static Mode mode; // Mode enum used for selecting

    // Gesture detector and index multiplexer handle the touch screen
    GestureDetector gestureDetector;
    FPSLogger fpsLogger;
    int levelX, levelY, levelNumber, levelSelected, episodeNumber, episodeSelected; // Level and episode selection variables

    // Fonts
    public static BitmapFont dinPro1;
    public static BitmapFont dinPro2;
    public static BitmapFont arial;
    public static GlyphLayout glyphLayout;
    ArrayList<Button> levelList;  // List of buttons in the level selector screen

    // Touch variables
    long lastDown, lastDuration;
    Vector lastTouch, lastTap;
    public static boolean isPressed = false;
    float xDrag = 0;
    float xDragTotal = 0;
    float xDragVelocity = 0;
    int dragDuration = 0;

    // Player variables
    public static Player player;

    // Background
    public static Background bg;

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
        Gdx.input.setCatchBackKey(true);

        // Create Entity System
        entities = new EntitySystem();

        // Start random number generator
        generator = new Random();

        // Initialize touch variables
        lastTouch = new Vector(0, 0);
        lastTap = new Vector(0, 0);

        dinPro1 = new BitmapFont(Gdx.files.internal("fonts/DinPro1/DinPro1.fnt"), true);
        dinPro1.setColor(Color.WHITE);
        dinPro2 = new BitmapFont(Gdx.files.internal("fonts/DinPro2/DinPro2.fnt"), true);
        dinPro2.setColor(Color.WHITE);
        arial = new BitmapFont(Gdx.files.internal("fonts/Arial/arial.fnt"), true);
        arial.setColor(Color.WHITE);
        glyphLayout = new GlyphLayout();

        // Initialize velocity variables
        timeScaleStage = 1;
        maxTimeScale = (float)0.75;
        minTimeScale = (float)0.25;
        timeScale = minTimeScale + (maxTimeScale - minTimeScale) /  2 * timeScaleStage;
        resolutionMult = (float)(height / 1920.0);

        // Initialize player variables
        player = new Player(0, 0);

        // Initialize background
        bg = new Background(0, 0);

    }

    @Override
    public void dispose(){
        // Clear the batch and renderer
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
        // Set the texture and sprites for a button, then create the button
        Texture tempTexture = new Texture(Gdx.files.internal("button/startButton.png"));
        Sprite tempSprite = new Sprite(tempTexture);
        startButton = new Button(0, 0, width, height, tempSprite);

        tempTexture = new Texture(Gdx.files.internal("button/playButton.png"));
        tempSprite = new Sprite(tempTexture);
        playButton = new Button(width / 6, height / 3, 2 * width / 3, height / 10, tempSprite);
        levelButton = new Button(width / 6, 8 * height / 9, 2 * width / 3, height / 10, tempSprite);

        tempTexture = new Texture(Gdx.files.internal("button/shopButton.png"));
        tempSprite = new Sprite(tempTexture);
        shopButton = new Button(width / 6, height / 2, 2 * width / 3, height / 10, tempSprite);

        tempTexture = new Texture(Gdx.files.internal("button/settingsButton.png"));
        tempSprite = new Sprite(tempTexture);
        settingsButton = new Button(width / 6, 2 * height / 3, 2 * width / 3, height / 10, tempSprite);

        tempTexture = new Texture(Gdx.files.internal("button/testButton.png"));
        tempSprite = new Sprite(tempTexture);
        testButton = new Button(width / 6, (float)2.5 * height / 3, 2 * width / 3, height / 10, tempSprite);

        tempTexture = new Texture(Gdx.files.internal("button/seedButton.png"));
        tempSprite = new Sprite(tempTexture);
        seedButton = new Button(width / 6, 8 * height / 9, 2 * width / 3, height / 10, tempSprite);

        tempTexture = new Texture(Gdx.files.internal("button/pauseButton.png"));
        tempSprite = new Sprite(tempTexture);
        pauseButton = new Button(0, height - height / 20, width / 10, height / 20, tempSprite);

        tempTexture = new Texture(Gdx.files.internal("button/resumeButton.png"));
        tempSprite = new Sprite(tempTexture);
        resumeButton = new Button(width / 6, height / 3, 2 * width / 3, height / 10, tempSprite);

        tempTexture = new Texture(Gdx.files.internal("button/menuButton.png"));
        tempSprite = new Sprite(tempTexture);
        menuButton = new Button(width / 6, 2 * height / 3, 2 * width / 3, height / 10, tempSprite);

        tempTexture = new Texture(Gdx.files.internal("button/retryButton.png"));
        tempSprite = new Sprite(tempTexture);
        retryButton = new Button(width / 6, height / 3, 2 * width / 3, height / 10, tempSprite);

        tempTexture = new Texture(Gdx.files.internal("button/timeScaleButton1.png"));
        tempSprite = new Sprite(tempTexture);
        timeScaleButton = new Button(width - width / 10, height - height / 20, width / 10, height / 20, tempSprite);

        createLevelButtons();
    }

    public void createLevelButtons(){
        Texture tempTexture = new Texture(Gdx.files.internal("button/levelButton.png"));
        Sprite tempSprite = new Sprite(tempTexture);
        // All these numbers are just for figuring out the position of the button on screen
        episodeNumber = 3;
        levelX = 3;
        levelY = 5;
        levelSelected = -1;
        episodeSelected = -1;
        levelNumber = levelX * levelY * episodeNumber;
        levelList = new ArrayList<Button>();
        // For loop that creates the buttons at the correct position
        for (int i = 0; i < episodeNumber; i++){
            int count = 0;
            for (int y = 0; y < levelY; y++) {
                for (int x = 0; x < levelX; x++){
                    count++;
                    float levelButtonX = width / 5 + x * width / 5 + width * i + (width / 5 - width / 6) / 2;
                    float levelButtonY = height / 7 + y * height / 7 + (height / 7 - height / 8) / 2;
                    Button b = new Button(levelButtonX, levelButtonY, width / 6, height / 8, tempSprite, Integer.toString(count));
                    levelList.add(b);
                }
            }
        }
    }

    public static boolean outsideScreen(Vector v){
        return (v.x < camera.position.x - center.x || v.x > camera.position.x + center.x ||
                v.y < camera.position.y - center.y || v.y > camera.position.y + center.y);
    }

    public void updateCamera(){
        // Update camera to apply any changes to camera positions,
        // then set the camera variables to their updated value
        camera.update();
        height = camera.viewportHeight;
        width = camera.viewportWidth;
        center = new Vector(width / 2, height / 2);
        origin = new Vector(camera.position.x - center.x, camera.position.x - center.y);
        origin.x = camera.position.x - width / 2;
        origin.y = camera.position.y - height / 2;
    }

    public static void centerCamera() {
        camera.position.x = center.x;
        camera.position.y = center.y;
    }

    public static void resetCamera() {
        if (Vector.distance(new Vector(camera.position.x, camera.position.y), center) > 3){
            camera.position.x += (center.x - ProjectSolaris.camera.position.x) * 0.05;
            camera.position.y += (center.y - ProjectSolaris.camera.position.y) * 0.05;
        } else {
            resetCamera = false;
            missileReady = true;
        }
    }

    public void setUpCamera(){
        camera = new OrthographicCamera();
        camera.setToOrtho(true, width, height); // By default libgdx has 0, 0 be the bottom left
        // corner, this should make it normal, 0, 0 top right corner
        updateCamera();
        // Set camera variables
        entityBorder = (float)Math.sqrt(Math.pow(width / 2, 2) + Math.pow(height / 2, 2)); // Maximum distance that entities can be from the center
    }

    public void strMeter(){
        if(missileReady) {
            //don't show strMeter with no ammo
            if (player.ammo <= 0) {
                return;
            }

            // Draws the circle around player ship that shows them how strong their current shot will be
            float maxStr = 3000; // Number of milliseconds for max strength
            if (System.currentTimeMillis() - lastDown > maxStr) {
                lastDown = System.currentTimeMillis();
            }
            double timeRatio = (System.currentTimeMillis() - lastDown) / maxStr;
            renderer.begin(ShapeRenderer.ShapeType.Line);
            renderer.setColor(255, 255, 255, 1);
            renderer.circle(player.position.x, player.position.y, (float) (500 * timeRatio));
            renderer.end();
        }
    }

    public static int randomInt(int min, int max){
        Random rand = new Random();

        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        return rand.nextInt((max - min) + 1) + min;
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
        // Draws background sprite
        bg.setSize(width, height);
        bg.setPosition(origin.x, origin.y);
        bg.draw();
    }

    @Override
    public void render() {
        // This is the main loop of the game, this method will always run
        // Clear screen, draw background, then depending on current mode, do something
        clearScreen();
        background();
        checkMode();
        // drawFPS();
    }

    public void checkMode(){
        // Depending on the current mode, do something
        switch(mode){
            // Start screen is what the player sees when the game is opened
            case START_SCREEN:
                startButton.draw();
                break;

            // Main menu comes after start screen
            case MAIN_MENU:
                entities.clear();
                playButton.draw();
                shopButton.draw();
                settingsButton.draw();
                testButton.draw();
                break;

            // Level selector is what the player sees when the play button is pressed in the main menu
            case LEVEL_SELECTOR:
                if (levelSelected >= 0) { // Draws a button to start a level if a level is selected
                    levelButton.position.x = camera.position.x - levelButton.width / 2;
                    levelButton.draw();
                }
                if (!isPressed){
                    xDragTotal = 0;
                    xDrag = 0;
                    dragDuration = 0;
                    xDragVelocity /= 1.5;

                    if(camera.position.x - xDragVelocity * 5 > width / 2 &&
                            camera.position.x - xDragVelocity * 5 < width * episodeNumber - width / 2) {
                        camera.translate(-xDragVelocity * 5, 0);
                    }
                }
                levelSelector();
                break;

            // Where the game happens, to get to this the player has to press the level button in level selector
            case PLAY:
                play();
                break;

            // Pauses the game stopping all movement
            case PAUSE:
                pauseGame();
                break;

            case LEVEL_FAILED:
                levelFailed();
                break;

            case TEST:
                seedButton.draw();
                entities.run();
                break;

            default:
                break;
        }
    }

    public void levelStatus() {
        // Checks if level is finished or failed
        if (missileReady && !player.hasAmmo()){
            mode = Mode.LEVEL_FAILED;
        }
    }

    public void play(){
        // Show strength of shot if the player is currently pressing anywhere on the screen
        isPaused = false;
        levelStatus();
        if (resetCamera){
            resetCamera();
        }
        if (isPressed){
            strMeter();
        }
        player.draw(); // Draw player ship
        entities.run(); //  Run all the movement, collisions and drawing of entities

        pauseButton.scale();
        pauseButton.draw();
        timeScaleButton.scale();
        timeScaleButton.draw();
        timeScale = minTimeScale + (maxTimeScale - minTimeScale) /  2 * timeScaleStage;

        // fpsLogger.log(); // Uncomment to show fps
        // System.out.println(Gdx.graphics.getFramesPerSecond()); // Uncomment to show fps
    }

    public void drawFPS(){
        batch.begin();
        arial.getData().setScale(height / 3000);
        arial.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        String text = Gdx.graphics.getFramesPerSecond() + "";
        glyphLayout.setText(ProjectSolaris.arial, text);
        float textX = remap(width, 0, width, origin.x, origin.x + width) - glyphLayout.width;
        float textY = remap(0, 0, height, origin.y, origin.y + height);
        arial.draw(ProjectSolaris.batch, ProjectSolaris.glyphLayout, textX, textY);
        batch.end();
    }

    public void levelFailed(){
        player.draw();
        isPaused = true;
        entities.run();
        retryButton.scale();
        retryButton.draw();
        menuButton.scale();
        menuButton.draw();
    }

    public void pauseGame(){
        // Draws all entities but sets isPaused to false to they don't move
        player.draw();
        isPaused = true;
        entities.run();
        resumeButton.scale();
        resumeButton.draw();
        menuButton.scale();
        menuButton.draw();
    }

    public void levelSelector(){
        // Draws the level buttons in level selector
        for (int i = 0; i < levelNumber; i++){
            if (i == levelSelected){ // Change the colour of the selected button
                levelList.get(i).selected = true;
            }
            else{
                levelList.get(i).selected = false;
            }

            if(!isPressed){
                episodeSelector(); // Scroll the screen to a centered position if player is not pressing
            }
            levelList.get(i).draw();
        }
    }

    public void episodeSelector(){
        // WIP
        // Automatically center the level selector screen, locking it to an episode
        episodeSelected = (int)(camera.position.x / width);

        if(!isPressed && Math.abs(xDragVelocity) < 15) {
            camera.position.x -= (camera.position.x - (episodeSelected * width + width / 2)) / 100;
        }
    }

    public static Vector generatePreset(Preset pos){
        // return a preset position somewhere on the screen
        Vector temp = new Vector(0, 0);
        switch(pos){
            case TOP_LEFT:
                temp.x = remap(width / 5, 0,width, origin.x, origin.x + width);
                temp.y = remap(height / 5, 0, height, origin.y, origin.y + height);
                break;

            case TOP:
                temp.x = remap(width / 2, 0, width, origin.x, origin.x + width);
                temp.y = remap(height / 5, 0, height, origin.y, origin.y + height);
                break;

            case TOP_RIGHT:
                temp.x = remap(width - width / 5, 0, width, origin.x, origin.x + width);
                temp.y = remap(height / 5, 0, height, origin.y, origin.y + height);
                break;

            case CENTER_LEFT:
                temp.x = remap(width / 5, 0, width, origin.x, origin.x + width);
                temp.y = remap(height / 2, 0, height, origin.y, origin.y + height);
            break;

            case CENTER:
                temp.x = remap(width / 2, 0, width, origin.x, origin.x + width);
                temp.y = remap(height / 2, 0, height, origin.y, origin.y + height);
                break;

            case CENTER_RIGHT:
                temp.x = remap(width - width / 5, 0, width, origin.x, origin.x + width);
                temp.y = remap(height / 2, 0, height, origin.y, origin.y + height);
                break;

            case BOTTOM_LEFT:
                temp.x = remap(width / 5, 0, width, origin.x, origin.x + width);
                temp.y = remap(height - height / 5, 0, height, origin.y, origin.y + height);
                break;

            case BOTTOM:
                temp.x = remap(width / 2, 0, width, origin.x, origin.x + width);
                temp.y = remap(height - height / 5, 0, height, origin.y, origin.y + height);
                break;

            case BOTTOM_RIGHT:
                temp.x = remap(width - width / 5, 0, width, origin.x, origin.x + width);
                temp.y = remap(height - height / 5, 0, height, origin.y, origin.y + height);;
                break;

            default:
                break;
        }
        return temp;
    }

    public static float getTime(){
        return System.currentTimeMillis();
    }

    @Override
    public boolean touchDown (float x, float y, int pointer, int button) {
        // This method is called when the screen is pressed
        // It's only use currently is to track the location of the last press and start a timer
        // to keep track of how long it's been pressed
        lastTouch.x = x;
        lastTouch.y = y;
        lastTap.x = x;
        lastTap.y = y;
        switch(mode){
            case PLAY:
                if(missileReady) {
                    lastDown = System.currentTimeMillis();
                    isPressed = true;
                }
                break;

            default:
                break;
        }
        return true;
    }

    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        // This method is called when the screen is released
        // Most buttons are checked here, and the mode is changed if they are clicked
        float remapX = remap(x, 0, width, origin.x, origin.x + width);
        float remapY = remap(y, 0, height, origin.y, origin.y + height);
        switch(mode){
            case START_SCREEN:
                if (startButton.isClicked(remapX, remapY)){
                    mode = Mode.MAIN_MENU;
                }
                break;

            case MAIN_MENU:
                if (playButton.isClicked(remapX, remapY)){
                    // Set level and episode to an unused value
                    levelSelected = -1;
                    episodeSelected = -1;
                    mode = Mode.LEVEL_SELECTOR;
                }
                if (testButton.isClicked(remapX, remapY)){
                    // Set level and episode to an unused value
                    Levels.createLevel(0, -1);
                    mode = Mode.TEST;
                }
                break;

            case LEVEL_SELECTOR:
                // Go through all the level selector buttons to determine if any of them were clicked
                Vector pos = new Vector(x, y);
                pos.sub(lastTap);
                if(pos.mag() < width / 20) {
                    if (levelSelected >= 0 && levelButton.isClicked(remapX, remapY)){
                        ProjectSolaris.centerCamera();
                        Levels.createLevel(episodeSelected, levelSelected - 15 * episodeSelected);
                        mode = Mode.PLAY;
                        break;
                    }
                    boolean nullTap = true;
                    for(int i = 0; i < levelNumber; i++){
                        if (levelList.get(i).isClicked(remapX, remapY)) {
                            nullTap = false;
                            levelSelected = i;
                        }
                    }
                    if(nullTap){ // If none are, set level selected to an unused value
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
                    mode = Mode.MAIN_MENU;
                    centerCamera();
                }
                break;

            case LEVEL_FAILED:
                if (retryButton.isClicked(remapX, remapY)){
                    Levels.createLevel(episodeSelected, levelSelected - 15 * episodeSelected);
                    mode = Mode.PLAY;
                }
                if (menuButton.isClicked(remapX, remapY)){
                    mode = Mode.MAIN_MENU;
                    centerCamera();
                }
                break;

            case PLAY:
                if (pauseButton.isClicked(remapX, remapY)){
                    mode = Mode.PAUSE;
                    isPressed = false;
                }
                if (timeScaleButton.isClicked(remapX, remapY)){
                    isPressed = false;
                    Texture tempTexture;
                    Sprite tempSprite;
                    if(timeScaleStage == 0){
                        timeScaleStage++;
                        tempTexture= new Texture(Gdx.files.internal("button/timeScaleButton1.png"));
                        tempSprite = new Sprite(tempTexture);
                        timeScaleButton.sprite = tempSprite;
                    }
                    else if(timeScaleStage == 1){
                        timeScaleStage++;
                        tempTexture= new Texture(Gdx.files.internal("button/timeScaleButton2.png"));
                        tempSprite = new Sprite(tempTexture);
                        timeScaleButton.sprite = tempSprite;
                    }
                    else{
                        timeScaleStage = 0;
                        tempTexture= new Texture(Gdx.files.internal("button/timeScaleButton0.png"));
                        tempSprite = new Sprite(tempTexture);
                        timeScaleButton.sprite = tempSprite;
                    }
                }
                // Create a missile where the release happened
                if(isPressed && missileReady) {
                    missileReady = false;
                    lastDuration = System.currentTimeMillis() - lastDown;
                    // Min str is 5
                    player.shootMissile(remapX,remapY,(lastDuration > 500) ? (lastDuration / 50) : 5);
                    isPressed = false;
                    // The missile strength is based on how long the screen was held
                }
                break;

            case TEST:
                if (seedButton.isClicked(remapX, remapY)){
                    entities.reSeed();
                }

            default:
                break;
        }
        return true;
    }


    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        // Called when a dragging motion is detected
        // Used to scroll
        isPressed = true;
        xDrag = x - lastTouch.x;
        xDragTotal += xDrag;
        lastTouch.x = x;
        dragDuration++;
        xDragVelocity = xDragTotal / dragDuration;
        switch(mode) {
            case LEVEL_SELECTOR:
                if(camera.position.x - xDrag > width / 2 &&
                        camera.position.x - xDrag < width * episodeNumber - width / 2) {
                    camera.translate(-xDrag, 0);
                }
                return true;
            default: // Does nothing in modes where it's not needed
                return false;
        }
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button){
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button){ return false;}

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2)
    {
        return false;
    }

    @Override
    public void pinchStop(){}

    @Override
    public boolean zoom(float initialDistance, float distance) { return false; }

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
    public boolean keyDown(int keycode) { return false;}

    @Override
    public boolean keyUp(int keycode) {

        if (keycode == Input.Keys.BACK) {
            switch(mode){
                case TEST:
                    entities.clear();
                    mode = Mode.MAIN_MENU;
                    break;
                case LEVEL_SELECTOR:
                    mode = Mode.MAIN_MENU;
            }
        }
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
