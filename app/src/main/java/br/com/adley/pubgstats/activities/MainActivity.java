package br.com.adley.pubgstats.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import br.com.adley.pubgstats.R;
import br.com.adley.pubgstats.data.ApiUtils;
import br.com.adley.pubgstats.data.Player;
import br.com.adley.pubgstats.data.remote.PBTService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private String LOG_TAG = MainActivity.class.getSimpleName();
    private PBTService mService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mService = ApiUtils.getPBTService();
        mService.getPlayerStatsByNickname("adley").enqueue(new Callback<Player>() {
            @Override
            public void onResponse(Call<Player> call, Response<Player> response) {
                if(response.isSuccessful()){
                    Log.i(LOG_TAG, response.body().getPlayerName());
                } else {
                    int statusCode = response.code();
                    Log.e(LOG_TAG, String.valueOf(statusCode));
                }
            }

            @Override
            public void onFailure(Call<Player> call, Throwable t) {
                Log.e(LOG_TAG, "Ocorreu um erro");
                Log.e(LOG_TAG, t.getMessage());
                Log.e(LOG_TAG, t.toString());
            }
        });
    }
}
