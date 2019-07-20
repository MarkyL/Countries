package com.example.countries.factory;

import com.example.countries.enums.CountrySortTypes;
import com.example.countries.model.Country;

import java.util.Comparator;

public class CountrySortingFactory implements Comparator<Country> {

    private boolean mIsAscending;
    private CountrySortTypes mSortType;

    public CountrySortingFactory(final CountrySortTypes sortType, final boolean isAscending) {
        mIsAscending = isAscending;
        mSortType = sortType;
    }

    // Used for sorting in ascending order of area
    @Override
    public int compare(Country o1, Country o2) {
        switch (mSortType) {
            case NAME: {
                return sortByName(o1, o2);
            }
            case AREA: {
                return sortByArea(o1, o2);
            }
            default:
        }
        return o1.getArea().compareTo(o2.getArea());
    }

    private int sortByName(Country o1, Country o2) {
        return mIsAscending ?
                o1.getName().compareTo(o2.getName()) : o2.getName().compareTo(o1.getName());
    }

    private int sortByArea(Country o1, Country o2) {
        return mIsAscending ?
                o1.getArea().compareTo(o2.getArea()) : o2.getArea().compareTo(o1.getArea());
    }
}
