package br.com.adley.pubgstats.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.adley.pubgstats.R;
import br.com.adley.pubgstats.data.LifetimeStats;
import br.com.adley.pubgstats.data.MatchHistory;
import br.com.adley.pubgstats.data.Player;
import br.com.adley.pubgstats.data.Season;
import br.com.adley.pubgstats.data.Stats;
import br.com.adley.pubgstats.data.remote.PBTService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private ViewPager mViewPager;
    private PBTService mService;
    private Player mPlayer;
    private List<MatchHistory> mMatchHistory;
    private List<Season> mSeasons;
    private List<Stats> mStats;
    private LifetimeStats mLifetimeStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            //doSomething
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }

    public void getPlayer(String playerName) {
        if (playerName != null && !playerName.isEmpty()) {
            mService.getPlayerStatsByNickname(playerName.trim()).enqueue(new Callback<Player>() {
                @Override
                public void onResponse(Call<Player> call, @NonNull Response<Player> response) {
                    try {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                mPlayer = response.body();
                                // Validate if has error message.
                                if (mPlayer != null && !mPlayer.getError().isEmpty()) {
                                    Toast.makeText(MainActivity.this, "Player not found", Toast.LENGTH_LONG).show();
                                } else {
                                    mMatchHistory = mPlayer != null ? mPlayer.getMatchHistory() : null;
                                    mSeasons = mPlayer != null ? mPlayer.getSeasons() : null;
                                }
                            }
                        } else {
                            int statusCode = response.code();
                            Toast.makeText(MainActivity.this, "Error code: "+statusCode, Toast.LENGTH_LONG).show();
                            Log.e(LOG_TAG, String.valueOf(statusCode));
                        }
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Player> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    Log.e(LOG_TAG, "An error happened...");
                    Log.e(LOG_TAG, t.getMessage());
                    Log.e(LOG_TAG, t.toString());
                }
            });

        } else {
            Toast.makeText(MainActivity.this, "Username cannot be empty", Toast.LENGTH_LONG).show();
        }
    }
}
