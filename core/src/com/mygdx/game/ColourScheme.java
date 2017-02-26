package com.mygdx.game;

/**
 * Created by lucas on 2/26/2017.
 */

public class ColourScheme {
    public String c1; // Atmosphere and background
    public String c2; // Darker patches
    public String c3; // Clouds

    public ColourScheme(int i1, int i2, int planetType){
        if (planetType == 0) { // Rock planets
            // Very cold: 0-7, Cold: 8-, Normal: -, Hot: -, Very Hot: -
            switch (i1) {
                case 0:
                    c1 = "E1E0FF";
                    break;
                case 1:
                    c1 = "DDEEFF";
                    break;
                case 2:
                    c1 = "D1DBFF";
                    break;
                case 3:
                    c1 = "F3E0FF";
                    break;
                case 4:
                    c1 = "C6DDF0";
                    break;
                case 5:
                    c1 = "C4EFE3";
                    break;
                case 6:
                    c1 = "C2E8FC";
                    break;
                case 7:
                    c1 = "9FBCFC";
                    break;



                default:
                    c1 = "FFFFFF";
                    break;
            }
            // Very cold: 0-7, Cold: 8-, Normal: -, Hot: -, Very Hot: -
            switch (i2) {
                case 0:
                    c2 = "FEFBFD";
                    break;
                case 1:
                    c2 = "FDFEFC";
                    break;
                case 2:
                    c2 = "FCF2F6";
                    break;
                case 3:
                    c2 = "F9F0E3";
                    break;
                case 4:
                    c2 = "FFFFFF";
                    break;
                case 5:
                    c2 = "EAEFF7";
                    break;
                case 6:
                    c2 = "EBF4DE";
                    break;
                case 7:
                    c2 = "DCEDF2";
                    break;


                case 8:
                    c2 = "DBF7FF";
                    break;
                case 9:
                    c2 = "EAFAFD";
                    break;
                default:
                    c2 = "FFFFFF";
                    break;
            }
            c3 = "FFFFFF";
        }

        else{ // Gas planets
            switch (i1) {
                case 0:
                    c1 = "255366";
                    break;
                case 1:
                    c1 = "597CB7";
                    break;
                case 2:
                    c1 = "5C6668";
                    break;
                case 3:
                    c1 = "558789";
                    break;
                case 4:
                    c1 = "557389";
                    break;
                case 5:
                    c1 = "446289";
                    break;
                case 6:
                    c1 = "4677A8";
                    break;
                case 7:
                    c1 = "5A896F";
                    break;

                default:
                    c1 = "FFFFFF";
                    break;
            }
            switch (i2) {
                case 0:
                    c2 = "9CA6B7";
                    break;
                case 1:
                    c2 = "92B7B7";
                    break;
                case 2:
                    c2 = "A3B794";
                    break;
                case 3:
                    c2 = "87AEB7";
                    break;
                case 4:
                    c2 = "80B783";
                    break;
                case 5:
                    c2 = "8ECBDB";
                    break;
                case 6:
                    c2 = "A4AAAD";
                    break;
                case 7:
                    c2 = "A4AAAD";
                    break;

                default:
                    c2 = "9A9AAD";
                    break;
            }
            c3 = "FFFFFF";
        }
    }

}
