package br.com.adley.pubgstats.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import br.com.adley.pubgstats.fragments.DuoFragment;
import br.com.adley.pubgstats.fragments.LifeTimeFragment;
import br.com.adley.pubgstats.fragments.SoloFragment;
import br.com.adley.pubgstats.fragments.SquadFragment;

/**
 * Created by Adley on 09/10/2017.
 * Page adapter for tabs in main activity.
 */

public class MainPageAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragmentsList;

    public MainPageAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.mFragmentsList = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentsList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentsList.size();
    }
}
