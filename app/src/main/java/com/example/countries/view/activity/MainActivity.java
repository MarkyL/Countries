package com.example.countries.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.countries.R;
import com.example.countries.model.Country;
import com.example.countries.presenter.MainView;
import com.example.countries.presenter.MainViewFragment;
import com.example.countries.presenter.MainViewPresenter;
import com.example.countries.view.adapter.CountryAdapter;
import com.example.countries.view.fragment.CountryFragment;
import com.example.countries.view.fragment.MainFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements
        MainView,
        MainFragment.MainFragmentCallback,
        CountryFragment.CountryFragmentCallback {

    private static final String TAG = "MainActivity";

    private int mContainerID;
    private MainViewPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: ");
        initUI();
        mPresenter.onViewCreated(this);
        addFragment(MainFragment.newInstance());
    }

    private void initUI() {
        mContainerID = R.id.fragments_container;
        mPresenter = new MainViewPresenter();
    }

    private void addFragment(Fragment toLoad) {
        FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction().
                add(mContainerID, toLoad);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof MainFragment) {
            ((MainFragment)fragment).setMainFragmentCallback(this);
        } else if (fragment instanceof CountryFragment) {
            ((CountryFragment)fragment).setCountryFragmentCallback(this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        mPresenter.onViewAttached(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                return true;
            }
            case R.id.sort_by_name: {
                mPresenter.onSortByName();
            }
            break;
            case R.id.sort_by_area: {
                mPresenter.onSortByArea();
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        mPresenter.onNavigateUp();
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: ");
        mPresenter.onViewDetached();
        super.onStop();
    }

    /**
     * @return the currently displayed fragment cast to MainViewFragment
     */
    private @Nullable MainViewFragment getCurrentFragmentAsMainView() {
        Fragment currentFragment = getCurrentFragment();
        if (currentFragment instanceof MainViewFragment) {
            return (MainViewFragment)currentFragment;
        }
        return null;
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(mContainerID);
    }

    //region MainView
    @Override
    public void showProgress() {
        Fragment currentFragment = getCurrentFragment();
        if (currentFragment instanceof MainViewFragment) {
            ((MainViewFragment)currentFragment).showProgress();
        }
    }

    @Override
    public void hideProgress() {
        Fragment currentFragment = getCurrentFragment();
        if (currentFragment instanceof MainViewFragment) {
            ((MainViewFragment)currentFragment).hideProgress();
        }
    }

    @Override
    public void setItems(List<Country> items) {
        Fragment currentFragment = getCurrentFragment();
        if (currentFragment instanceof MainViewFragment) {
            ((MainViewFragment) currentFragment).setAdapter(new CountryAdapter(items, mPresenter::onCountryClicked));
        }
    }

    @Override
    public void showMessage(String message) {
        MainViewFragment mainViewFragment = getCurrentFragmentAsMainView();
        if (mainViewFragment != null) {
            mainViewFragment.showMessage(message);
        }
    }

    @Override
    public void navigateToCountryScreen(String countryName) {
        addFragment(CountryFragment.newInstance(countryName));
    }

    @Override
    public void resetViews() {
        MainViewFragment mainViewFragment = getCurrentFragmentAsMainView();
        if (mainViewFragment != null) {
            mainViewFragment.updateActionBar();
            mainViewFragment.scrollToTop();
        }
    }

    //endregion

    //region FragmentCallback

    @Override
    public void onMainFragmentViewCreated() {
        mPresenter.onMainFragmentResume();
    }

    @Override
    public void onCountryFragmentResume() {
        mPresenter.onCountryFragmentResume();
    }

    //endregion
}
