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
                // Rock Planet, C1, Very Cold
                case 0:
                    c1 = "D4EDF4";
                    break;
                case 1:
                    c1 = "DDEEFF";
                    break;
                case 2:
                    c1 = "CDE7F4";
                    break;
                case 3:
                    c1 = "DBDBDB";
                    break;
                case 4:
                    c1 = "C3E9F6";
                    break;
                case 5:
                    c1 = "D2E7F2";
                    break;
                case 6:
                    c1 = "E8E8E8";
                    break;
                case 7:
                    c1 = "CAE6F7";
                    break;

                // Rock Planet, C1, Cold
                case 8:
                    c1 = "EFF6FC";
                    break;
                case 9:
                    c1 = "EFF6FC";
                    break;
                case 10:
                    c1 = "F7F7F7";
                    break;
                case 11:
                    c1 = "E4E4E4";
                    break;
                case 12:
                    c1 = "E9E9E9";
                    break;
                case 13:
                    c1 = "EFEFEF";
                    break;
                case 14:
                    c1 = "E0E0E0";
                    break;
                case 15:
                    c1 = "D6D6D6";
                    break;

                default:
                    c1 = "FFFFFF";
                    break;
            }
            // Very cold: 0-7, Cold: 8-, Normal: -, Hot: -, Very Hot: -
            switch (i2) {
                // Rock Planet, C2, Very Cold
                case 0:
                    c2 = "F2FAFD";
                    break;
                case 1:
                    c2 = "EBF3F6";
                    break;
                case 2:
                    c2 = "F3FBFE";
                    break;
                case 3:
                    c2 = "EFF6FC";
                    break;
                case 4:
                    c2 = "FFFFFF";
                    break;
                case 5:
                    c2 = "F7F7F7";
                    break;
                case 6:
                    c2 = "E8F6F9";
                    break;
                case 7:
                    c2 = "F3FDFF";
                    break;

                // Rock Planet, C2, Cold
                case 8:
                    c2 = "AFAFAF";
                    break;
                case 9:
                    c2 = "919191";
                    break;
                case 10:
                    c2 = "C4C4C4";
                    break;
                case 11:
                    c2 = "A5A5A5";
                    break;
                case 12:
                    c2 = "D6D6D6";
                    break;
                case 13:
                    c2 = "B7B7B7";
                    break;
                case 14:
                    c2 = "AAAAAA";
                    break;
                case 15:
                    c2 = "999999";
                    break;

                default:
                    c2 = "FFFFFF";
                    break;
            }
            c3 = "FFFFFF";
        }

        else{ // Gas planets
            switch (i1) {
                // Gas Planet, C1, Very Cold
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
                    c1 = "446B77";
                    break;

                // Gas Planet, C1, Cold
                case 8:
                    c1 = "5B90E0";
                    break;
                case 9:
                    c1 = "3CBBE4";
                    break;
                case 10:
                    c1 = "93B1C1";
                    break;
                case 11:
                    c1 = "54B9DD";
                    break;
                case 12:
                    c1 = "72A8EA";
                    break;
                case 13:
                    c1 = "459FCC";
                    break;
                case 14:
                    c1 = "5FA8D8";
                    break;
                case 15:
                    c1 = "869CD6";
                    break;


                default:
                    c1 = "FFFFFF";
                    break;
            }

            switch (i2) {
                // Gas Planet, C2, Very Cold
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
                    c2 = "9BAAC4";
                    break;
                case 5:
                    c2 = "8ECBDB";
                    break;
                case 6:
                    c2 = "A4AAAD";
                    break;
                case 7:
                    c2 = "88A1AA";
                    break;

                // Gas Planet, C2, Cold
                case 8:
                    c2 = "8DC0F5";
                    break;
                case 9:
                    c2 = "60D5F8";
                    break;
                case 10:
                    c2 = "CACEF7";
                    break;
                case 11:
                    c2 = "B5D7DD";
                    break;
                case 12:
                    c2 = "96BCEA";
                    break;
                case 13:
                    c2 = "9EC3D6";
                    break;
                case 14:
                    c2 = "B4C5F7";
                    break;
                case 15:
                    c2 = "BCCED8";
                    break;


                default:
                    c2 = "9A9AAD";
                    break;
            }
            c3 = "FFFFFF";
        }
    }

}
