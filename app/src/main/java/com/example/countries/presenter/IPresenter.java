package com.example.countries.presenter;

public interface IPresenter {
    void onViewCreated(MainView view);
    void onViewAttached(MainView view);
    void onViewDetached();
}
