package com.solutionsmax.silverlinktask.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FactsResponse {
    @SerializedName("title")
    private String sTitle;
    @SerializedName("rows")
    private List<FactsListItem> result;

    public String getsTitle() {
        return sTitle;
    }

    public void setsTitle(String sTitle) {
        this.sTitle = sTitle;
    }
}
