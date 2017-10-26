package br.com.adley.ipubg.fragments;

import br.com.adley.ipubg.wrapper.AbstractStats;

/**
 * Created by Adley on 13/10/2017.
 * Interface that bind the stats values
 */

interface BindEventsInterface {

    void bindStatsValues(AbstractStats abstractStats);
    void bindStatsValues(AbstractStats abstractStats, String selectedRegion, String fppType);
}
