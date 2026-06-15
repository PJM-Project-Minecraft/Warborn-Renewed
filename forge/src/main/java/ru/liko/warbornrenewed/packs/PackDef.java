package ru.liko.warbornrenewed.packs;

import com.google.gson.annotations.SerializedName;

public class PackDef {
    
    @SerializedName("tab_name")
    private String tabName;
    
    @SerializedName("icon_item")
    private String iconItem;

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public String getIconItem() {
        return iconItem;
    }

    public void setIconItem(String iconItem) {
        this.iconItem = iconItem;
    }
}