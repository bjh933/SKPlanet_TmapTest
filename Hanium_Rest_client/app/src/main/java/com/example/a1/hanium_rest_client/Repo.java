package com.example.a1.hanium_rest_client;

import com.example.a1.hanium_rest_client.Model.wModel;

/**
 * Created by a1 on 2017. 7. 3..
 */

public class Repo {

    private double longitude = 0.0;
    private double latitude = 0.0;
    private wModel.SkyStatus skyStatus = wModel.SkyStatus.sunny;
    private wModel.WeatherAmount rainAmount = wModel.WeatherAmount.none;
    private wModel.WeatherAmount snowAmount = wModel.WeatherAmount.none;
    private wModel.WeatherAmount fogAmount = wModel.WeatherAmount.none;
    private wModel.WeatherAmount cloudAmount = wModel.WeatherAmount.none;
    private wModel.WindDirection windDirection = wModel.WindDirection.N;
    private double windSpeed = 0.0;

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public wModel.SkyStatus getSkyStatus() {
        return skyStatus;
    }

    public wModel.WeatherAmount getRainAmount() {
        return rainAmount;
    }

    public wModel.WeatherAmount getSnowAmount() {
        return snowAmount;
    }

    public wModel.WeatherAmount getFogAmount() {
        return fogAmount;
    }

    public wModel.WeatherAmount getCloudAmount() {
        return cloudAmount;
    }

    public wModel.WindDirection getWindDirection() {
        return windDirection;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

}
