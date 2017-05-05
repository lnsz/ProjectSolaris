package com.mygdx.game;

/**
 * Created by lucas on 2016-08-27.
 */
public class Levels {
    // Some default values, should make it easier to understand and build new levels
    // Mass
    static final float MASSIVE_PLANET_MASS = 2000;
    static final float LARGE_PLANET_MASS = 1600;
    static final float NORMAL_PLANET_MASS = 1200;
    static final float SMALL_PLANET_MASS = 800;
    static final float TINY_PLANET_MASS = 400;

    static final float MASSIVE_MOON_MASS = 1000;
    static final float LARGE_MOON_MASS = 800;
    static final float NORMAL_MOON_MASS = 600;
    static final float SMALL_MOON_MASS = 400;
    static final float TINY_MOON_MASS = 200;

    // Radius
    static final float MASSIVE_PLANET_RADIUS = MissileGame.height / 4;
    static final float LARGE_PLANET_RADIUS = MissileGame.height / 6;
    static final float NORMAL_PLANET_RADIUS = MissileGame.height / 8;
    static final float SMALL_PLANET_RADIUS = MissileGame.height / 10;
    static final float TINY_PLANET_RADIUS = MissileGame.height / 12;

    static final float MASSIVE_MOON_RADIUS = MissileGame.height / 10;
    static final float LARGE_MOON_RADIUS = MissileGame.height / 14;
    static final float NORMAL_MOON_RADIUS = MissileGame.height / 16;
    static final float SMALL_MOON_RADIUS = MissileGame.height / 22;
    static final float TINY_MOON_RADIUS = MissileGame.height / 26;

    static final float MASSIVE_MOON_ALTITUDE = 5 * MissileGame.height / 4;
    static final float LARGE_MOON_ALTITUDE = MissileGame.height;
    static final float NORMAL_MOON_ALTITUDE = 3 * MissileGame.height / 4;
    static final float SMALL_MOON_ALTITUDE = 2 * MissileGame.height / 4;
    static final float TINY_MOON_ALTITUDE = MissileGame.height / 4;

    static final float FAST_COMET_SPEED = 70;
    static final float NORMAL_COMET_SPEED = 50;
    static final float SLOW_COMET_SPEED = 30;

    private Levels(){
    }
    public static void createLevel(int episodeSelected, int levelSelected){
        Planet planet;
        Player player = MissileGame.player;
        player.resetAmmo();
        MissileGame.missile = false;
        MissileGame.bg = new Background(episodeSelected, levelSelected / 3);
        if (episodeSelected == 0) {
            switch (levelSelected) {
                case -1: // Test level
                    planet = new Planet(MissileGame.Preset.CENTER, -1, -1);
                    planet.displayInfo = true;
                    MissileGame.entities.addEntity(planet);
                    break;
                case 0:
                    // Introduces shooting mechanic
                    // One normal sized planet, no obstacles
                    player.position = MissileGame.generatePreset(MissileGame.Preset.BOTTOM);
                    planet = new Planet(MissileGame.Preset.TOP, NORMAL_PLANET_RADIUS, NORMAL_PLANET_MASS);
                    MissileGame.entities.addEntity(planet);
                    break;

                case 1:
                    // Introduces comets and asteroids
                    player.position = MissileGame.generatePreset(MissileGame.Preset.TOP_RIGHT);
                    planet = new Planet(MissileGame.Preset.BOTTOM_LEFT, NORMAL_PLANET_RADIUS, NORMAL_PLANET_MASS);
                    MissileGame.entities.addEntity(planet);
                    MissileGame.entities.addEntity(new Asteroid(NORMAL_MOON_RADIUS, true,
                            Math.PI, planet, SMALL_MOON_ALTITUDE));
                    MissileGame.entities.addEntity(new Asteroid(NORMAL_MOON_RADIUS, true,
                            Math.PI, planet, LARGE_MOON_ALTITUDE));
                    MissileGame.entities.addEntity(new Comet(2 * Math.PI / 3, -Math.PI / 3,
                            FAST_COMET_SPEED, NORMAL_MOON_RADIUS));
                    break;

                case 2:
                    // Introduces moons
                    // One normal sized planet and one moon
                    player.position = MissileGame.generatePreset(MissileGame.Preset.BOTTOM_LEFT);
                    planet = new Planet(MissileGame.Preset.TOP_RIGHT, SMALL_PLANET_RADIUS, SMALL_PLANET_MASS);
                    MissileGame.entities.addEntity(planet);
                    MissileGame.entities.addEntity(new Moon(LARGE_MOON_RADIUS, LARGE_MOON_MASS, true,
                            Math.PI, planet, SMALL_MOON_ALTITUDE));
                    MissileGame.entities.addEntity(new Moon(LARGE_MOON_RADIUS, LARGE_MOON_MASS, true,
                            0, planet, SMALL_MOON_ALTITUDE));
                    break;

                case 3:
                    // Introduces Elliptical orbits
                    // Two moons in elliptical orbit
                    player.position = MissileGame.generatePreset(MissileGame.Preset.TOP_RIGHT);
                    planet = new Planet(MissileGame.Preset.BOTTOM_LEFT, LARGE_PLANET_RADIUS, LARGE_PLANET_MASS);
                    MissileGame.entities.addEntity(planet);
                    MissileGame.entities.addEntity(new Moon(SMALL_MOON_RADIUS, LARGE_MOON_MASS, true,
                            4 * Math.PI / 3, planet, TINY_MOON_ALTITUDE, MASSIVE_MOON_ALTITUDE));
                    MissileGame.entities.addEntity(new Moon(SMALL_MOON_RADIUS, LARGE_MOON_MASS, true,
                            7 * Math.PI / 6, planet, TINY_MOON_ALTITUDE, MASSIVE_MOON_ALTITUDE));
                    break;

                case 4:
                    // Introduces multiple planets
                    // One small planet and one large "target" planet
                    player.position = MissileGame.generatePreset(MissileGame.Preset.BOTTOM_RIGHT);
                    planet = new Planet(MissileGame.Preset.TOP_LEFT, MASSIVE_PLANET_RADIUS, MASSIVE_PLANET_MASS);
                    MissileGame.entities.addEntity(planet);
                    MissileGame.entities.addEntity(new Planet(MissileGame.Preset.CENTER, NORMAL_PLANET_RADIUS, LARGE_PLANET_MASS));
                    break;

                case 5:
                    // Moons and asteroids
                    // One tiny planet, a moon and two asteroid
                    player.position = MissileGame.generatePreset(MissileGame.Preset.TOP);
                    planet = new Planet(MissileGame.Preset.BOTTOM, TINY_PLANET_RADIUS, TINY_PLANET_MASS);
                    MissileGame.entities.addEntity(planet);
                    MissileGame.entities.addEntity(new Moon(MASSIVE_MOON_RADIUS, MASSIVE_MOON_MASS, true,
                            0, planet, NORMAL_MOON_ALTITUDE));
                    MissileGame.entities.addEntity(new Asteroid(LARGE_MOON_RADIUS, true,
                            0, planet, SMALL_MOON_ALTITUDE));
                    MissileGame.entities.addEntity(new Asteroid(LARGE_MOON_RADIUS, true,
                            Math.PI, planet, NORMAL_MOON_ALTITUDE));
                    break;

                case 6:
                    break;

                case 7:
                    break;

                case 8:
                    break;

                case 9:
                    break;

                case 10:
                    break;

                case 11:
                    break;

                case 12:
                    break;

                case 13:
                    break;

                case 14:
                    break;

                default:
                    break;
            }
        } else if (episodeSelected == 1){
            switch (levelSelected) {
                case 0:
                    break;

                case 1:
                    break;

                case 2:
                    break;

                case 3:
                    break;

                case 4:
                    break;

                case 5:
                    break;

                case 6:
                    break;

                case 7:
                    break;

                case 8:
                    break;

                case 9:
                    break;

                case 10:
                    break;

                case 11:
                    break;

                case 12:
                    break;

                case 13:
                    break;

                case 14:
                    break;

                default:
                    break;
            }

        } else if (episodeSelected == 2){
            switch (levelSelected) {
                case 0:
                    break;

                case 1:
                    break;

                case 2:
                    break;

                case 3:
                    break;

                case 4:
                    break;

                case 5:
                    break;

                case 6:
                    break;

                case 7:
                    break;

                case 8:
                    break;

                case 9:
                    break;

                case 10:
                    break;

                case 11:
                    break;

                case 12:
                    break;

                case 13:
                    break;

                case 14:
                    break;

                default:
                    break;
            }

        }
    }
}
