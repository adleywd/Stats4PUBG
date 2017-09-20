package br.com.adley.pubgstats.data;

import br.com.adley.pubgstats.data.remote.PBTService;
import br.com.adley.pubgstats.data.remote.RetrofitClient;

/**
 * Created by Adley.Damaceno on 20/09/2017.
 * Defines the BASE Url.
 */

public class ApiUtils {
    public static final String BASE_URL = "https://pubgtracker.com/api/";

    public static PBTService getPBTService() {
        return RetrofitClient.getClient(BASE_URL).create(PBTService.class);
    }
}
