package com.xzit.app.retrofit.model.response.masterdata;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subtype {
    @SerializedName("lookupId")
    @Expose
    private String lookupId;
    @SerializedName("lookupValue")
    @Expose
    private String lookupValue;
    @SerializedName("displayValue")
    @Expose
    private String displayValue;
    public boolean isSelected = false;

    public String getLookupId() {
        return lookupId;
    }

    public void setLookupId(String lookupId) {
        this.lookupId = lookupId;
    }

    public String getLookupValue() {
        return lookupValue;
    }

    public void setLookupValue(String lookupValue) {
        this.lookupValue = lookupValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

}
