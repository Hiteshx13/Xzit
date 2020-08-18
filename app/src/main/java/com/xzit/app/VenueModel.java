package com.xzit.app;

public class VenueModel {
    public VenueModel(String name,
                      boolean isSelected) {
        this.name = name;
        this.isSelected = isSelected;
    }

    public String name;
    public boolean isSelected = false;
}
