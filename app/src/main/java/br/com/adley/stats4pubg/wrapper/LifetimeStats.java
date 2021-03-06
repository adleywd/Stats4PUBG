package br.com.adley.stats4pubg.wrapper;

import java.util.List;

import br.com.adley.stats4pubg.data.models.Season;
import br.com.adley.stats4pubg.data.models.Stats;

/**
 * Created by Gabriel Lundgren on 08/10/2017.
 * Contain the lifetime stats about the player.
 */

public class LifetimeStats extends AbstractStats {

    List<Stats> statsList;

    public LifetimeStats(List<Season> seasons){

        for (Season season : seasons) {
            // Get all seasons "Aggregated". This type of season contains all seasons.
            // Here the type o game (solo, duo...) doesn't matter. We want all.
            if (season.getRegion().equals("agg")) {
                statsList = season.getStats();
                setStats(statsList);
            }
        }
        // TODO: implement getAvgDamagePerMatch for Lifetime. Currently we are summing everything.

    }
}
