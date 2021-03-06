package br.com.adley.stats4pubg.wrapper;

import java.util.List;

import br.com.adley.stats4pubg.data.models.Season;
import br.com.adley.stats4pubg.data.models.Stats;

/**
 * Created by Gabriel Lundgren on 08/10/2017.
 */

public class SoloStats extends AbstractStats {
    List<Stats> statsList;
    public SoloStats(List<Season> seasons, String selectedRegion){

        for (Season season : seasons) {
            // Get all seasons "Aggregated". This type of season contains all seasons.
            // Here the type o game (solo, duo...) doesn't matter. We want all.
            if (season.getMatch().equals("solo") && season.getRegion().equals(selectedRegion)) {
                statsList = season.getStats();
                setStats(statsList);
            }
        }
    }
}
