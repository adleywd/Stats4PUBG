package br.com.adley.ipubg.wrapper;

import java.util.List;

import br.com.adley.ipubg.data.models.Season;
import br.com.adley.ipubg.data.models.Stats;

/**
 * Created by Adley Damaceno on 15/10/2017.
 * Squad FPP Stats
 */

public class SquadFppStats extends AbstractStats {
    List<Stats> statsList;
    public SquadFppStats(List<Season> seasons, String selectedRegion){

        for (Season season : seasons) {
            // Get all seasons "Aggregated". This type of season contains all seasons.
            // Here the type o game (solo, duo...) doesn't matter. We want all.
            if (season.getMatch().equals("squad-fpp") && season.getRegion().equals(selectedRegion)) {
                statsList = season.getStats();
                setStats(statsList);
            }
        }
    }
}
