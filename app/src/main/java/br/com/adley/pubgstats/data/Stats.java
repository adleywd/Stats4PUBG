package br.com.adley.pubgstats.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Stats {

    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("field")
    @Expose
    private String field;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("ValueInt")
    @Expose
    private int valueInt;
    @SerializedName("ValueDec")
    @Expose
    private double valueDec;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("rank")
    @Expose
    private int rank;
    @SerializedName("percentile")
    @Expose
    private int percentile;
    @SerializedName("displayValue")
    @Expose
    private String displayValue;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getValueInt() {
        return valueInt;
    }

    public void setValueInt(int valueInt) {
        this.valueInt = valueInt;
    }

    public double getValueDec() {
        return valueDec;
    }

    public void setValueDec(double valueDec) {
        this.valueDec = valueDec;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getPercentile() {
        return percentile;
    }

    public void setPercentile(int percentile) {
        this.percentile = percentile;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

}