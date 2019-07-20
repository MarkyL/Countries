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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countries.R;
import com.example.countries.presenter.MainViewFragment;
import com.example.countries.view.adapter.CountryAdapter;

public class MainFragment extends Fragment implements MainViewFragment {
    private static final String TAG = "MainFragment";

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private MainFragmentCallback mCallback;
    private LinearLayoutManager mLayoutManager;

    public MainFragment() {
        // Required empty public constructor
    }

    public void setMainFragmentCallback(MainFragmentCallback callback) {
        this.mCallback = callback;
    }
    public interface MainFragmentCallback {
        void onMainFragmentViewCreated();
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
        mCallback.onMainFragmentViewCreated();
    }

    private void initUI(View view) {
        mRecyclerView = view.findViewById(R.id.list);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mProgressBar = view.findViewById(R.id.progress);
        handleActionBar();
    }

    private void handleActionBar() {
        AppCompatActivity compatActivity = (AppCompatActivity) getActivity();
        if (compatActivity != null) {
            ActionBar supportActionBar = compatActivity.getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setTitle(getString(R.string.action_bar_title_country));
                supportActionBar.setDisplayHomeAsUpEnabled(false);
            }
        }
    }

    //region MainViewFragment

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

    //endregion
}
