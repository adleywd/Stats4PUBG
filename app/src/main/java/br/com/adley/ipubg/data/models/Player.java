package br.com.adley.ipubg.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import br.com.adley.ipubg.wrapper.DuoFppStats;
import br.com.adley.ipubg.wrapper.DuoStats;
import br.com.adley.ipubg.wrapper.LifetimeStats;
import br.com.adley.ipubg.wrapper.SoloFppStats;
import br.com.adley.ipubg.wrapper.SoloStats;
import br.com.adley.ipubg.wrapper.SquadFppStats;
import br.com.adley.ipubg.wrapper.SquadStats;

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

    private SoloStats mSoloStats;
    private DuoStats mDuoStats;
    private SquadStats mSquadStats;
    private LifetimeStats mLifetimeStats;
    private SoloFppStats mSoloFppStats;
    private DuoFppStats mDuoFppStats;
    private SquadFppStats mSquadFppStats;

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


    public SoloStats getSoloStats(){
        if(mSoloStats == null){
            if(seasons != null){
                mSoloStats = new SoloStats(seasons, selectedRegion);
            }
        }
        return mSoloStats;
    }

    public DuoStats getDuoStats(){
        if(mDuoStats == null){
            if(seasons != null){
                mDuoStats = new DuoStats(seasons, selectedRegion);
            }
        }
        return mDuoStats;
    }

    public SquadStats getSquadStats(){
        if(mSquadStats == null){
            if(seasons != null){
                mSquadStats = new SquadStats(seasons, selectedRegion);
            }
        }
        return mSquadStats;
    }

    public LifetimeStats getLifetimeStats() {
        if(mLifetimeStats == null){
            if(seasons != null){
                mLifetimeStats = new LifetimeStats(seasons);
            }
        }
        return mLifetimeStats;
    }

    public SoloFppStats getSoloFppStats() {
        if(mSoloFppStats == null){
            if(seasons != null){
                mSoloFppStats = new SoloFppStats(seasons, selectedRegion);
            }
        }
        return mSoloFppStats;
    }

    public DuoFppStats getDuoFppStats() {
        if(mDuoFppStats == null){
            if(seasons != null){
                mDuoFppStats = new DuoFppStats(seasons, selectedRegion);
            }
        }
        return mDuoFppStats;
    }

    public SquadFppStats getSquadFppStats() {
        if(mSquadFppStats == null){
            if(seasons != null){
                mSquadFppStats = new SquadFppStats(seasons, selectedRegion);
            }
        }
        return mSquadFppStats;
    }
}