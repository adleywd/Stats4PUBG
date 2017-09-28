package br.com.adley.pubgstats.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Season {

    @SerializedName("Region")
    @Expose
    private String region;
    @SerializedName("Season")
    @Expose
    private String season;
    @SerializedName("Match")
    @Expose
    private String match;
    @SerializedName("Stats")
    @Expose
    private List<Stats> stats = null;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getMatch() {
        return match;
    }

    public void setMatch(String match) {
        this.match = match;
    }

    public List<Stats> getStats() {
        return stats;
    }

    public void setStats(List<Stats> statses) {
        this.stats = statses;
    }

}