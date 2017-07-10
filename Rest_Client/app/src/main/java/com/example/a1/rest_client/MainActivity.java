package com.example.a1.rest_client;

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

    @Bind(R.id.max)
    TextView max;

    @Bind(R.id.min)
    TextView min;

    @Bind(R.id.getWeatherBtn)
    Button getWeatherBtn;

    @Bind(R.id.lat)
    EditText mlat;

    @Bind(R.id.lon)
    EditText mlon;

    @OnClick(R.id.getWeatherBtn)
    public void setGetWeatherBtn(View view) {

        String lat = mlat.getText().toString();
        String lon = mlon.getText().toString();

        Retrofit client = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create()).build();

        ContentService service = client.create(ContentService.class);

        Call<Repo> call = service.repo("API Key",
                Double.valueOf(lat), Double.valueOf(lon));

        call.enqueue(new Callback<Repo>() {
            public void onResponse(Response<Repo> response) {
                if (response.isSuccess()) {
                    Repo repo = response.body();
                    double rMax = Double.valueOf(repo.getModel().getTemp_max()) - 273.15;
                    double rMin = Double.valueOf(repo.getModel().getTemp_min()) - 273.15;
                    max.setText(String.valueOf(rMax));
                    min.setText(String.valueOf(rMin));
                } else {

                }
            }

            @Override
            public void onFailure(Throwable t) {

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
