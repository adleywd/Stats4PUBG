package br.com.adley.pubgstats.library;

/**
 * Created by Adley.Damaceno on 28/09/2017.
 * This class contain utilities for these project.
 */

public class Utils {

    public static float getKDAverage(int kills, int matchesPlayed, int wins){
        return (float)kills / (float)(matchesPlayed - wins);
    }

}
