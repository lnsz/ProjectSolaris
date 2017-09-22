package com.mygdx.game;

/**
 * Created by lucas on 2016-08-27.
 */
public class Levels {
    // Some default values, should make it easier to understand and build new levels
    // Mass
    static final float MASSIVE_PLANET_MASS = 500;
    static final float LARGE_PLANET_MASS = 400;
    static final float NORMAL_PLANET_MASS = 300;
    static final float SMALL_PLANET_MASS = 200;
    static final float TINY_PLANET_MASS = 100;

    static final float MASSIVE_MOON_MASS = 250;
    static final float LARGE_MOON_MASS = 200;
    static final float NORMAL_MOON_MASS = 150;
    static final float SMALL_MOON_MASS = 100;
    static final float TINY_MOON_MASS = 50;

    // Radius
    static final float MASSIVE_PLANET_RADIUS = ProjectSolaris.height / 12;
    static final float LARGE_PLANET_RADIUS = ProjectSolaris.height / 16;
    static final float NORMAL_PLANET_RADIUS = ProjectSolaris.height / 20;
    static final float SMALL_PLANET_RADIUS = ProjectSolaris.height / 24;
    static final float TINY_PLANET_RADIUS = ProjectSolaris.height / 28;

    static final float MASSIVE_MOON_RADIUS = ProjectSolaris.height / 30;
    static final float LARGE_MOON_RADIUS = ProjectSolaris.height / 40;
    static final float NORMAL_MOON_RADIUS = ProjectSolaris.height / 50;
    static final float SMALL_MOON_RADIUS = ProjectSolaris.height / 60;
    static final float TINY_MOON_RADIUS = ProjectSolaris.height / 70;

    static final float MASSIVE_MOON_ALTITUDE = 5 * ProjectSolaris.height / 8;
    static final float LARGE_MOON_ALTITUDE = 4 * ProjectSolaris.height / 8;
    static final float NORMAL_MOON_ALTITUDE = 3 * ProjectSolaris.height / 8;
    static final float SMALL_MOON_ALTITUDE = 2 * ProjectSolaris.height / 8;
    static final float TINY_MOON_ALTITUDE = ProjectSolaris.height / 8;

    static final float FAST_COMET_SPEED = 50;
    static final float NORMAL_COMET_SPEED = 30;
    static final float SLOW_COMET_SPEED = 10;

    private Levels(){
    }
    public static void createLevel(int episodeSelected, int levelSelected){
        Planet planet;
        Player player = ProjectSolaris.player;
        player.resetAmmo();
        ProjectSolaris.missile = false;
        ProjectSolaris.bg = new Background(episodeSelected, levelSelected / 3);
        if (episodeSelected == 0) {
            switch (levelSelected) {
                case -1: // Test level
                    planet = new Planet(ProjectSolaris.Preset.CENTER, -1, -1);
                    planet.displayInfo = true;
                    ProjectSolaris.entities.addEntity(planet);
                    break;
                case 0:
                    // Introduces shooting mechanic
                    // One normal sized planet, no obstacles
                    player.position = ProjectSolaris.generatePreset(ProjectSolaris.Preset.BOTTOM);
                    planet = new Planet(ProjectSolaris.Preset.TOP, NORMAL_PLANET_RADIUS, NORMAL_PLANET_MASS);
                    ProjectSolaris.entities.addEntity(planet);
                    break;

                case 1:
                    // Introduces comets and asteroids
                    player.position = ProjectSolaris.generatePreset(ProjectSolaris.Preset.TOP_RIGHT);
                    planet = new Planet(ProjectSolaris.Preset.BOTTOM_LEFT, MASSIVE_PLANET_RADIUS, NORMAL_PLANET_MASS);
                    ProjectSolaris.entities.addEntity(planet);
                    ProjectSolaris.entities.addEntity(new Asteroid(NORMAL_MOON_RADIUS, true,
                            Math.PI, planet, SMALL_MOON_ALTITUDE));
                    ProjectSolaris.entities.addEntity(new Asteroid(NORMAL_MOON_RADIUS, true,
                            Math.PI, planet, LARGE_MOON_ALTITUDE));
                    ProjectSolaris.entities.addEntity(new Comet(2 * Math.PI / 3, -Math.PI / 3,
                            FAST_COMET_SPEED, NORMAL_MOON_RADIUS));
                    break;

                case 2:
                    // Introduces moons
                    // One normal sized planet and one moon
                    player.position = ProjectSolaris.generatePreset(ProjectSolaris.Preset.BOTTOM_LEFT);
                    planet = new Planet(ProjectSolaris.Preset.TOP_RIGHT, SMALL_PLANET_RADIUS, SMALL_PLANET_MASS);
                    ProjectSolaris.entities.addEntity(planet);
                    ProjectSolaris.entities.addEntity(new Moon(LARGE_MOON_RADIUS, LARGE_MOON_MASS, true,
                            Math.PI, planet, SMALL_MOON_ALTITUDE));
                    ProjectSolaris.entities.addEntity(new Moon(LARGE_MOON_RADIUS, LARGE_MOON_MASS, true,
                            0, planet, SMALL_MOON_ALTITUDE));
                    break;

                case 3:
                    // Introduces Elliptical orbits
                    // Two moons in elliptical orbit
                    player.position = ProjectSolaris.generatePreset(ProjectSolaris.Preset.TOP_RIGHT);
                    planet = new Planet(ProjectSolaris.Preset.BOTTOM_LEFT, LARGE_PLANET_RADIUS, LARGE_PLANET_MASS);
                    ProjectSolaris.entities.addEntity(planet);
                    ProjectSolaris.entities.addEntity(new Moon(SMALL_MOON_RADIUS, LARGE_MOON_MASS, true,
                            4 * Math.PI / 3, planet, TINY_MOON_ALTITUDE, MASSIVE_MOON_ALTITUDE));
                    ProjectSolaris.entities.addEntity(new Moon(SMALL_MOON_RADIUS, LARGE_MOON_MASS, true,
                            7 * Math.PI / 6, planet, TINY_MOON_ALTITUDE, MASSIVE_MOON_ALTITUDE));
                    break;

                case 4:
                    // Introduces multiple planets
                    // One small planet and one large "target" planet
                    player.position = ProjectSolaris.generatePreset(ProjectSolaris.Preset.BOTTOM_RIGHT);
                    planet = new Planet(ProjectSolaris.Preset.TOP_LEFT, MASSIVE_PLANET_RADIUS, MASSIVE_PLANET_MASS);
                    ProjectSolaris.entities.addEntity(planet);
                    ProjectSolaris.entities.addEntity(new Planet(ProjectSolaris.Preset.CENTER, NORMAL_PLANET_RADIUS, LARGE_PLANET_MASS));
                    break;

                case 5:
                    // Moons and asteroids
                    // One tiny planet, a moon and two asteroid
                    player.position = ProjectSolaris.generatePreset(ProjectSolaris.Preset.TOP);
                    planet = new Planet(ProjectSolaris.Preset.BOTTOM, TINY_PLANET_RADIUS, TINY_PLANET_MASS);
                    ProjectSolaris.entities.addEntity(planet);
                    ProjectSolaris.entities.addEntity(new Moon(MASSIVE_MOON_RADIUS, MASSIVE_MOON_MASS, true,
                            0, planet, NORMAL_MOON_ALTITUDE));
                    ProjectSolaris.entities.addEntity(new Asteroid(LARGE_MOON_RADIUS, true,
                            0, planet, SMALL_MOON_ALTITUDE));
                    ProjectSolaris.entities.addEntity(new Asteroid(LARGE_MOON_RADIUS, true,
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
