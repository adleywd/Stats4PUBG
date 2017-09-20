package br.com.adley.pubgstats.data;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    private List<Stats> statses = null;

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

    public List<Stats> getStatses() {
        return statses;
    }

    public void setStatses(List<Stats> statses) {
        this.statses = statses;
    }

}