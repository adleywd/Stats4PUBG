package br.com.adley.pubgstats.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Player {

    @SerializedName("platformId")
    @Expose
    private int platformId;
    @SerializedName("AccountId")
    @Expose
    private String accountId;
    @SerializedName("Avatar")
    @Expose
    private String avatar;
    @SerializedName("selectedRegion")
    @Expose
    private String selectedRegion;
    @SerializedName("defaultSeason")
    @Expose
    private String defaultSeason;
    @SerializedName("seasonDisplay")
    @Expose
    private String seasonDisplay;
    @SerializedName("LastUpdated")
    @Expose
    private String lastUpdated;
    @SerializedName("PlayerName")
    @Expose
    private String playerName;
    @SerializedName("PubgTrackerId")
    @Expose
    private int pubgTrackerId;
    @SerializedName("Stats")
    @Expose
    private List<Season> seasons = null;
    //@SerializedName("MatchHistory")
    //@Expose
    //private List<MatchHistory> matchHistory = null;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("message")
    @Expose
    private String errorMessage;

    private LifetimeStats lifetimeStats;

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSelectedRegion() {
        return selectedRegion;
    }

    public void setSelectedRegion(String selectedRegion) {
        this.selectedRegion = selectedRegion;
    }

    public String getDefaultSeason() {
        return defaultSeason;
    }

    public void setDefaultSeason(String defaultSeason) {
        this.defaultSeason = defaultSeason;
    }

    public String getSeasonDisplay() {
        return seasonDisplay;
    }

    public void setSeasonDisplay(String seasonDisplay) {
        this.seasonDisplay = seasonDisplay;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPubgTrackerId() {
        return pubgTrackerId;
    }

    public void setPubgTrackerId(int pubgTrackerId) {
        this.pubgTrackerId = pubgTrackerId;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

    public String getError() { return error; }

    public void setError(String error) { this.error = error; }

    public String getErrorMessage() { return errorMessage; }

    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public LifetimeStats getLifetimeStats() {
        if(lifetimeStats == null){
            if(seasons != null){
                lifetimeStats = new LifetimeStats(seasons);
            }
        }
        return lifetimeStats;
    }

    public void setLifetimeStats(LifetimeStats lifetimeStats) {
        this.lifetimeStats = lifetimeStats;
    }
}