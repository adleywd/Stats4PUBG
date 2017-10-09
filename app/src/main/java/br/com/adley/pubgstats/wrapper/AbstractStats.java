package br.com.adley.pubgstats.wrapper;

import java.util.List;

import br.com.adley.pubgstats.data.Stats;

/**
 * Created by Gabriel Lundgren on 08/10/2017.
 */

public abstract class AbstractStats {
    public int roundsPlayed;
    public int kills;
    public int wins;
    public int top10s;
    public int healsTotal;
    public double avgDamagePerMatch;
    public double kdAverage;

    public void setStats( List<Stats> statsList){
        for (Stats stats : statsList) {

            switch (stats.getLabel()){
                // Get All Matches Played
                case "Rounds Played":
                    roundsPlayed += stats.getValueInt();
                    break;
                // Get All Kills
                case "Kills":
                    kills += stats.getValueInt();
                    break;
                // Get All Wins
                case "Wins":
                    wins += stats.getValueInt();
                    break;
                // Get All Top 10s
                case "Top 10s":
                    top10s += stats.getValueInt();
                    break;
                // Get All Heals
                case "Heals":
                    healsTotal += stats.getValueInt();
                    break;
                // Get Average Damage Per Match
                case "Avg Dmg per Match":
                    avgDamagePerMatch += stats.getValueDec();
                    break;
                // Get K/D Ratio
                case "K/D Ratio":
                    kdAverage += stats.getValueDec();
                    break;
            }
        }
    }

    public int getRoundsPlayed() {
        return this.roundsPlayed;
    }

    public int getKills() {
        return this.kills;
    }

    public int getWins() {
        return this.wins;
    }

    public int getTop10s() {
        return this.top10s;
    }

    public int getHealsTotal() {
        return this.healsTotal;
    }

    public double getAvgDamagePerMatch(){
        return this.avgDamagePerMatch;
    }

    public double getKdAverage() {
        return this.kdAverage;
    }
}
