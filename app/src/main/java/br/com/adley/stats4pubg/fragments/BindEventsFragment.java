package br.com.adley.stats4pubg.fragments;

import br.com.adley.stats4pubg.wrapper.AbstractStats;

/**
 * Created by Adley on 13/10/2017.
 * Interface that bind the stats values
 */

interface BindEventsInterface {

    void bindStatsValues(AbstractStats abstractStats);
    void bindStatsValues(AbstractStats abstractStats, String selectedRegion, String fppType);
}
