package com.example.countries.network;

import com.example.countries.model.Country;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CountriesService {

    // https://restcountries.eu/rest/v2/all
    @GET("all/")
    Call<List<Country>> getAllCountries();

    // https://restcountries.eu/rest/v2/{service}?fields={field};{field};{field}
    // https://restcountries.eu/rest/v2/all?fields=name;capital;currencies
    @GET("all?fields=name;nativeName;area;borders;alpha3Code")
    Call<List<Country>> getAllCountriesFiltered();
}
