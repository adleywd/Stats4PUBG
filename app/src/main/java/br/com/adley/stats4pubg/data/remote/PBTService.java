package br.com.adley.stats4pubg.data.remote;

import br.com.adley.stats4pubg.data.models.Player;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by Adley.Damaceno on 20/09/2017.
 * API Interface
 * Define methods available
 */

public interface PBTService {
    String API_KEY = "TRN-Api-Key:ea4f1d45-473b-4925-90cb-d0e276339822";
    @Headers(API_KEY)
    @GET("profile/pc/{nickname}")
    Call<Player> getPlayerStatsByNickname(@Path("nickname") String nickname);

}
