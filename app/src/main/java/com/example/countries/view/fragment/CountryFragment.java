package com.example.countries.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countries.R;
import com.example.countries.presenter.MainViewFragment;
import com.example.countries.view.adapter.CountryAdapter;

public class CountryFragment extends Fragment implements MainViewFragment {
    private static final String TAG = "CountryFragment";
    private static final String ARG_COUNTRY_NAME = "country_name";

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private CountryFragmentCallback mCallback;

    private String mCountryName;
    private LinearLayoutManager mLayoutManager;

    public CountryFragment() {
        // Required empty public constructor
    }

    public void setCountryFragmentCallback(CountryFragmentCallback callback) {
        this.mCallback = callback;
    }
    public interface CountryFragmentCallback {
        void onCountryFragmentResume();
    }

    public static CountryFragment newInstance(String countryName) {
        final Bundle args = new Bundle();
        args.putString(ARG_COUNTRY_NAME, countryName);
        CountryFragment countryFragment = new CountryFragment();
        countryFragment.setArguments(args);
        return countryFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        mCountryName = arguments.getString(ARG_COUNTRY_NAME, "Specific country");
        showMessage("CountryFragment - " + mCountryName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_country, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        mCallback.onCountryFragmentResume();
    }

    private void initUI(View view) {
        mRecyclerView = view.findViewById(R.id.list);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mProgressBar = view.findViewById(R.id.progress);
        handleActionBar();
    }

    private void handleActionBar() {
        FragmentActivity fragmentActivity = getActivity();
        ActionBar supportActionBar = ((AppCompatActivity) fragmentActivity).getSupportActionBar();
        supportActionBar.setTitle(mCountryName);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void setAdapter(CountryAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void updateActionBar() {
        handleActionBar();
    }

    @Override
    public void scrollToTop() {
        mLayoutManager.scrollToPosition(0);
    }
}
