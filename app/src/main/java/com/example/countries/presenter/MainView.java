package com.example.countries.presenter;

import com.example.countries.model.Country;

import java.util.List;

public interface MainView {

    void showProgress();

    void hideProgress();

    void setItems(List<Country> items);

    void showMessage(String message);

    void navigateToCountryScreen(String countryName);

    void resetViews();
}
