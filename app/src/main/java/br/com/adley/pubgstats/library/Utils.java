package br.com.adley.pubgstats.library;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import br.com.adley.pubgstats.data.Player;

/**
 * Created by Adley.Damaceno on 28/09/2017.
 * This class contain utilities for these project.
 */

public class Utils {

    private Context mContext;

    public Utils(Context context){
        mContext = context;
    }

    public static float getKDAverage(int kills, int matchesPlayed, int wins){
        return (float)kills / (float)(matchesPlayed - wins);
    }

    private static Player getFakePlayerObject(Context context) {
        Gson gson = new Gson();
        String json;
        try {
            InputStream is = context.getAssets().open("adley_pubg.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
            return gson.fromJson(json, Player.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void closeKeyboard(@NonNull Activity activity) {
        // Check if no view has focus:
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        }
    }

}
