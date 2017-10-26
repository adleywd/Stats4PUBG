package br.com.adley.stats4pubg.wrapper;

import java.util.List;

import br.com.adley.stats4pubg.data.models.Season;
import br.com.adley.stats4pubg.data.models.Stats;

/**
 * Created by Adley on 15/10/2017.
 * Solo FPP Stats
 */

public class SoloFppStats extends AbstractStats {
    List<Stats> statsList;
    public SoloFppStats(List<Season> seasons, String selectedRegion){

        for (Season season : seasons) {
            // Get all seasons "Aggregated". This type of season contains all seasons.
            // Here the type o game (solo, duo...) doesn't matter. We want all.
            if (season.getMatch().equals("solo-fpp") && season.getRegion().equals(selectedRegion)) {
                statsList = season.getStats();
                setStats(statsList);
            }
        }
    }
}
