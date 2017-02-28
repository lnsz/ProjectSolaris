package com.mygdx.game;

/**
 * Created by lucas on 2/26/2017.
 */

public class ColourScheme {
    public String c1; // Atmosphere and background
    public String c2; // Darker patches
    public String c3; // Clouds

    public static final String[] RockC1 = {
            "D4EDF4", "DDEEFF", "CDE7F4", "DBDBDB", "C3E9F6", "D2E7F2", "E8E8E8", "CAE6F7",     // Rock Planet, C1, Very Cold (0-8)
            "EFF6FC", "EFF6FC", "F7F7F7", "E4E4E4", "E9E9E9", "EFEFEF", "E0E0E0", "D6D6D6",     // Rock Planet, C1, Cold (9-15)
            "5DA9D8", "508FD3", "82C4D8", "6FB3D6", "3BA0D3", "5492DD", "74A8D3", "63C0FF",     // Rock Planet, C1, Normal (16-23)
            "5DA9D8", "508FD3", "82C4D8", "6FB3D6", "3BA0D3", "5492DD", "74A8D3", "63C0FF",     // Rock Planet, C1, Hot (24-31)
            "5DA9D8", "508FD3", "82C4D8", "6FB3D6", "3BA0D3", "5492DD", "74A8D3", "63C0FF",     // Rock Planet, C1, Very Hot (32-39)
    };

    public static final String[] RockC2 = {
            "F2FAFD", "EBF3F6", "F3FBFE", "EFF6FC", "FFFFFF", "F7F7F7", "E8F6F9", "F3FDFF",     // Rock Planet, C2, Very Cold (0-8)
            "AFAFAF", "919191", "C4C4C4", "A5A5A5", "EFEFEF", "B7B7B7", "AAAAAA", "999999",     // Rock Planet, C2, Cold (9-15)
            "95C183", "94CC9A", "3FCC79", "A3D87C", "85CC66", "B5AE9F", "C1C17C", "74B56E",     // Rock Planet, C2, Normal (16-23)
            "95C183", "94CC9A", "3FCC79", "A3D87C", "85CC66", "B5AE9F", "C1C17C", "74B56E",     // Rock Planet, C2, Hot (24-31)
            "95C183", "94CC9A", "3FCC79", "A3D87C", "85CC66", "B5AE9F", "C1C17C", "74B56E"      // Rock Planet, C2, Very Hot (32-39)

    };

    public static final String[] GasC1 = {
            "255366", "597CB7", "5C6668", "558789", "557389", "446289", "4677A8", "446B77",     // Gas Planet, C1, Very Cold (0-8)
            "5B90E0", "3CBBE4", "93B1C1", "54B9DD", "72A8EA", "459FCC", "5FA8D8", "869CD6",     // Gas Planet, C1, Cold (9-15)
            "5B90E0", "3CBBE4", "93B1C1", "54B9DD", "72A8EA", "459FCC", "5FA8D8", "869CD6",     // Gas Planet, C1, Normal (16-23)
            "5B90E0", "3CBBE4", "93B1C1", "54B9DD", "72A8EA", "459FCC", "5FA8D8", "869CD6",     // Gas Planet, C1, Hot (24-31)
            "5B90E0", "3CBBE4", "93B1C1", "54B9DD", "72A8EA", "459FCC", "5FA8D8", "869CD6",     // Gas Planet, C1, Very Hot (32-39)
    };

    public static final String[] GasC2 = {
            "9CA6B7", "92B7B7", "A3B794", "87AEB7", "9BAAC4", "8ECBDB", "A4AAAD", "88A1AA",     // Gas Planet, C2, Very Cold (0-8)
            "8DC0F5", "60D5F8", "CACEF7", "B5D7DD", "96BCEA", "9EC3D6", "B4C5F7", "BCCED8",     // Gas Planet, C2, Cold (9-15)
            "8DC0F5", "60D5F8", "CACEF7", "B5D7DD", "96BCEA", "9EC3D6", "B4C5F7", "BCCED8",     // Gas Planet, C2, Normal (16-23)
            "8DC0F5", "60D5F8", "CACEF7", "B5D7DD", "96BCEA", "9EC3D6", "B4C5F7", "BCCED8",     // Gas Planet, C2, Hot (24-31)
            "8DC0F5", "60D5F8", "CACEF7", "B5D7DD", "96BCEA", "9EC3D6", "B4C5F7", "BCCED8",     // Gas Planet, C2, Very Hot (32-39)
    };


    public ColourScheme(int i1, int i2, int planetType){
        if (planetType == 0) { // Rock planets
            c1 = RockC1[i1];
            c2 = RockC2[i2];
            c3 = "FFFFFF";
        }

        else{ // Gas planets
            c1 = GasC1[i1];
            c2 = GasC2[i2];
            c3 = "FFFFFF";
        }
    }

}
