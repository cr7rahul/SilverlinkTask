package com.solutionsmax.silverlinktask.model;

import com.google.gson.annotations.SerializedName;

public class FactsListItem {
    @SerializedName("title")
    private String sTitle;
    @SerializedName("description")
    private String sDescription;
    @SerializedName("imageHref")
    private String sImageRef;

    public FactsListItem(String sTitle, String sDescription, String sImageRef) {
        this.sTitle = sTitle;
        this.sDescription = sDescription;
        this.sImageRef = sImageRef;
    }

    public String getsTitle() {
        return sTitle;
    }

    public void setsTitle(String sTitle) {
        this.sTitle = sTitle;
    }

    public String getsDescription() {
        return sDescription;
    }

    public void setsDescription(String sDescription) {
        this.sDescription = sDescription;
    }

    public String getsImageRef() {
        return sImageRef;
    }

    public void setsImageRef(String sImageRef) {
        this.sImageRef = sImageRef;
    }
}
