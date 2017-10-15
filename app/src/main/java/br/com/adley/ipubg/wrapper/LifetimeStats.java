package br.com.adley.ipubg.wrapper;

import java.util.List;

import br.com.adley.ipubg.data.models.Season;
import br.com.adley.ipubg.data.models.Stats;
import br.com.adley.ipubg.library.Utils;

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

        // Get the average of K/D
        setKdAverage(Utils.getKDAverage(getKills(), getRoundsPlayed(), getWins()));
    }
}
