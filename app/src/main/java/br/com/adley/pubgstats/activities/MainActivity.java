package br.com.adley.pubgstats.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.adley.pubgstats.R;
import br.com.adley.pubgstats.adapters.CustomViewPager;
import br.com.adley.pubgstats.adapters.MainPageAdapter;
import br.com.adley.pubgstats.data.Player;
import br.com.adley.pubgstats.data.Season;
import br.com.adley.pubgstats.data.Stats;
import br.com.adley.pubgstats.data.remote.ApiUtils;
import br.com.adley.pubgstats.data.remote.PBTService;
import br.com.adley.pubgstats.library.Utils;
import br.com.adley.pubgstats.wrapper.DuoStats;
import br.com.adley.pubgstats.wrapper.LifetimeStats;
import br.com.adley.pubgstats.wrapper.SoloStats;
import br.com.adley.pubgstats.wrapper.SquadStats;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();
    private CustomViewPager mViewPager;
    private TabLayout mTabLayout;
    private PBTService mService;
    private Player mPlayer;
    private LinearLayout mLayoutPlayerSearchLabel;
    private boolean mIsUserNotSearch = true;
    private LifetimeStats mLifetimeStats;
    private SoloStats mSoloStats;
    private DuoStats mDuoStats;
    private SquadStats mSquadStats;
    private List<Season> mSeasons;
    private List<Stats> mStats;
    private LinearLayout mLoadingLayout;
    private LinearLayout mPlayerNotFoundLayout;
    private LinearLayout mErrorMainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mService = ApiUtils.getPBTService(getString(R.string.api_url));
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager = (CustomViewPager) findViewById(R.id.container);
        mLayoutPlayerSearchLabel = (LinearLayout) findViewById(R.id.layout_player_search_label);
        mLoadingLayout = (LinearLayout) findViewById(R.id.loading_main);
        mPlayerNotFoundLayout = (LinearLayout) findViewById(R.id.player_not_found_layout);
        mErrorMainLayout = (LinearLayout) findViewById(R.id.error_main_layout);

        // Create custom tabs.
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        // Set lifetime fragment.
        View lifetimeView = getLayoutInflater().inflate(R.layout.tab_main, null);
        TextView lifetimeTabText = lifetimeView.findViewById(R.id.text_tab);
        lifetimeTabText.setText(getString(R.string.tab_lifetime));
        // Set Icon
        //ImageView lifetime_tab_icon = (ImageView) favoritesView.findViewById(R.id.icon_tab);
        //lifetime_tab_icon.setImageResource(R.drawable.ic_favorite_white_24dp);

        // Solo Stats Fragment
        View soloView = getLayoutInflater().inflate(R.layout.tab_main, null);
        TextView soloTabText = soloView.findViewById(R.id.text_tab);
        soloTabText .setText(getString(R.string.tab_solo));

        // Duo Stats Fragment
        View duoView = getLayoutInflater().inflate(R.layout.tab_main, null);
        TextView duoTabText = duoView.findViewById(R.id.text_tab);
        duoTabText .setText(getString(R.string.tab_duo));

        // Squad Stats Fragment
        View squadView = getLayoutInflater().inflate(R.layout.tab_main, null);
        TextView squadTabText = squadView.findViewById(R.id.text_tab);
        squadTabText .setText(getString(R.string.tab_squad));

        if (mTabLayout != null) {
            mTabLayout.addTab(mTabLayout.newTab().setCustomView(lifetimeView));
            mTabLayout.addTab(mTabLayout.newTab().setCustomView(soloView));
            mTabLayout.addTab(mTabLayout.newTab().setCustomView(duoView));
            mTabLayout.addTab(mTabLayout.newTab().setCustomView(squadView));
            mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            final MainPageAdapter adapter = new MainPageAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());
            if (mViewPager != null) {
                mViewPager.setAdapter(adapter);
                mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
                mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        mViewPager.setCurrentItem(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                });
            }
            // Set up the ViewPager with the sections adapter.
            mViewPager.setPagingEnabled(false);
            mTabLayout.setVisibility(View.GONE);
        }

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
        // Set the actions for searching username.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getPlayer(query);
                Utils.closeKeyboard(MainActivity.this);
                setLayoutVisibilitiesLoading();
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
                                if (mPlayer.getError() != null && !mPlayer.getError().isEmpty()) {
                                    Toast.makeText(MainActivity.this, "Player not found", Toast.LENGTH_LONG).show();
                                    setLayoutVisibilitiesUserNotFound();
                                } else {
                                    // Set objects with Player Data
                                    mSeasons = mPlayer.getSeasons();
                                    mSoloStats = mPlayer.getSoloStats();
                                    mDuoStats = mPlayer.getDuoStats();
                                    mSquadStats = mPlayer.getSquadStats();
                                    mLifetimeStats = mPlayer.getLifetimeStats();
                                    setLayoutEnableContent();
                                }
                            }
                        } else {
                            int statusCode = response.code();
                            setErrorMainLayout();
                            Toast.makeText(MainActivity.this, "Error code: "+statusCode, Toast.LENGTH_LONG).show();
                            Log.e(LOG_TAG, String.valueOf(statusCode));
                        }

                        Toast.makeText(MainActivity.this, mPlayer.getPlayerName() != null ? mPlayer.getPlayerName() : "Not Work",Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        setErrorMainLayout();
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

    public void setLayoutEnableContent(){
        mLayoutPlayerSearchLabel.setVisibility(View.GONE);
        mLoadingLayout.setVisibility(View.GONE);
        mPlayerNotFoundLayout.setVisibility(View.GONE);
        mErrorMainLayout.setVisibility(View.GONE);
        mViewPager.setVisibility(View.VISIBLE);
        mTabLayout.setVisibility(View.VISIBLE);
        mViewPager.setPagingEnabled(true);
    }

    public void setLayoutVisibilitiesLoading(){
        mPlayerNotFoundLayout.setVisibility(View.GONE);
        mLayoutPlayerSearchLabel.setVisibility(View.GONE);
        mViewPager.setVisibility(View.GONE);
        mViewPager.setPagingEnabled(false); // Do not allow change tabs by swipe
        mPlayerNotFoundLayout.setVisibility(View.GONE);
        mErrorMainLayout.setVisibility(View.GONE);
        mLoadingLayout.setVisibility(View.VISIBLE);
    }

    public void setLayoutVisibilitiesUserNotFound(){
        mViewPager.setVisibility(View.GONE); // Removes the content
        mLayoutPlayerSearchLabel.setVisibility(View.GONE); // Remove layout with the label Search for a player.
        mLoadingLayout.setVisibility(View.GONE); // Remove loading layout.
        mErrorMainLayout.setVisibility(View.GONE); // Remove error layout
        mTabLayout.setVisibility(View.GONE); // Remove tabs
        mViewPager.setPagingEnabled(false); // Do not allow change tabs by swipe
        mPlayerNotFoundLayout.setVisibility(View.VISIBLE); // Show Player not found message
    }

    public void setErrorMainLayout(){
        mPlayerNotFoundLayout.setVisibility(View.GONE);
        mLayoutPlayerSearchLabel.setVisibility(View.GONE);
        mViewPager.setVisibility(View.GONE);
        mViewPager.setPagingEnabled(false); // Do not allow change tabs by swipe
        mPlayerNotFoundLayout.setVisibility(View.GONE);
        mLoadingLayout.setVisibility(View.GONE);
        mErrorMainLayout.setVisibility(View.VISIBLE);
    }

}
