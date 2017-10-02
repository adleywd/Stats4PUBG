package br.com.adley.pubgstats.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import br.com.adley.pubgstats.R;
import br.com.adley.pubgstats.data.Player;
import br.com.adley.pubgstats.library.Constants;

public class PlayerDetailsActivity extends AppCompatActivity {

    private Player mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Favorite Float Button
        FloatingActionButton favoriteFab = (FloatingActionButton) findViewById(R.id.favoriteFab);
        favoriteFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Do nothing", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Retrieves the object that was sent by an Intent.
        Bundle b = this.getIntent().getExtras();
        if(b != null){
            mPlayer = b.getParcelable(Constants.PLAYER_OBJ_KEY);
        }

        Log.d("PLAYER_NAME", "NOME: " + mPlayer.getPlayerName());
    }
}
