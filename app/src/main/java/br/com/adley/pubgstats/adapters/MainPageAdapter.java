package br.com.adley.pubgstats.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import br.com.adley.pubgstats.fragments.DuoFragment;
import br.com.adley.pubgstats.fragments.LifeTimeFragment;
import br.com.adley.pubgstats.fragments.SoloFragment;
import br.com.adley.pubgstats.fragments.SquadFragment;

/**
 * Created by Adley on 09/10/2017.
 * Page adapter for tabs in main activity.
 */

public class MainPageAdapter extends FragmentStatePagerAdapter {
    private int mNumOfTabs;

    public MainPageAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return new LifeTimeFragment();
            case 1:
                return new SoloFragment();
            case 2:
                return new DuoFragment();
            case 3:
                return new SquadFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
