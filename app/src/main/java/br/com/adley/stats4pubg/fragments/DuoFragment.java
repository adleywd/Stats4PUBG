package br.com.adley.stats4pubg.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.adley.stats4pubg.R;
import br.com.adley.stats4pubg.wrapper.AbstractStats;

/**
 * Created by Adley on 06/10/2017.
 * Fragment with Duo Data.
 */

public class DuoFragment extends Fragment implements BindEventsInterface {
    private TextView mMatches;
    private TextView mWins;
    private TextView mTop10s;
    private TextView mKills;
    private TextView mKD;
    private TextView mHeals;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_duo, container, false);
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
        mMatches.setText(String.valueOf(abstractStats.getRoundsPlayed()));
        mWins.setText(String.valueOf(abstractStats.getWins()));
        mTop10s.setText(String.valueOf(abstractStats.getTop10s()));
        mKills.setText(String.valueOf(abstractStats.getKills()));
        mKD.setText(String.format(java.util.Locale.US, "%.2f", abstractStats.getKdAverage()));
        mHeals.setText(String.valueOf(abstractStats.getHealsTotal()));
    }

    @Override
    public void bindStatsValues(AbstractStats abstractStats, String selectedRegion, String fppType) {
        throw new UnsupportedOperationException();
    }
}