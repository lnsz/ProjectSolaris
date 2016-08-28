package com.mygdx.game;

/**
 * Created by lucas on 2016-08-27.
 */
public class Levels {
    public Levels(){
    }
    public static void createLevel(int level){
        Planet planet;
        Player player = MissileGame.player;

        // Some default values, should make it easier to understand and build new levels
        // Mass
        final float MASSIVE_PLANET_MASS = 2000;
        final float LARGE_PLANET_MASS = 1600;
        final float NORMAL_PLANET_MASS = 1200;
        final float SMALL_PLANET_MASS = 800;
        final float TINY_PLANET_MASS = 400;

        final float MASSIVE_MOON_MASS = 1000;
        final float LARGE_MOON_MASS = 800;
        final float NORMAL_MOON_MASS = 600;
        final float SMALL_MOON_MASS = 400;
        final float TINY_MOON_MASS = 200;

        // Radius
        final float MASSIVE_PLANET_RADIUS = MissileGame.height / 4;
        final float LARGE_PLANET_RADIUS = MissileGame.height / 6;
        final float NORMAL_PLANET_RADIUS = MissileGame.height / 8;
        final float SMALL_PLANET_RADIUS = MissileGame.height / 10;
        final float TINY_PLANET_RADIUS = MissileGame.height / 12;

        final float MASSIVE_MOON_RADIUS = MissileGame.height / 10;
        final float LARGE_MOON_RADIUS = MissileGame.height / 14;
        final float NORMAL_MOON_RADIUS = MissileGame.height / 16;
        final float SMALL_MOON_RADIUS = MissileGame.height / 22;
        final float TINY_MOON_RADIUS = MissileGame.height / 26;

        final float MASSIVE_MOON_ALTITUDE = 5 * MissileGame.height / 3;
        final float LARGE_MOON_ALTITUDE = 4 * MissileGame.height / 3;
        final float NORMAL_MOON_ALTITUDE = MissileGame.height;
        final float SMALL_MOON_ALTITUDE = 2 * MissileGame.height / 3;
        final float TINY_MOON_ALTITUDE = MissileGame.height / 3;

        switch(level){
            case 0:
                // Introduces shooting mechanic
                // One normal sized planet, no obstacles
                player.position = MissileGame.generatePreset(MissileGame.Preset.BOTTOM);
                planet = new Planet(MissileGame.Preset.TOP, NORMAL_PLANET_RADIUS, NORMAL_PLANET_MASS);
                MissileGame.entities.addEntity(planet);
                break;

            case 1:
                // Introduces moons
                // One normal sized planet and one moon
                player.position = MissileGame.generatePreset(MissileGame.Preset.BOTTOM);
                planet = new Planet(MissileGame.Preset.TOP, NORMAL_PLANET_RADIUS, NORMAL_PLANET_MASS);
                MissileGame.entities.addEntity(planet);
                MissileGame.entities.addEntity(new Moon(NORMAL_MOON_RADIUS, NORMAL_MOON_MASS, true,
                        Math.PI, planet, SMALL_MOON_ALTITUDE));
                break;

            case 2:

                player.position = MissileGame.generatePreset(MissileGame.Preset.TOP_RIGHT);
                planet = new Planet(MissileGame.Preset.BOTTOM_LEFT, MASSIVE_PLANET_RADIUS, TINY_PLANET_MASS);
                MissileGame.entities.addEntity(planet);
                MissileGame.entities.addEntity(new Moon(MASSIVE_MOON_RADIUS, TINY_MOON_MASS, true,
                        Math.PI, planet, SMALL_MOON_ALTITUDE));
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
                player.position = MissileGame.generatePreset(MissileGame.Preset.BOTTOM);
                planet = new Planet(MissileGame.width / 2,
                        MissileGame.remap(MissileGame.height / 5, 0, MissileGame.height,
                                MissileGame.defaultOriginY, MissileGame.
                                        defaultOriginY + MissileGame.defaultHeight),
                        MissileGame.height / 8, 600);
                MissileGame.entities.addEntity(planet);
                MissileGame.entities.addEntity(new Moon(MissileGame.height / 20, 400, true, Math.PI, planet, 750));
                MissileGame.entities.addEntity(new Moon(MissileGame.height / 20, 400, false, 3 * Math.PI / 2, planet, 1250));
//        entities.addEntity(new Moon(width / 2, height / 5, height / 20, 400, true, 0, planet, 1000), true);
                MissileGame.entities.addEntity(new Moon(MissileGame.height / 20, 400, false, Math.PI / 2, planet, 1250));
//        entities.addEntity(new Moon(width / 2, height / 5, height / 20, 400, true, Math.PI / 6, planet, 500, 2000), true);
                MissileGame.entities.addEntity(new Moon(MissileGame.height / 20, 400, true, 0, planet, 750));

                MissileGame.entities.addEntity(new Moon(MissileGame.height / 20, 400, false, Math.PI, planet, 1250));
                MissileGame.entities.addEntity(new Moon(MissileGame.height / 20, 400, true, 3 * Math.PI / 2, planet, 750));
//        entities.addEntity(new Moon(width / 2, height / 5, height / 20, 400, true, 0, planet, 1000), true);
                MissileGame.entities.addEntity(new Moon(MissileGame.height / 20, 400, true, Math.PI / 2, planet, 750));
//        entities.addEntity(new Moon(width / 2, height / 5, height / 20, 400, true, Math.PI / 6, planet, 500, 2000), true);
                MissileGame.entities.addEntity(new Moon(MissileGame.height / 20, 400, false, 0, planet, 1250));
//        entities.addEntity(new Comet(Math.PI / 2 * 3, Math.PI / 2, 20, height / 20), true);

                MissileGame.entities.addEntity(new Moon(MissileGame.height / 20, 400, false, Math.PI / 4, planet, 1000, 3000));
                MissileGame.entities.addEntity(new Moon(MissileGame.height / 20, 400, true, 3 * Math.PI / 4, planet, 1000, 3000));
//        entities.addEntity(new Moon(width / 2, height / 5, height / 20, 400, true, 0, planet, 1000), true);
                MissileGame.entities.addEntity(new Moon(MissileGame.height / 20, 400, true, 5 * Math.PI / 4, planet,  1000, 3000));
//        entities.addEntity(new Moon(width / 2, height / 5, height / 20, 400, true, Math.PI / 6, planet, 500, 2000), true);
                MissileGame.entities.addEntity(new Moon(MissileGame.height / 20, 400, false, 7 * Math.PI / 4, planet,  1000, 3000));
                break;
        }
    }
}
