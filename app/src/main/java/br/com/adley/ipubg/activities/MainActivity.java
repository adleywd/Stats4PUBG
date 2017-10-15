package br.com.adley.ipubg.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
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

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.adley.ipubg.R;
import br.com.adley.ipubg.adapters.CustomViewPager;
import br.com.adley.ipubg.adapters.MainPageAdapter;
import br.com.adley.ipubg.data.models.Player;
import br.com.adley.ipubg.data.remote.ApiUtils;
import br.com.adley.ipubg.data.remote.PBTService;
import br.com.adley.ipubg.fragments.DuoFppFragment;
import br.com.adley.ipubg.fragments.DuoFragment;
import br.com.adley.ipubg.fragments.LifeTimeFragment;
import br.com.adley.ipubg.fragments.SoloFppFragment;
import br.com.adley.ipubg.fragments.SoloFragment;
import br.com.adley.ipubg.fragments.SquadFppFragment;
import br.com.adley.ipubg.fragments.SquadFragment;
import br.com.adley.ipubg.library.TabsEnum;
import br.com.adley.ipubg.library.Utils;
import br.com.adley.ipubg.wrapper.DuoStats;
import br.com.adley.ipubg.wrapper.LifetimeStats;
import br.com.adley.ipubg.wrapper.SoloStats;
import br.com.adley.ipubg.wrapper.SquadStats;
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
    public LifetimeStats mLifetimeStats;
    public SoloStats mSoloStats;
    public DuoStats mDuoStats;
    public SquadStats mSquadStats;
    private LinearLayout mLoadingLayout;
    private LinearLayout mPlayerNotFoundLayout;
    private LinearLayout mErrorMainLayout;
    private List<Fragment> mFragmentsList;
    private TextView mSeasonRegionLabel;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //AdMob Config
        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this, getString(R.string.ads_app_id));

        // Gets the ad view defined in layout/ad_fragment.xml with ad unit ID set in
        // values/strings.xml.
        mAdView = findViewById(R.id.ad_view_main);

        // Create an ad request. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        // Start loading the ad in the background.
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mAdView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int i) {
                super.onAdFailedToLoad(i);
                mAdView.setVisibility(View.GONE);
            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        buildFragmentList();
        mService = ApiUtils.getPBTService(getString(R.string.api_url));
        mTabLayout = findViewById(R.id.tabs);
        mViewPager = findViewById(R.id.container);
        mLayoutPlayerSearchLabel = findViewById(R.id.layout_player_search_label);
        mLoadingLayout = findViewById(R.id.loading_main);
        mPlayerNotFoundLayout = findViewById(R.id.player_not_found_layout);
        mErrorMainLayout = findViewById(R.id.error_main_layout);
        mSeasonRegionLabel = findViewById(R.id.seasonRegionLabel);

        // Create custom tabs.
        mTabLayout = findViewById(R.id.tabs);
        // Set lifetime fragment.
        View lifetimeView = getLayoutInflater().inflate(R.layout.tab_main, null);
        TextView lifetimeTabText = lifetimeView.findViewById(R.id.text_tab);
        lifetimeTabText.setText(getString(R.string.tab_lifetime));
        // Set Icon
        //ImageView lifetime_tab_icon = (ImageView) favoritesView.findViewById(R.id.icon_tab);
        //lifetime_tab_icon.setImageResource(R.drawable.ic_favorite_white_24dp);

        // SOLO Stats Fragment
        View soloView = getLayoutInflater().inflate(R.layout.tab_main, null);
        TextView soloTabText = soloView.findViewById(R.id.text_tab);
        soloTabText.setText(getString(R.string.tab_solo));

        // Duo Stats Fragment
        View duoView = getLayoutInflater().inflate(R.layout.tab_main, null);
        TextView duoTabText = duoView.findViewById(R.id.text_tab);
        duoTabText.setText(getString(R.string.tab_duo));

        // Squad Stats Fragment
        View squadView = getLayoutInflater().inflate(R.layout.tab_main, null);
        TextView squadTabText = squadView.findViewById(R.id.text_tab);
        squadTabText.setText(getString(R.string.tab_squad));

        // SOLO FPP Stats Fragment
        View soloFppView = getLayoutInflater().inflate(R.layout.tab_main, null);
        TextView soloFppTabText = soloFppView.findViewById(R.id.text_tab);
        soloFppTabText.setText(getString(R.string.tab_solo_fpp));

        // Duo FPP Stats Fragment
        View duoFppView = getLayoutInflater().inflate(R.layout.tab_main, null);
        TextView duoFppTabText = duoFppView .findViewById(R.id.text_tab);
        duoFppTabText.setText(getString(R.string.tab_duo_fpp));

        // Squad FPP Stats Fragment
        View squadFppView = getLayoutInflater().inflate(R.layout.tab_main, null);
        TextView squadFppTabText = squadFppView.findViewById(R.id.text_tab);
        squadFppTabText.setText(getString(R.string.tab_squad_fpp));

        if (mTabLayout != null) {
            mTabLayout.addTab(mTabLayout.newTab().setCustomView(lifetimeView));
            mTabLayout.addTab(mTabLayout.newTab().setCustomView(soloView));
            mTabLayout.addTab(mTabLayout.newTab().setCustomView(duoView));
            mTabLayout.addTab(mTabLayout.newTab().setCustomView(squadView));
            mTabLayout.addTab(mTabLayout.newTab().setCustomView(soloFppView));
            mTabLayout.addTab(mTabLayout.newTab().setCustomView(duoFppView));
            mTabLayout.addTab(mTabLayout.newTab().setCustomView(squadFppView));
            mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            final MainPageAdapter adapter = new MainPageAdapter(getSupportFragmentManager(), mFragmentsList);
            if (mViewPager != null) {
                mViewPager.setAdapter(adapter);
                mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
                mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        setBindContantInTabs(tab, adapter);
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
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }


    private void buildFragmentList() {
        mFragmentsList = new ArrayList<>();
        mFragmentsList.add(new LifeTimeFragment());
        mFragmentsList.add(new SoloFragment());
        mFragmentsList.add(new DuoFragment());
        mFragmentsList.add(new SquadFragment());
        mFragmentsList.add(new SoloFppFragment());
        mFragmentsList.add(new DuoFppFragment());
        mFragmentsList.add(new SquadFppFragment());
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
    public void onResume() {
        // Resume the AdView.
        super.onResume();
        mAdView.resume();
    }

    @Override
    public void onPause() {
        // Pause the AdView.
        super.onPause();
        mAdView.pause();

    }

    @Override
    public void onDestroy() {
        // Destroy the AdView.
        super.onDestroy();
        mAdView.destroy();
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
                                    Toast.makeText(MainActivity.this, "Player not found", Toast.LENGTH_SHORT).show();
                                    setLayoutVisibilitiesUserNotFound();
                                } else {
                                    // Set objects with Player Data
                                    mSoloStats = mPlayer.getSoloStats();
                                    mDuoStats = mPlayer.getDuoStats();
                                    mSquadStats = mPlayer.getSquadStats();
                                    mLifetimeStats = mPlayer.getLifetimeStats();
                                    for (Fragment fragment : mFragmentsList) {
                                        if (fragment instanceof LifeTimeFragment && fragment.getActivity() != null) {
                                            ((LifeTimeFragment) fragment).bindStatsValues(mPlayer.getLifetimeStats());
                                        }
                                        if (fragment instanceof SoloFragment && fragment.getActivity() != null) {
                                            ((SoloFragment) fragment).bindStatsValues(mPlayer.getSoloStats());
                                        }
                                        if (fragment instanceof DuoFragment && fragment.getActivity() != null) {
                                            ((DuoFragment) fragment).bindStatsValues(mPlayer.getDuoStats());
                                        }
                                        if (fragment instanceof SquadFragment && fragment.getActivity() != null) {
                                            ((SquadFragment) fragment).bindStatsValues(mPlayer.getSquadStats());
                                        }
                                        if (fragment instanceof SoloFppFragment && fragment.getActivity() != null) {
                                            ((SoloFppFragment) fragment).bindStatsValues(mPlayer.getSoloFppStats(), mPlayer.getSelectedRegion().toUpperCase(), "SOLO FPP");
                                        }
                                        if (fragment instanceof DuoFppFragment && fragment.getActivity() != null) {
                                            ((DuoFppFragment) fragment).bindStatsValues(mPlayer.getDuoFppStats(), mPlayer.getSelectedRegion().toUpperCase(), "DUO FPP");
                                        }
                                        if (fragment instanceof SquadFppFragment && fragment.getActivity() != null) {
                                            ((SquadFppFragment) fragment).bindStatsValues(mPlayer.getSquadFppStats(), mPlayer.getSelectedRegion().toUpperCase(), "SQUAD FPP");
                                        }
                                    }
                                    mSeasonRegionLabel.setText(String.format(Locale.US,"%s - %s",mPlayer.getSeasonDisplay(), mPlayer.getSelectedRegion().toUpperCase()));
                                    setLayoutEnableContent();
                                }
                            }
                        } else {
                            int statusCode = response.code();
                            setErrorMainLayout();
                            Toast.makeText(MainActivity.this, "Error code: " + statusCode, Toast.LENGTH_LONG).show();
                            Log.e(LOG_TAG, String.valueOf(statusCode));
                        }

                    } catch (Exception e) {
                        setErrorMainLayout();
                        Log.e("ERROR", e.getMessage());
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

    private void setBindContantInTabs(TabLayout.Tab tab, MainPageAdapter adapter){
        mViewPager.setCurrentItem(tab.getPosition());
        if (tab.getPosition() == TabsEnum.ALL.getValue()) {
            ((LifeTimeFragment) adapter.getItem(tab.getPosition())).bindStatsValues(mPlayer.getLifetimeStats());
        }
        else if(tab.getPosition() == TabsEnum.SOLO.getValue()){
            ((SoloFragment) adapter.getItem(tab.getPosition())).bindStatsValues(mPlayer.getSoloStats());
        }
        else if(tab.getPosition() == TabsEnum.DUO.getValue()){
            ((DuoFragment) adapter.getItem(tab.getPosition())).bindStatsValues(mPlayer.getDuoStats());
        }
        else if(tab.getPosition() == TabsEnum.SQUAD.getValue()){
            ((SquadFragment) adapter.getItem(tab.getPosition())).bindStatsValues(mPlayer.getSquadStats());
        }
        else if(tab.getPosition() == TabsEnum.SOLOFPP.getValue()){
            ((SoloFppFragment) adapter.getItem(tab.getPosition())).bindStatsValues(mPlayer.getSoloFppStats(), mPlayer.getSelectedRegion().toUpperCase(), "SOLO FPP");
        }
        else if(tab.getPosition() == TabsEnum.DUOFPP.getValue()){
            ((DuoFppFragment) adapter.getItem(tab.getPosition())).bindStatsValues(mPlayer.getDuoFppStats(), mPlayer.getSelectedRegion().toUpperCase(), "DUO FPP");
        }
        else if(tab.getPosition() == TabsEnum.SQUADFPP.getValue()){
            ((SquadFppFragment) adapter.getItem(tab.getPosition())).bindStatsValues(mPlayer.getSquadFppStats(), mPlayer.getSelectedRegion().toUpperCase(), "SQUAD FPP");
        }
    }

    public void setLayoutEnableContent() {
        mLayoutPlayerSearchLabel.setVisibility(View.GONE);
        mLoadingLayout.setVisibility(View.GONE);
        mPlayerNotFoundLayout.setVisibility(View.GONE);
        mErrorMainLayout.setVisibility(View.GONE);
        mViewPager.setVisibility(View.VISIBLE);
        mTabLayout.setVisibility(View.VISIBLE);
        mViewPager.setPagingEnabled(true);
        mSeasonRegionLabel.setVisibility(View.VISIBLE);
    }

    public void setLayoutVisibilitiesLoading() {
        mLayoutPlayerSearchLabel.setVisibility(View.GONE);
        mViewPager.setVisibility(View.INVISIBLE);
        mViewPager.setPagingEnabled(false); // Do not allow change tabs by swipe
        mPlayerNotFoundLayout.setVisibility(View.GONE);
        mErrorMainLayout.setVisibility(View.GONE);
        mLoadingLayout.setVisibility(View.VISIBLE);
        mSeasonRegionLabel.setVisibility(View.GONE);
    }

    public void setLayoutVisibilitiesUserNotFound() {
        mViewPager.setVisibility(View.INVISIBLE); // Removes the content
        mLayoutPlayerSearchLabel.setVisibility(View.GONE); // Remove layout with the label Search for a player.
        mLoadingLayout.setVisibility(View.GONE); // Remove loading layout.
        mErrorMainLayout.setVisibility(View.GONE); // Remove error layout
        mTabLayout.setVisibility(View.GONE); // Remove tabs
        mViewPager.setPagingEnabled(false); // Do not allow change tabs by swipe
        mPlayerNotFoundLayout.setVisibility(View.VISIBLE); // Show Player not found message
        mSeasonRegionLabel.setVisibility(View.GONE);
    }

    public void setErrorMainLayout() {
        mPlayerNotFoundLayout.setVisibility(View.GONE);
        mLayoutPlayerSearchLabel.setVisibility(View.GONE);
        mViewPager.setVisibility(View.INVISIBLE);
        mViewPager.setPagingEnabled(false); // Do not allow change tabs by swipe
        mPlayerNotFoundLayout.setVisibility(View.GONE);
        mLoadingLayout.setVisibility(View.GONE);
        mErrorMainLayout.setVisibility(View.VISIBLE);
        mSeasonRegionLabel.setVisibility(View.GONE);
    }

}
