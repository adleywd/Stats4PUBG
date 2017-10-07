package br.com.adley.pubgstats.data;

import java.text.DecimalFormat;
import java.util.List;

import br.com.adley.pubgstats.library.Utils;

/**
 * Created by Adley.Damaceno on 06/10/2017.
 * Contain the lifetime stats about the player.
 */

public class LifetimeStats {

    private int roundsPlayed;
    private int kills;
    private int wins;
    private int top10s;
    private int healsTotal;
    private float kdAverage;


    public LifetimeStats() {
    }

    public LifetimeStats(List<Season> seasons) {
        List<Stats> statsList;
        for (Season season : seasons) {
            // Get all seasons "Aggregated". This type of season contains all seasons.
            // Here the type o game (solo, duo...) doesn't matter. We want all.
            if (season.getRegion().equals("agg")) {
                statsList = season.getStats();
                for (Stats stats : statsList) {
                    // Get All Matches Played
                    if (stats.getLabel().equals("Rounds Played")) {
                        roundsPlayed += stats.getValueInt();
                    }
                    // Get All Kills
                    if (stats.getLabel().equals("Kills")) {
                        kills += stats.getValueInt();
                    }
                    // Get All Wins
                    if (stats.getLabel().equals("Wins")) {
                        wins += stats.getValueInt();
                    }
                    // Get All Top 10s
                    if (stats.getLabel().equals("Top 10s")) {
                        top10s += stats.getValueInt();
                    }
                    // Get All Heals
                    if (stats.getLabel().equals("Heals")) {
                        healsTotal += stats.getValueInt();
                    }
                }
            }
        }
        // Get the average of K/D
        kdAverage = Utils.getKDAverage(kills, roundsPlayed, wins);
    }

    public int getRoundsPlayed() {
        return roundsPlayed;
    }

    public void setRoundsPlayed(int roundsPlayed) {
        this.roundsPlayed = roundsPlayed;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getTop10s() {
        return top10s;
    }

    public void setTop10s(int top10s) {
        this.top10s = top10s;
    }

    public int getHealsTotal() {
        return healsTotal;
    }

    public void setHealsTotal(int healsTotal) {
        this.healsTotal = healsTotal;
    }

    public float getKdAverage() {
        return kdAverage;
    }

    public void setKdAverage(float kdAverage) {
        this.kdAverage = kdAverage;
    }
}