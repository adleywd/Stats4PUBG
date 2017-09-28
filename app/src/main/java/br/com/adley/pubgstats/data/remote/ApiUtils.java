package br.com.adley.pubgstats.data.remote;

/**
 * Created by Adley.Damaceno on 20/09/2017.
 * Defines the BASE Url.
 */

public class ApiUtils {

    public static PBTService getPBTService(String BASE_URL) {
        return RetrofitClient.getClient(BASE_URL).create(PBTService.class);
    }
}
