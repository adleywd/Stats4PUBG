package br.com.adley.stats4pubg.wrapper;

import java.util.List;

import br.com.adley.stats4pubg.data.models.Stats;
import br.com.adley.stats4pubg.library.Utils;

/**
 * Created by Gabriel Lundgren on 08/10/2017.
 */

public abstract class AbstractStats {
    private int mRoundsPlayed;
    private int mKills;
    private int mWins;
    private int mTop10s;
    private int mHealsTotal;
    private double mAvgDamagePerMatch;
    private double mKdAverage;
    private List<Stats> mStatsList;

    public void setStats( List<Stats> statsList){
        mStatsList = statsList;
        for (Stats stats : mStatsList) {

            switch (stats.getLabel()){
                // Get ALL Matches Played
                case "Rounds Played":
                    mRoundsPlayed += stats.getValueInt();
                    break;
                // Get ALL Kills
                case "Kills":
                    mKills += stats.getValueInt();
                    break;
                // Get ALL Wins
                case "Wins":
                    mWins += stats.getValueInt();
                    break;
                // Get ALL Top 10s
                case "Top 10s":
                    mTop10s += stats.getValueInt();
                    break;
                // Get ALL Heals
                case "Heals":
                    mHealsTotal += stats.getValueInt();
                    break;
                // Get Average Damage Per Match
                case "Avg Dmg per Match":
                    mAvgDamagePerMatch += stats.getValueDec();
                    break;
                // Get K/D Ratio
                case "K/D Ratio":
                    mKdAverage += stats.getValueDec();
                    break;
            }
        }
        mKdAverage = Utils.getKDAverage(mKills, mRoundsPlayed, mWins);
    }

    public int getRoundsPlayed() {
        return this.mRoundsPlayed;
    }

    public int getKills() {
        return this.mKills;
    }

    public int getWins() {
        return this.mWins;
    }

    public int getTop10s() {
        return this.mTop10s;
    }

    public int getHealsTotal() {
        return this.mHealsTotal;
    }

    public double getAvgDamagePerMatch(){
        return this.mAvgDamagePerMatch;
    }

    public double getKdAverage() {
        return this.mKdAverage;
    }

    public void setRoundsPlayed(int roundsPlayed) {
        this.mRoundsPlayed = roundsPlayed;
    }

    public void setKills(int kills) {
        this.mKills = kills;
    }

    public void setWins(int wins) {
        this.mWins = wins;
    }

    public void setTop10s(int top10s) {
        this.mTop10s = top10s;
    }

    public void setHealsTotal(int healsTotal) {
        this.mHealsTotal = healsTotal;
    }

    public void setAvgDamagePerMatch(double avgDamagePerMatch) {
        this.mAvgDamagePerMatch = avgDamagePerMatch;
    }

    public void setKdAverage(double kdAverage) {
        this.mKdAverage = kdAverage;
    }

    public List<Stats> getStatsList() {
        return mStatsList;
    }
}
