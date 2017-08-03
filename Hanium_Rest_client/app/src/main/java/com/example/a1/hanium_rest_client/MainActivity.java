package com.example.a1.hanium_rest_client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.skyStatus)
    TextView skyStatus;

    @Bind(R.id.rainAmount)
    TextView rainAmount;

    @Bind(R.id.windSpeed)
    TextView windSpeed;

    @Bind(R.id.getWeatherBtn)
    Button getWeatherBtn;

    @OnClick(R.id.getWeatherBtn)
    public void setGetWeatherBtn(View view) {

        double lon=127.0;
        double lat=37.0;

        Retrofit client = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                //.baseUrl("http://10.0.2.2:8080/data/weather?lon=25.0&lat=38.7")
                .addConverterFactory(GsonConverterFactory.create()).build();

        ContentService service = client.create(ContentService.class);

        Call<Repo> call = service.repo(Double.valueOf(lon), Double.valueOf(lat));

        call.enqueue(new Callback<Repo>() {
            public void onResponse(Response<Repo> response) {
                if (response.isSuccess()) {
                    Repo repo = response.body();
                    skyStatus.setText(String.valueOf(repo.getSkyStatus()));
                    rainAmount.setText(String.valueOf(repo.getRainAmount()));
                    windSpeed.setText(String.valueOf(repo.getWindSpeed()));
                } else {

                }
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }
}
