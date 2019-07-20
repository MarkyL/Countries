package com.example.countries.presenter;

import com.example.countries.view.adapter.CountryAdapter;

public interface MainViewFragment {
    void showProgress();

    void hideProgress();

    void setAdapter(CountryAdapter adapter);

    void showMessage(String message);

    void updateActionBar();

    void scrollToTop();
}
