package br.com.adley.pubgstats.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Player implements Parcelable {

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
    @SerializedName("MatchHistory")
    @Expose
    private List<MatchHistory> matchHistory = null;
    @SerializedName("error")
    @Expose
    private String error;
    @SerializedName("message")
    @Expose
    private String errorMessage;

    protected Player(Parcel in) {
        platformId = in.readInt();
        accountId = in.readString();
        avatar = in.readString();
        selectedRegion = in.readString();
        defaultSeason = in.readString();
        seasonDisplay = in.readString();
        lastUpdated = in.readString();
        playerName = in.readString();
        pubgTrackerId = in.readInt();
        error = in.readString();
        errorMessage = in.readString();
    }

    public Player(){

    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

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

    public List<MatchHistory> getMatchHistory() {
        return matchHistory;
    }

    public void setMatchHistory(List<MatchHistory> matchHistory) {
        this.matchHistory = matchHistory;
    }

    public String getError() { return error; }

    public void setError(String error) { this.error = error; }

    public String getErrorMessage() { return errorMessage; }

    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(platformId);
        parcel.writeString(accountId);
        parcel.writeString(avatar);
        parcel.writeString(selectedRegion);
        parcel.writeString(defaultSeason);
        parcel.writeString(seasonDisplay);
        parcel.writeString(lastUpdated);
        parcel.writeString(playerName);
        parcel.writeInt(pubgTrackerId);
        parcel.writeString(error);
        parcel.writeString(errorMessage);
    }
}