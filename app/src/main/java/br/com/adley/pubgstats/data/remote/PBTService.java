package br.com.adley.pubgstats.data.remote;

import br.com.adley.pubgstats.data.Player;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Adley.Damaceno on 20/09/2017.
 * API Interface
 * Define methods available
 */

public interface PBTService {
    String API_KEY = "TRN-Api-Key:ea4f1d45-473b-4925-90cb-d0e276339822";
    @Headers(API_KEY)
    @GET("/profile/pc/{nickname}")
    Call<Player> getPlayerStatsByNickname(@Path("nickname") String nickname);

    @Headers(API_KEY)
    @GET("/search?")
    Call<Player> getPlayerStatsBySteamId(@Query("steamId") String steamid);
}
