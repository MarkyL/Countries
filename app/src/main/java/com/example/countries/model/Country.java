package com.example.countries.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Country implements Serializable {

    private String name;

    private Double area;

    private List<String> borders = null;

    private String nativeName;

    @SerializedName("alpha3Code")
    private String country3CharName;

    //region getters/setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getArea() {
        if (area == null) {
            return -1.0;
        }
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public List<String> getBorders() {
        return borders;
    }

    public void setBorders(List<String> borders) {
        this.borders = borders;
    }

    public String getNativeName() {
        return nativeName;
    }

    public void setNativeName(String nativeName) {
        this.nativeName = nativeName;
    }

    public String getCountry3CharName() {
        return country3CharName;
    }

    public void setCountry3CharName(String country3CharName) {
        this.country3CharName = country3CharName;
    }

    //endregion

}
