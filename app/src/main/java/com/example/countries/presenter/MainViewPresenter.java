package com.example.countries.presenter;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.countries.enums.CountrySortTypes;
import com.example.countries.factory.CountrySortingFactory;
import com.example.countries.model.Country;
import com.example.countries.network.RetrofitClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainViewPresenter implements IPresenter {

    private static final String TAG = "MainViewPresenter";

    private MainView mMainView;
    private List<Country> mCountriesDataSet;
    private Map<String, Country> mCountryHashMap;

    private List<Country> mCurrentDisplayedCountries;

    // By default, fetched data is by name ASC order.
    private boolean mIsLastSortAsc = true;
    private CountrySortTypes mLastSortType = CountrySortTypes.NAME;
    private Country mCurrentlySelectedCountry;

    public void onMainFragmentResume() {
        Log.d(TAG, "onMainFragmentViewCreated: ");
        mCurrentlySelectedCountry = null;
        if (mCountriesDataSet == null) {
            loadCountries();
        } else {
            mCurrentDisplayedCountries = mCountriesDataSet;
            mCurrentlySelectedCountry = null;
        }
    }

    public void onCountryFragmentResume() {
        Log.d(TAG, "onCountryFragmentResume: ");
        if (mMainView != null) {
            mCurrentDisplayedCountries = getBorderCountries(mCurrentlySelectedCountry);
            mMainView.setItems(mCurrentDisplayedCountries);
        }
    }

    private void loadCountries() {
        toggleProgress(true);
        Call<List<Country>> call = RetrofitClient.countriesService.getAllCountriesFiltered();
        Log.d(TAG, "loadCountries: START");
        call.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(@NonNull Call<List<Country>> call,@NonNull Response<List<Country>> response) {
                Log.d(TAG, "loadCountries: FINISH");
                toggleProgress(false);
                if (response.isSuccessful()) {
                    mCountriesDataSet = response.body();
                    generateHashMap();
                    updateDisplayedCountries(mCountriesDataSet);
                }
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                toggleProgress(false);
                showMessage("getAllCountries() failed");
            }
        });
    }

    private void generateHashMap() {
        mCountryHashMap =
                mCountriesDataSet.stream().collect(Collectors.toMap(Country::getCountry3CharName, item -> item));
    }

    private void updateDisplayedCountries(List<Country> countries) {
        mCurrentDisplayedCountries = countries;
        if (mMainView != null) {
            mMainView.setItems(mCurrentDisplayedCountries);
        }
    }

    public void onCountryClicked(final Country country) {
        if (mMainView != null) {
            if (mCurrentlySelectedCountry == null) {
                mCurrentlySelectedCountry = country;
                mMainView.navigateToCountryScreen(country.getName());
            } else {
                Log.d(TAG, "onCountryClicked: no action here.");
            }
        }
    }

    private List<Country> getBorderCountries(Country country) {
        List<String> countryBorders3CharNames = country.getBorders();
        List<Country> countryBorders = new ArrayList<>();
        for (String country3CharName : countryBorders3CharNames) {
            countryBorders.add(mCountryHashMap.get(country3CharName));
        }
        return countryBorders;
    }

    private void toggleProgress(final boolean isShown) {
        if (mMainView != null) {
            if (isShown) {
                mMainView.showProgress();
            } else {
                mMainView.hideProgress();
            }
        }
    }

    private void showMessage(final String message) {
        if (mMainView != null) {
            mMainView.showMessage(message);
        }
    }

    @Override
    public void onViewCreated(MainView view) {
        mMainView = view;
    }

    @Override
    public void onViewAttached(MainView view) {
        mMainView = view;
    }

    @Override
    public void onViewDetached() {
        mMainView = null;
    }

    public void onSortByName() {
        sortCountries(CountrySortTypes.NAME);
    }

    public void onSortByArea() {
        sortCountries(CountrySortTypes.AREA);
    }

    private void sortCountries(CountrySortTypes type) {
        Log.d(TAG, "sortCountries() called with: type = [" + type + "]");
        if (mLastSortType == type) {
            // If the sorting type is the same, we want to toggle sorting order.
            mIsLastSortAsc = !mIsLastSortAsc;
        } else {
            // the sorting type is new, default order by is ASC.
            mIsLastSortAsc = true;
        }
        Collections.sort(mCurrentDisplayedCountries, new CountrySortingFactory(type, mIsLastSortAsc));
        mLastSortType = type;
        showMessage("Sorted by: " + type.name() + ", order: " + (mIsLastSortAsc ? "ASC" : "DESC"));
        updateDisplayedCountries(mCurrentDisplayedCountries);
    }

    public void onNavigateUp() {
        mCurrentlySelectedCountry = null;
        if (mMainView != null) {
            mMainView.resetViews();
        }
    }

}
