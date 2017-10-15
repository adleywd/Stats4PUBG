package br.com.adley.pubgstats.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import br.com.adley.pubgstats.R;
import br.com.adley.pubgstats.wrapper.AbstractStats;

/**
 * Created by Adley on 06/10/2017.
 * Fragment with Squad Data
 */

public class SquadFppFragment extends Fragment implements BindEventsInterface{
    private TextView mMatches;
    private TextView mWins;
    private TextView mTop10s;
    private TextView mKills;
    private TextView mKD;
    private TextView mHeals;
    private ScrollView mScrollMainView;
    private TextView mLabelWithoutFpp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_squad_fpp, container, false);
        mScrollMainView = rootView.findViewById(R.id.stats_content_scrollable);
        mLabelWithoutFpp = rootView.findViewById(R.id.label_without_fpp);
        mMatches = rootView.findViewById(R.id.matchesPlayedValue);
        mWins = rootView.findViewById(R.id.winsValue);
        mTop10s = rootView.findViewById(R.id.top10sValue);
        mKills = rootView.findViewById(R.id.killsValue);
        mKD = rootView.findViewById(R.id.kdValue);
        mHeals = rootView.findViewById(R.id.healValue);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void bindStatsValues(AbstractStats abstractStats) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void bindStatsValues(AbstractStats abstractStats, String selectedRegion, String fppType) {
        if(abstractStats.getStatsList() == null){
            mLabelWithoutFpp.setText(getString(R.string.msg_region_without_fpp, selectedRegion, fppType));
            checkViewHasValues(false);
        }else {
            checkViewHasValues(true);
            mMatches.setText(String.valueOf(abstractStats.getRoundsPlayed()));
            mWins.setText(String.valueOf(abstractStats.getWins()));
            mTop10s.setText(String.valueOf(abstractStats.getTop10s()));
            mKills.setText(String.valueOf(abstractStats.getKills()));
            mKD.setText(String.format(java.util.Locale.US, "%.2f", abstractStats.getKdAverage()));
            mHeals.setText(String.valueOf(abstractStats.getHealsTotal()));
        }
    }

    private void checkViewHasValues(boolean hasValues){
        if(hasValues){
            mLabelWithoutFpp.setVisibility(View.GONE);
            mScrollMainView.setVisibility(View.VISIBLE);
        }else{
            mLabelWithoutFpp.setVisibility(View.VISIBLE);
            mScrollMainView.setVisibility(View.GONE);
        }
    }
}