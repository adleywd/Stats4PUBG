package br.com.adley.pubgstats.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

import br.com.adley.pubgstats.R;
import br.com.adley.pubgstats.data.MatchHistory;
import br.com.adley.pubgstats.data.Player;
import br.com.adley.pubgstats.data.Season;
import br.com.adley.pubgstats.data.Stats;
import br.com.adley.pubgstats.data.remote.ApiUtils;
import br.com.adley.pubgstats.data.remote.PBTService;
import br.com.adley.pubgstats.library.Constants;
import br.com.adley.pubgstats.library.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private String LOG_TAG = MainActivity.class.getSimpleName();
    private PBTService mService;
    private ImageButton mSearchButton;
    private EditText mSearchInput;
    private RelativeLayout mRelativeLayout;
    private Player mPlayer;
    private List<MatchHistory> mMatchHistory;
    private List<Season> mSeasons;
    private List<Stats> mStats;
    private CardView mCardViewPlayerResume;
    private TextView mMatchesPlayed;
    private TextView mWins;
    private TextView mTop10s;
    private TextView mKills;
    private TextView mKD;
    private TextView mHeals;
    private LinearLayout mLoaderLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mService = ApiUtils.getPBTService(getString(R.string.api_url));
        mSearchButton = (ImageButton)findViewById(R.id.search_button);
        mSearchInput = (EditText)findViewById(R.id.input_player_nickname);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.main_layout);
        mLoaderLayout = (LinearLayout) findViewById(R.id.spinnerLayout);
        mCardViewPlayerResume = (CardView) findViewById(R.id.cardViewPlayerResume);
        mMatchesPlayed = (TextView) findViewById(R.id.txtMatchesPlayed);
        mWins = (TextView) findViewById(R.id.txtWins);
        mTop10s = (TextView) findViewById(R.id.txtTop10);
        mKills = (TextView) findViewById(R.id.txtKills);
        mKD = (TextView) findViewById(R.id.txtKd);
        mHeals = (TextView) findViewById(R.id.txtHeals);

        // Perform an action when search key in keyboard is pressed.
        mSearchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_SEARCH){
                    getPlayer();
                    return true;
                }
                return false;
            }
        });

        // Perform an action when click on search button.
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPlayer();
            }
        });

        // Start player static details activity
        mCardViewPlayerResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // start - Just for tests
                mPlayer = new Player();
                mPlayer.setPlayerName("Adley");
                // end  - Just for tests
                // Prepare the parcelable object to be sent to another activity using an Intent Class.
                Intent intentPlayerDetails = new Intent();
                Bundle bundlePlayerDetails = new Bundle();
                bundlePlayerDetails.putParcelable(Constants.PLAYER_OBJ_KEY, mPlayer);
                intentPlayerDetails.putExtras(bundlePlayerDetails);
                // Initiate player details stats class.
                intentPlayerDetails.setClass(MainActivity.this, PlayerDetailsActivity.class);
                startActivity(intentPlayerDetails);
            }
        });

    }

    public void getPlayer(){
        if (mSearchInput != null && !mSearchInput.getText().toString().trim().equals("")) {
            closeKeyboard();
            mCardViewPlayerResume.setVisibility(View.GONE);
            mLoaderLayout.setVisibility(View.VISIBLE);

            // SEARCH FOR NICKNAME
            mService.getPlayerStatsByNickname(mSearchInput.getText().toString().trim()).enqueue(new Callback<Player>() {
                @Override
                public void onResponse(Call<Player> call, @NonNull Response<Player> response) {
                    if (response.isSuccessful()) {
                        if(response.body() != null) {
                            mPlayer = response.body();
                            // Validate if has error message.
                            if(mPlayer != null && !mPlayer.getError().isEmpty()){
                                mLoaderLayout.setVisibility(View.GONE);
                                Snackbar snackbar;
                                String errorMessage;
                                if(mPlayer.getErrorMessage() != null && !mPlayer.getErrorMessage().isEmpty()){
                                    errorMessage = mPlayer.getErrorMessage();
                                } else if(!mPlayer.getError().isEmpty()){
                                    errorMessage = mPlayer.getError();
                                } else {
                                    errorMessage = "Something went wrong :(";
                                }
                                snackbar = Snackbar.make(mRelativeLayout, errorMessage, Snackbar.LENGTH_LONG);
                                snackbar.show();
                            } else {
                                mMatchHistory = mPlayer != null ? mPlayer.getMatchHistory() : null;
                                mSeasons = mPlayer != null ? mPlayer.getSeasons() : null;
                                if (mSeasons != null) {
                                    bindCardView();
                                } else {
                                    mLoaderLayout.setVisibility(View.GONE);
                                    Snackbar snackbar = Snackbar.make(mRelativeLayout, "User not found.", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                            }
                        }
                    } else {
                        int statusCode = response.code();
                        Log.e(LOG_TAG, String.valueOf(statusCode));
                    }
                }

                @Override
                public void onFailure(Call<Player> call, Throwable t) {
                    Snackbar snackbar = Snackbar.make(mRelativeLayout, "An error happened: "+t.getMessage(), Snackbar.LENGTH_LONG);
                    snackbar.show();
                    Log.e(LOG_TAG, "An error happened...");
                    Log.e(LOG_TAG, t.getMessage());
                    Log.e(LOG_TAG, t.toString());
                }
            });

        }else{
            Snackbar snackbar = Snackbar.make(mRelativeLayout, "Você deve preencher o campo de busca.", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    public void bindCardView(){
        List<Stats> statsList;
        DecimalFormat df = new DecimalFormat("0.00");
        int killsTotal = 0, winsTotal = 0, top10s = 0, matchesPlayedTotal = 0, healsTotal = 0;
        float kdAverage;
        for(Season season: mSeasons){
            // Get all seasons "Aggregated". This type of season contains all seasons.
            // Here the type o game (solo, duo...) doesn't matter. We want all.
            if(season.getRegion().equals("agg")){
                statsList = season.getStats();

                // Get All Matches Played
                for(Stats stats : statsList) {
                    if(stats.getLabel().equals("Rounds Played")){
                        matchesPlayedTotal += stats.getValueInt();
                    }
                }

                // Get All Kills
                for(Stats stats : statsList){
                    if(stats.getLabel().equals("Kills")){
                        killsTotal += stats.getValueInt();
                    }
                }

                // Get All Wins
                for(Stats stats : statsList){
                    if(stats.getLabel().equals("Wins")){
                        winsTotal += stats.getValueInt();
                    }
                }

                // Get All Top 10s
                for(Stats stats : statsList){
                    if(stats.getLabel().equals("Top 10s")){
                        top10s += stats.getValueInt();
                    }
                }

                // Get All Heals
                for(Stats stats : statsList){
                    if(stats.getLabel().equals("Heals")){
                        healsTotal += stats.getValueInt();
                    }
                }

            }
        }

        // Get the average of K/D
        kdAverage = Utils.getKDAverage(killsTotal, matchesPlayedTotal, winsTotal);

        mKills.setText(String.valueOf(killsTotal));
        mWins.setText(String.valueOf(winsTotal));
        mTop10s.setText(String.valueOf(top10s));
        mMatchesPlayed.setText(String.valueOf(matchesPlayedTotal));
        mHeals.setText(String.valueOf(healsTotal));
        mKD.setText(String.valueOf(df.format(kdAverage)));
        // Hide spinner loader.
        mLoaderLayout.setVisibility(View.GONE);
        // Make the card view result visible.
        mCardViewPlayerResume.setVisibility(View.VISIBLE);
    }

    private void closeKeyboard() {
        try {
            InputMethodManager inputManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
        } catch (NullPointerException ignored){}
    }
}
