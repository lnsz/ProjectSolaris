package com.mygdx.game;

/**
 * Created by lucas on 2016-08-27.
 */
public class Levels {
    public Levels(){
    }
    public static void createLevel(int level){
        Planet planet;
        switch(level){
            case 0:
                planet = new Planet(MissileGame.width / 2,
                        MissileGame.remap(MissileGame.height / 5, 0, MissileGame.height,
                                MissileGame.defaultOriginY, MissileGame.
                                        defaultOriginY + MissileGame.defaultHeight),
                        MissileGame.height / 8, 600);
                MissileGame.entities.addEntity(planet);
                MissileGame.entities.addEntity(new Moon(MissileGame.height / 20, 400, true, Math.PI, planet, 750));
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
