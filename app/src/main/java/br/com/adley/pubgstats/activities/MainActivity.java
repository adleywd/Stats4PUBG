package br.com.adley.pubgstats.activities;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import br.com.adley.pubgstats.R;
import br.com.adley.pubgstats.data.ApiUtils;
import br.com.adley.pubgstats.data.Player;
import br.com.adley.pubgstats.data.remote.PBTService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private String LOG_TAG = MainActivity.class.getSimpleName();
    private PBTService mService;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Button mSearchButton;
    private EditText mNicknameInput;
    private RelativeLayout mRelativeLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mService = ApiUtils.getPBTService();
        mSearchButton = (Button)findViewById(R.id.search_button);
        mNicknameInput = (EditText)findViewById(R.id.input_player_nickname);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.main_layout);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mNicknameInput != null && !mNicknameInput.toString().equals("")) {

                    mService.getPlayerStatsByNickname(mNicknameInput.toString()).enqueue(new Callback<Player>() {
                        @Override
                        public void onResponse(Call<Player> call, Response<Player> response) {
                            if (response.isSuccessful()) {
                                Log.i(LOG_TAG, response.body().getPlayerName());
                            } else {
                                int statusCode = response.code();
                                Log.e(LOG_TAG, String.valueOf(statusCode));
                            }
                        }

                        @Override
                        public void onFailure(Call<Player> call, Throwable t) {
                            Log.e(LOG_TAG, "Ocorreu um erro");
                            Log.e(LOG_TAG, t.getMessage());
                            Log.e(LOG_TAG, t.toString());
                        }
                    });
                }else{
                    Snackbar snackbar = Snackbar.make(mRelativeLayout, "Você deve preencher o campo de busca.", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });

    }



}
