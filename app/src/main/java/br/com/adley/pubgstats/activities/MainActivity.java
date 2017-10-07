package br.com.adley.pubgstats.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import br.com.adley.pubgstats.R;
import br.com.adley.pubgstats.data.LifetimeStats;
import br.com.adley.pubgstats.data.Player;
import br.com.adley.pubgstats.data.Season;
import br.com.adley.pubgstats.data.Stats;
import br.com.adley.pubgstats.data.remote.ApiUtils;
import br.com.adley.pubgstats.data.remote.PBTService;
import br.com.adley.pubgstats.fragments.DefaultFragment;
import br.com.adley.pubgstats.fragments.DuoFragment;
import br.com.adley.pubgstats.fragments.LifeTimeFragment;
import br.com.adley.pubgstats.fragments.SoloFragment;
import br.com.adley.pubgstats.fragments.SquadFragment;
import br.com.adley.pubgstats.library.Utils;
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
    private LifetimeStats mLifetimeStats;
    private List<Season> mSeasons;
    private List<Stats> mStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mService = ApiUtils.getPBTService(getString(R.string.api_url));

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
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getPlayer(query);
                Utils.closeKeyboard(MainActivity.this);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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
            if(position == 0){
                return new LifeTimeFragment();
            } else if(position == 1){
                return new SoloFragment();
            } else if(position == 2){
                return new DuoFragment();
            } else if(position == 3){
                return new SquadFragment();
            }

            return new DefaultFragment();
        }

        @Override
        public int getCount() {
            // Show 4 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "All";
                case 1:
                    return "Solo";
                case 2:
                    return "Duo";
                case 3:
                    return "Squad";
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
                                if (mPlayer != null && mPlayer.getError() != null && !mPlayer.getError().isEmpty()) {
                                    Toast.makeText(MainActivity.this, "Player not found", Toast.LENGTH_LONG).show();
                                } else {
                                    mSeasons = mPlayer.getSeasons();
                                    mLifetimeStats = mPlayer.getLifetimeStats();
                                }
                            }
                        } else {
                            int statusCode = response.code();
                            Toast.makeText(MainActivity.this, "Error code: "+statusCode, Toast.LENGTH_LONG).show();
                            Log.e(LOG_TAG, String.valueOf(statusCode));
                        }

                        Toast.makeText(MainActivity.this, mPlayer.getPlayerName() != null? mPlayer.getPlayerName() : "Deu ruim",Toast.LENGTH_LONG).show();
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
