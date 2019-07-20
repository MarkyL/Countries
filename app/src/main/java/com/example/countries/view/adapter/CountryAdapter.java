package com.example.countries.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.countries.R;
import com.example.countries.model.Country;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int COUNTRY_ITEM_TYPE = 0;
    private static final int HEADER_ITEM_TYPE = COUNTRY_ITEM_TYPE + 1;

    private List<Country> mCountries;
    private ActionListener mListener;

    public CountryAdapter(List<Country> countries, ActionListener listener) {
        mCountries = countries;
        mListener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case HEADER_ITEM_TYPE: {
                return new HeaderViewHolder(getView(viewGroup, R.layout.country_header_row));
            }
            case COUNTRY_ITEM_TYPE: {
                return new CountryViewHolder(getView(viewGroup, R.layout.country_row));
            }
            default: {
                return new CountryViewHolder(getView(viewGroup, R.layout.country_row));
            }
        }
    }

    private View getView(final ViewGroup viewGroup, int ref) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(ref, viewGroup, false);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case HEADER_ITEM_TYPE: {
                bindHeaderViewHolder((HeaderViewHolder) holder);
            }
            break;
            case COUNTRY_ITEM_TYPE: {
                bindCountryViewHolder((CountryViewHolder) holder, position);
            }
            break;
        }
    }

    private void bindHeaderViewHolder(final HeaderViewHolder holder) {
        // STUB!
    }

    private void bindCountryViewHolder(final CountryViewHolder holder, int position) {
        // actual position due to usage of header views.
        final int actualPosition = position - 1;
        holder.mSerial.setText(String.valueOf(position));
        Country country = mCountries.get(actualPosition);
        String countryName = country.getName();
        holder.mName.setText(countryName);
        holder.mNativeName.setText(country.getNativeName());

        holder.itemView.setOnClickListener(v -> mListener.onCountryClicked(country));
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? HEADER_ITEM_TYPE : COUNTRY_ITEM_TYPE;
    }

    @Override
    public int getItemCount() {
        // +1 for header
        return mCountries == null ? 1 : mCountries.size() + 1;
    }

    public interface ActionListener {
        void onCountryClicked(final Country country);
    }

    public List<Country> getCountries() {
        return mCountries;
    }

    static class CountryViewHolder extends RecyclerView.ViewHolder {

        TextView mSerial;
        TextView mName;
        TextView mNativeName;

        CountryViewHolder(@NonNull View itemView) {
            super(itemView);
            mSerial = itemView.findViewById(R.id.serial_number_tv);
            mName = itemView.findViewById(R.id.name);
            mNativeName = itemView.findViewById(R.id.native_name);
        }
    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
